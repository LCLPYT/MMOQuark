package work.lclpnet.mmoquark.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import work.lclpnet.mmocontent.block.ext.MMOBlock;

import net.minecraft.util.math.random.Random;

public class ChorusVegetationBlock extends MMOBlock implements Fertilizable {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 13, 14);

    private final boolean simple;

    public ChorusVegetationBlock(boolean simple) {
        super(Settings.of(Material.REPLACEABLE_PLANT)
                .noCollision()
                .breakInstantly()
                .sounds(BlockSoundGroup.GRASS)
                .ticksRandomly());

        this.simple = simple;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextDouble() < 0.2) teleport(pos, random, world, state);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        world.addParticle(ParticleTypes.PORTAL, pos.getX() + 0.2 + random.nextDouble() * 0.6, pos.getY() + 0.3, pos.getZ() + 0.2 + random.nextDouble() * 0.6, 0, 0, 0);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (simple && world instanceof ServerWorld && entity instanceof LivingEntity && !(entity instanceof EndermanEntity) && !(entity instanceof EndermiteEntity)) {
            BlockPos target = teleport(pos, world.random, (ServerWorld) world, state);

            if(target != null && world.random.nextDouble() < 0.01) {
                EndermiteEntity mite = new EndermiteEntity(EntityType.ENDERMITE, world);
                mite.setPosition(target.getX(), target.getY(), target.getZ());
                world.spawnEntity(mite);
            }
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);

        if (world instanceof ServerWorld) runAwayFromWater(pos, world.random, (ServerWorld) world, state);
    }

    private void runAwayFromWater(BlockPos pos, Random random, ServerWorld worldIn, BlockState state) {
        for (Direction d : Direction.values()) {
            BlockPos test = pos.offset(d);
            FluidState fluid = worldIn.getFluidState(test);
            if (fluid.getFluid() == Fluids.WATER || fluid.getFluid() == Fluids.FLOWING_WATER) {
                teleport(pos, random, worldIn, state, 8, 1);
                return;
            }
        }
    }

    private BlockPos teleport(BlockPos pos, Random random, ServerWorld worldIn, BlockState state) {
        return teleport(pos, random, worldIn, state, 4, 0.99);
    }

    private BlockPos teleport(BlockPos pos, Random random, ServerWorld worldIn, BlockState state, int range, double growthChance) {
        int xOff;
        int zOff;
        do {
            xOff = random.nextInt(range + 1) - (range / 2);
            zOff = random.nextInt(range + 1) - (range / 2);
        } while(xOff == 0 && zOff == 0);
        BlockPos newPos = pos.add(xOff, 10, zOff);

        for(int i = 0; i < 20; i++) {
            BlockState stateAt = worldIn.getBlockState(newPos);
            if(stateAt.getBlock() == Blocks.END_STONE)
                break;

            else newPos = newPos.down();
        }

        if(worldIn.getBlockState(newPos).getBlock() == Blocks.END_STONE && worldIn.getBlockState(newPos.up()).isAir()) {
            newPos = newPos.up();
            worldIn.setBlockState(newPos, state);

            if(random.nextDouble() < growthChance) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
                worldIn.spawnParticles(ParticleTypes.PORTAL, pos.getX() + 0.5, pos.getY() - 0.25, pos.getZ(), 50, 0.25, 0.25, 0.25, 1);
                worldIn.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.BLOCKS, 0.1F, 5F + random.nextFloat());
            }

            worldIn.spawnParticles(ParticleTypes.REVERSE_PORTAL, newPos.getX() + 0.5, newPos.getY() - 0.25, newPos.getZ(), 50, 0.25, 0.25, 0.25, 0.05);

            return newPos;
        }

        return null;
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        for (int i = 0; i < (3 + random.nextInt(3)); i++)
            teleport(pos, random, world, state, 10, 0);

        teleport(pos, random, world, state, 4, 1);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).getBlock() == Blocks.END_STONE;
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return (type == NavigationType.AIR && !this.collidable) || super.canPathfindThrough(state, world, pos, type);
    }
}
