package work.lclpnet.mmoblocks.entity;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.mmoblocks.asm.mixin.common.GoalSelectorAccessor;
import work.lclpnet.mmoblocks.entity.ai.ActWaryGoal;
import work.lclpnet.mmoblocks.entity.ai.FavorBlockGoal;
import work.lclpnet.mmoblocks.entity.ai.RunAndPoofGoal;
import work.lclpnet.mmoblocks.sound.MMOSounds;

import java.util.List;
import java.util.Set;

public class StonelingEntity extends PathAwareEntity {

    public static final Identifier CARRY_LOOT_TABLE = new Identifier("mmoblocks", "entities/stoneling_carry");

    private static final TrackedData<ItemStack> CARRYING_ITEM = DataTracker.registerData(StonelingEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private static final TrackedData<Byte> VARIANT = DataTracker.registerData(StonelingEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Float> HOLD_ANGLE = DataTracker.registerData(StonelingEntity.class, TrackedDataHandlerRegistry.FLOAT);

    private static final String TAG_CARRYING_ITEM = "carryingItem";
    private static final String TAG_VARIANT = "variant";
    private static final String TAG_HOLD_ANGLE = "itemAngle";
    private static final String TAG_PLAYER_MADE = "playerMade";

    private ActWaryGoal waryGoal;

    private boolean isTame;

    public StonelingEntity(EntityType<? extends StonelingEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_CACTUS, 1.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_CACTUS, 1.0F);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        dataTracker.startTracking(CARRYING_ITEM, ItemStack.EMPTY);
        dataTracker.startTracking(VARIANT, (byte) 0);
        dataTracker.startTracking(HOLD_ANGLE, 0F);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(5, new WanderAroundFarGoal(this, 0.2, 0.98F));
        goalSelector.add(4, new FavorBlockGoal(this, 0.2, s -> s.getBlock() == Blocks.DIAMOND_ORE));
        goalSelector.add(3, new TemptGoal(this, 0.6, Ingredient.ofItems(Items.DIAMOND), false));
        goalSelector.add(2, new RunAndPoofGoal<>(this, PlayerEntity.class, 4, 0.5, 0.5));
        goalSelector.add(1, waryGoal = new ActWaryGoal(this, 0.1, 6, () -> true));
        goalSelector.add(0, new TemptGoal(this, 0.6, Ingredient.ofItems(Items.DIAMOND), false));
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1D);
    }

    @Override
    public void tick() {
        super.tick();

        if (touchingWater) stepHeight = 1F;
        else stepHeight = 0.6F;

        if (!world.isClient && world.getDifficulty() == Difficulty.PEACEFUL && !isTame) {
            remove();
            for (Entity passenger : getPassengersDeep())
                if (!(passenger instanceof PlayerEntity))
                    passenger.remove();
        }

        this.prevBodyYaw = this.prevYaw;
        this.bodyYaw = this.yaw;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !isTame;
    }

    @Override
    public void checkDespawn() {
        boolean wasAlive = isAlive();
        super.checkDespawn();
        if (!isAlive() && wasAlive)
            for (Entity passenger : getPassengersDeep())
                if (!(passenger instanceof PlayerEntity))
                    passenger.remove();
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!stack.isEmpty() && stack.getItem() == Items.NAME_TAG) return stack.getItem().useOnEntity(stack, player, this, hand);
        else return super.interactMob(player, hand);
    }

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        if (hand != Hand.MAIN_HAND || !isAlive()) {
            return ActionResult.PASS;
        }

        ItemStack playerItem = player.getStackInHand(hand);
        Vec3d pos = getPos();

        if (world.isClient) {
            return ActionResult.PASS;
        }

        if (isPlayerMade()) {
            if (!player.isSneaky() && !playerItem.isEmpty()) {

                StonelingVariant currentVariant = getVariant();
                StonelingVariant targetVariant = null;
                Block targetBlock = null;
                mainLoop: for (StonelingVariant variant : StonelingVariant.values()) {
                    for (Block block : variant.getBlocks()) {
                        if (block.asItem() == playerItem.getItem()) {
                            targetVariant = variant;
                            targetBlock = block;
                            break mainLoop;
                        }
                    }
                }

                if (targetVariant != null) {
                    if (world instanceof ServerWorld) {
                        ((ServerWorld) world).spawnParticles(ParticleTypes.HEART, pos.x, pos.y + getHeight(), pos.z, 1, 0.1, 0.1, 0.1, 0.1);
                        if (targetVariant != currentVariant)
                            ((ServerWorld) world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, targetBlock.getDefaultState()), pos.x, pos.y + getHeight() / 2, pos.z, 16, 0.1, 0.1, 0.1, 0.25);
                    }

                    if (targetVariant != currentVariant) {
                        playSound(MMOSounds.ENTITY_STONELING_EAT, 1F, 1F);
                        dataTracker.set(VARIANT, targetVariant.getIndex());
                    }

                    playSound(MMOSounds.ENTITY_STONELING_PURR, 1F, 1F + world.random.nextFloat());

                    heal(1);

                    if (!player.abilities.creativeMode) playerItem.decrement(1);

                    return ActionResult.SUCCESS;
                }

                return ActionResult.PASS;
            }

            ItemStack stonelingItem = dataTracker.get(CARRYING_ITEM);

            if (!stonelingItem.isEmpty() || !playerItem.isEmpty()) {
                player.setStackInHand(hand, stonelingItem.copy());
                dataTracker.set(CARRYING_ITEM, playerItem.copy());

                if (playerItem.isEmpty()) playSound(MMOSounds.ENTITY_STONELING_GIVE, 1F, 1F);
                else playSound(MMOSounds.ENTITY_STONELING_TAKE, 1F, 1F);
            }
        } else if (playerItem.getItem() == Items.DIAMOND) {
            heal(8);

            setPlayerMade(true);

            playSound(MMOSounds.ENTITY_STONELING_PURR, 1F, 1F + world.random.nextFloat());

            if (!player.abilities.creativeMode) playerItem.decrement(1);

            if (world instanceof ServerWorld)
                ((ServerWorld) world).spawnParticles(ParticleTypes.HEART, pos.x, pos.y + getHeight(), pos.z, 4, 0.1, 0.1, 0.1, 0.1);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        byte variant;
        if (entityData instanceof StonelingVariant) variant = ((StonelingVariant) entityData).getIndex();
        else variant = (byte) world.getRandom().nextInt(StonelingVariant.values().length);

        dataTracker.set(VARIANT, variant);
        dataTracker.set(HOLD_ANGLE, world.getRandom().nextFloat() * 90 - 45);

        if(!isTame && !world.isClient() && world instanceof ServerWorld) {
            LootManager lootManager = ((ServerWorld) world).getServer().getLootManager();
            List<ItemStack> items = lootManager
                    .getTable(CARRY_LOOT_TABLE)
                    .generateLoot(new LootContext.Builder((ServerWorld) world)
                            .build(LootContextTypes.EMPTY));

            if (!items.isEmpty()) dataTracker.set(CARRYING_ITEM, items.get(0));
        }

        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return damageSource == DamageSource.CACTUS || damageSource.isProjectile() || super.isInvulnerableTo(damageSource);
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public boolean canSpawn(WorldView world) {
        return world.intersectsEntities(this, VoxelShapes.cuboid(getBoundingBox()));
    }

    @Override
    public double getMountedHeightOffset() {
        return this.getHeight();
    }

    @Override
    public boolean canFly() { // Forge: isPushedByWater
        return true;
    }

    @Override
    protected int getNextAirUnderwater(int air) {
        return air;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }


    @Override
    protected void applyDamage(DamageSource source, float amount) {
        super.applyDamage(source, amount);

        if (isPlayerMade() && !this.world.isClient) {
            System.out.println("PLAYER MADE");
        }

        if(!isPlayerMade() && source.getSource() instanceof PlayerEntity) {
            startle();
            for (Entity entity : world.getOtherEntities(this, getBoundingBox().expand(16))) {
                if (entity instanceof StonelingEntity) {
                    StonelingEntity stoneling = (StonelingEntity) entity;
                    if (!stoneling.isPlayerMade() && stoneling.getVisibilityCache().canSee(this)) {
                        startle();
                    }
                }
            }
        }
    }

    public boolean isStartled() {
        return waryGoal.isStartled();
    }

    public void startle() {
        waryGoal.startle();
        Set<PrioritizedGoal> entries = Sets.newHashSet(((GoalSelectorAccessor) goalSelector).getGoals());

        for (PrioritizedGoal task : entries)
            if (task.getGoal() instanceof TemptGoal)
                goalSelector.remove(task.getGoal());
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);

        ItemStack stack = getCarryingItem();
        if(!stack.isEmpty()) dropStack(stack, 0F);
    }

    public void setPlayerMade(boolean value) {
        isTame = value;
    }

    public ItemStack getCarryingItem() {
        return dataTracker.get(CARRYING_ITEM);
    }

    public StonelingVariant getVariant() {
        return StonelingVariant.byIndex(dataTracker.get(VARIANT));
    }

    public float getItemAngle() {
        return dataTracker.get(HOLD_ANGLE);
    }

    public boolean isPlayerMade() {
        return isTame;
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);

        if(tag.contains(TAG_CARRYING_ITEM, 10)) {
            CompoundTag itemCmp = tag.getCompound(TAG_CARRYING_ITEM);
            ItemStack stack = ItemStack.fromTag(itemCmp);
            dataTracker.set(CARRYING_ITEM, stack);
        }

        dataTracker.set(VARIANT, tag.getByte(TAG_VARIANT));
        dataTracker.set(HOLD_ANGLE, tag.getFloat(TAG_HOLD_ANGLE));
        setPlayerMade(tag.getBoolean(TAG_PLAYER_MADE));
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);

        CompoundTag carryingItem = new CompoundTag();
        getCarryingItem().toTag(carryingItem);
        tag.put(TAG_CARRYING_ITEM, carryingItem);

        tag.putByte(TAG_VARIANT, getVariant().getIndex());
        tag.putFloat(TAG_HOLD_ANGLE, getItemAngle());
        tag.putBoolean(TAG_PLAYER_MADE, isPlayerMade());
    }

    @Override
    public boolean canSee(Entity entity) {
        Vec3d pos = getPos();
        Vec3d epos = entity.getPos();

        Vec3d origin = new Vec3d(pos.x, pos.y + getStandingEyeHeight(), pos.z);
        float otherEyes = entity.getStandingEyeHeight();
        for (float height = 0; height <= otherEyes; height += otherEyes / 8) {
            RaycastContext ctx = new RaycastContext(origin, epos.add(0, height, 0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this);
            if (this.world.raycast(ctx).getType() == HitResult.Type.MISS) return true;
        }

        return false;
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        BlockState state = world.getBlockState(new BlockPos(getPos()).down());
        if (state.getMaterial() != Material.STONE) return false;

        boolean overworld = ((World) world).getRegistryKey().getValue().toString().equals(new Identifier("overworld").toString());
        return overworld && super.canSpawn(world, spawnReason);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MMOSounds.ENTITY_STONELING_CRY;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return MMOSounds.ENTITY_STONELING_DIE;
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 1200;
    }
}
