package work.lclpnet.mmoblocks.block;

import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.mmoblocks.block.ext.MMOBlock;

import java.util.Arrays;
import java.util.Random;

public class CandleBlock extends MMOBlock implements Waterloggable {

    private static final VoxelShape SHAPE = Block.createCuboidShape(6F, 0F, 6F, 10F, 8F, 10F);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private final DyeColor color;

    public CandleBlock(DyeColor color) {
        super(Settings.of(Material.SUPPORTED, color.getMaterialColor())
                .strength(0.2F, 0.2F)
                .luminance(b -> b.get(WATERLOGGED) ? 0 : 14)
                .sounds(BlockSoundGroup.WOOL));

        this.color = color;

        setDefaultState(getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        if (state == null) throw new IllegalStateException("Placement state is null");

        return state.with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED))
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));

        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.getBlockTickScheduler().schedule(pos, this, 2);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        world.getBlockTickScheduler().schedule(pos, this, 2);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient) checkFallable(world, pos);
    }

    private void checkFallable(World world, BlockPos pos) {
        if (world.isClient || (!world.isAir(pos.down()) && (!FallingBlock.canFallThrough(world.getBlockState(pos.down())) || pos.getY() < 0))) return;

        FallingBlockEntity fallingblockentity = new FallingBlockEntity(world, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, world.getBlockState(pos));
        world.spawnEntity(fallingblockentity);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(WATERLOGGED)) return;

        final double x = pos.getX() + 0.5D, y = pos.getY() + 0.7D, z = pos.getZ() + 0.5D;

        for (DefaultParticleType defaultParticleType : Arrays.asList(ParticleTypes.SMOKE, ParticleTypes.FLAME))
            world.addParticle(defaultParticleType, x, y, z, 0.0D, 0.0D, 0.0D);
    }
}
