package work.lclpnet.mmoquark.entity;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.mmoquark.entity.ai.RaveGoal;
import work.lclpnet.mmoquark.module.CrabsModule;
import work.lclpnet.mmoquark.util.MiscUtil;

import java.util.Optional;

public class CrabEntity extends AnimalEntity {

    public static final Identifier CRAB_LOOT_TABLE = new Identifier("quark", "entities/crab");

    private static final TrackedData<Float> SIZE_MODIFIER = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(CrabEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private int lightningCooldown;
    private Ingredient temptationItems;

    private boolean noSpike;
    private boolean crabRave;
    private BlockPos jukeboxPosition;

    public CrabEntity(EntityType<? extends CrabEntity> entityType, World world) {
        this(entityType, world, 1F);
    }

    public CrabEntity(EntityType<? extends CrabEntity> entityType, World world, float sizeModifier) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
        if (sizeModifier != 1)
            dataTracker.set(SIZE_MODIFIER, sizeModifier);
    }

    public static void rave(WorldAccess world, BlockPos pos, boolean raving) {
        for (CrabEntity crab : world.getNonSpectatingEntities(CrabEntity.class, (new Box(pos)).expand(3.0D)))
            crab.party(pos, raving);
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos.down()).isIn(CrabsModule.crabSpawnableTag) ? 10.0F : world.getBrightness(pos) - 0.5F;
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.ARTHROPOD;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();

        dataTracker.startTracking(SIZE_MODIFIER, 1F);
        dataTracker.startTracking(VARIANT, -1);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return super.getActiveEyeHeight(pose, dimensions);
    }

    public float getSizeModifier() {
        return dataTracker.get(SIZE_MODIFIER);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25D));
        this.goalSelector.add(2, new RaveGoal(this));
        this.goalSelector.add(3, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(4, new TemptGoal(this, 1.2D, getTemptationItems(), false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
                .add(EntityAttributes.GENERIC_ARMOR, 3.0D)
                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 2.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    public void tick() {
        super.tick();

        // maybe move elsewhere
        if (!world.isClient && dataTracker.get(VARIANT) == -1) {
            int variant = 0;
            if (random.nextBoolean()) variant += random.nextInt(2) + 1;

            dataTracker.set(VARIANT, variant);
        }

        if (isTouchingWater()) stepHeight = 1F;
        else stepHeight = 0.6F;

        if (lightningCooldown > 0) {
            lightningCooldown--;
            extinguish();
        }

        Vec3d pos = getPos();
        if (isRaving() && (
                jukeboxPosition == null || jukeboxPosition.getSquaredDistanceFromCenter(pos.x, pos.y, pos.z) > 24.0D
                        || world.getBlockState(jukeboxPosition).getBlock() != Blocks.JUKEBOX
        )) {
            party(null, false);
        }

        if (isRaving() && world.isClient && age % 10 == 0) {
            BlockPos below = getBlockPos().down();
            BlockState belowState = world.getBlockState(below);
            if (belowState.getMaterial() == Material.AGGREGATE)
                world.syncWorldEvent(2001, below, Block.getRawIdFromState(belowState));
        }
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return super.getDimensions(pose).scaled(this.getSizeModifier());
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    protected int getNextAirUnderwater(int air) {
        return air;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return super.isInvulnerableTo(damageSource) ||
                damageSource == DamageSource.LIGHTNING_BOLT ||
                getSizeModifier() > 1 && damageSource.isFire();
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        if (lightningCooldown > 0 || world.isClient) return;

        float sizeMod = getSizeModifier();
        if (sizeMod <= 15) {
            Optional.ofNullable(this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH))
                    .ifPresent(attr -> attr.addPersistentModifier(new EntityAttributeModifier("Lightning Bonus", 0.5, EntityAttributeModifier.Operation.ADDITION)));
            Optional.ofNullable(this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED))
                    .ifPresent(attr -> attr.addPersistentModifier(new EntityAttributeModifier("Lightning Debuff", -0.05, EntityAttributeModifier.Operation.ADDITION)));
            Optional.ofNullable(this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR))
                    .ifPresent(attr -> attr.addPersistentModifier(new EntityAttributeModifier("Lightning Bonus", 0.125, EntityAttributeModifier.Operation.ADDITION)));

            float sizeModifier = Math.min(sizeMod + 1, 16);
            this.dataTracker.set(SIZE_MODIFIER, sizeModifier);
            calculateDimensions();

            lightningCooldown = 150;
        }
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        if (getSizeModifier() <= 1) super.pushAwayFrom(entity);
    }

    @Override
    protected void pushAway(Entity entity) {
        super.pushAway(entity);
        if (world.getDifficulty() != Difficulty.PEACEFUL && !noSpike) {
            if (entity instanceof LivingEntity && !(entity instanceof CrabEntity))
                entity.damage(DamageSource.CACTUS, 1f);
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return !stack.isEmpty() && getTemptationItems().test(stack);
    }

    private Ingredient getTemptationItems() {
        if(temptationItems == null)
            temptationItems =  MiscUtil.mergeIngredients(Lists.newArrayList(
                    Ingredient.ofItems(Items.WHEAT, Items.CHICKEN),
                    Ingredient.fromTag(ItemTags.FISHES)
            ));

        return temptationItems;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return new CrabEntity(CrabsModule.crabType, world);
    }

    @Override
    protected Identifier getLootTableId() {
        return CRAB_LOOT_TABLE;
    }

    public int getVariant() {
        return Math.max(0, dataTracker.get(VARIANT));
    }

    public void party(BlockPos pos, boolean isPartying) {
        // A separate method, due to setPartying being side-only.
        jukeboxPosition = pos;
        crabRave = isPartying;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void setNearbySongPlaying(BlockPos pos, boolean isPartying) {
        party(pos, isPartying);
    }

    public boolean isRaving() {
        return crabRave;
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (data.equals(SIZE_MODIFIER)) calculateDimensions();

        super.onTrackedDataSet(data);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putFloat("EnemyCrabRating", getSizeModifier());
        tag.putInt("LightningCooldown", lightningCooldown);
        tag.putInt("Variant", dataTracker.get(VARIANT));
        tag.putBoolean("NoSpike", noSpike);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);

        lightningCooldown = tag.getInt("LightningCooldown");
        noSpike = tag.getBoolean("NoSpike");

        if (tag.contains("EnemyCrabRating")) {
            float sizeModifier = tag.getFloat("EnemyCrabRating");
            dataTracker.set(SIZE_MODIFIER, sizeModifier);
        }

        if (tag.contains("Variant")) dataTracker.set(VARIANT, tag.getInt("Variant"));
    }
}
