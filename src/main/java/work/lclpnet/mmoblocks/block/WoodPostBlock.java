package work.lclpnet.mmoblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.mmoblocks.util.Env;
import work.lclpnet.mmoblocks.util.MMORenderLayers;

public class WoodPostBlock extends MMOBlock implements Waterloggable {

    private static final VoxelShape SHAPE_X = Block.createCuboidShape(0F, 6F, 6F, 16F, 10F, 10F);
    private static final VoxelShape SHAPE_Y = Block.createCuboidShape(6F, 0F, 6F, 10F, 16F, 10F);
    private static final VoxelShape SHAPE_Z = Block.createCuboidShape(6F, 6F, 0F, 10F, 10F, 16F);

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;

    public static final BooleanProperty[] CHAINED = new BooleanProperty[] {
            BooleanProperty.of("chain_down"),
            BooleanProperty.of("chain_up"),
            BooleanProperty.of("chain_north"),
            BooleanProperty.of("chain_south"),
            BooleanProperty.of("chain_west"),
            BooleanProperty.of("chain_east")
    };

    public Block strippedBlock = null;

    public WoodPostBlock(Block parent, boolean nether) {
        super(Settings.copy(parent).sounds(nether ? BlockSoundGroup.NETHER_STEM : BlockSoundGroup.WOOD));

        BlockState state = stateManager.getDefaultState().with(WATERLOGGED, false).with(AXIS, Direction.Axis.Y);
        for(BooleanProperty prop : CHAINED)
            state = state.with(prop, false);
        setDefaultState(state);

        if (Env.isClient()) registerRenderLayer();
    }

    @Environment(EnvType.CLIENT)
    protected void registerRenderLayer() {
        MMORenderLayers.setBlockRenderType(this, RenderLayer.getCutout());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch(state.get(AXIS)) {
            case X: return SHAPE_X;
            case Y: return SHAPE_Y;
            default: return SHAPE_Z;
        }
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getState(ctx.getWorld(), ctx.getBlockPos(), ctx.getSide().getAxis());
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);

        BlockState newState = getState(world, pos, state.get(AXIS));
        if (!newState.equals(state)) world.setBlockState(pos, newState);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, AXIS);
        for (BooleanProperty prop : CHAINED) builder.add(prop);
    }

    private BlockState getState(World world, BlockPos pos, Direction.Axis axis) {
        BlockState state = getDefaultState().with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER).with(AXIS, axis);

        for (Direction d : Direction.values()) {
            if (d.getAxis() == axis) continue;

            BlockState sideState = world.getBlockState(pos.offset(d));
            if ((sideState.getBlock() instanceof ChainBlock && sideState.get(Properties.AXIS) == d.getAxis())
                    || (d == Direction.DOWN && sideState.getBlock() instanceof LanternBlock && sideState.get(LanternBlock.HANGING))) {
                BooleanProperty prop = CHAINED[d.ordinal()];
                state = state.with(prop, true);
            }
        }

        return state;
    }
}
