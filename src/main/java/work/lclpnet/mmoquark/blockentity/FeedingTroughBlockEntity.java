package work.lclpnet.mmoquark.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import work.lclpnet.mmoquark.block.FeedingTroughBlock;
import work.lclpnet.mmoquark.module.FeedingTroughModule;

import java.util.List;
import net.minecraft.util.math.random.Random;

public class FeedingTroughBlockEntity extends LootableContainerBlockEntity  {

    private DefaultedList<ItemStack> stacks;

    private int cooldown = 0;
    private long internalRng = 0;

    protected FeedingTroughBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.stacks = DefaultedList.ofSize(9, ItemStack.EMPTY);
    }

    public FeedingTroughBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(FeedingTroughModule.tileEntityType, blockPos, blockState);
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.stacks;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.stacks = list;
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("quark.container.feeding_trough");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new Generic3x3ContainerScreenHandler(syncId, playerInventory, this);
    }

    private Random getSpecialRand() {
        Random specialRand = Random.create(internalRng);
        internalRng = specialRand.nextLong();
        return specialRand;
    }

    @Override
    public int size() {
        return 9;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size(); i++)
            if (!getStack(i).isEmpty())
                return false;

        return true;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);

        this.cooldown = tag.getInt("Cooldown");
        this.internalRng = tag.getLong("rng");
        this.stacks = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(tag)) Inventories.readNbt(tag, this.stacks);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FeedingTroughBlockEntity be) {
        if (world != null && !world.isClient) {
            if (be.cooldown > 0) {
                be.cooldown--;
            } else {
                be.cooldown = 30; // minimize aabb calls
                List<AnimalEntity> animals = world.getNonSpectatingEntities(AnimalEntity.class, new Box(pos).expand(1.5, 0, 1.5).shrink(0, 0.75, 0));

                for (AnimalEntity creature : animals) {
                    if (creature.canEat() && creature.getBreedingAge() == 0) {
                        for (int i = 0; i < be.size(); i++) {
                            ItemStack stack = be.getStack(i);
                            if (creature.isBreedingItem(stack)) {
                                creature.playSound(creature.getEatSound(stack), 0.5F + 0.5F * world.random.nextInt(2), (world.random.nextFloat() - world.random.nextFloat()) * 0.2F + 1.0F);
                                be.addItemParticles(creature, stack, 16);

                                if (be.getSpecialRand().nextFloat() < 0.333333F) {
                                    List<AnimalEntity> animalsAround = world.getNonSpectatingEntities(AnimalEntity.class, new Box(pos).expand(10));
                                    if (animalsAround.size() <= 32) creature.lovePlayer(null);
                                }

                                stack.decrement(1);
                                be.markDirty();

                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
        BlockState state = getCachedState();
        if (world != null && state.getBlock() instanceof FeedingTroughBlock) {
            boolean full = state.get(FeedingTroughBlock.FULL);
            boolean shouldBeFull = !isEmpty();

            if (full != shouldBeFull) world.setBlockState(pos, state.with(FeedingTroughBlock.FULL, shouldBeFull), 2);
        }
    }

    private void addItemParticles(Entity entity, ItemStack stack, int count) {
        for(int i = 0; i < count; ++i) {
            Vec3d direction = new Vec3d((entity.world.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            direction = direction.rotateX(-entity.getPitch() * ((float)Math.PI / 180F));
            direction = direction.rotateY(-entity.getYaw() * ((float)Math.PI / 180F));
            double yVelocity = (-entity.world.random.nextFloat()) * 0.6D - 0.3D;
            Vec3d position = new Vec3d((entity.world.random.nextFloat() - 0.5D) * 0.3D, yVelocity, 0.6D);
            Vec3d entityPos = entity.getPos();
            position = position.rotateX(-entity.getPitch() * ((float)Math.PI / 180F));
            position = position.rotateY(-entity.getYaw() * ((float)Math.PI / 180F));
            position = position.add(entityPos.x, entityPos.y + entity.getStandingEyeHeight(), entityPos.z);
            if (this.world instanceof ServerWorld) {
                ((ServerWorld)this.world).spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), position.x, position.y, position.z, 1, direction.x, direction.y + 0.05D, direction.z, 0.0D);
            }
            else if (this.world != null) {
                this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, stack), position.x, position.y, position.z, direction.x, direction.y + 0.05D, direction.z);
            }
        }
    }
}
