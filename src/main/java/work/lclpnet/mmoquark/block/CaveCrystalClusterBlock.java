package work.lclpnet.mmoquark.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.mmocontent.block.BlockStatesUtil;
import work.lclpnet.mmocontent.block.ext.MMOBlock;

public class CaveCrystalClusterBlock extends MMOBlock {

    public static final EnumProperty<Direction> FACING = Properties.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    protected final VoxelShape northShape = Block.createCuboidShape(3, 3, 9, 13, 13, 16);
    protected final VoxelShape southShape = Block.createCuboidShape(3, 3, 0, 13, 13, 7);
    protected final VoxelShape eastShape  = Block.createCuboidShape(0, 3, 3, 7, 13, 13);
    protected final VoxelShape westShape  = Block.createCuboidShape(9, 3, 3, 16, 13, 13);
    protected final VoxelShape upShape    = Block.createCuboidShape(3, 0, 3, 13, 7, 13);
    protected final VoxelShape downShape  = Block.createCuboidShape(3, 9, 3, 13, 16, 13);

    public CaveCrystalClusterBlock(Block base) {
        super(BlockStatesUtil.copyState(base));

        setDefaultState(getDefaultState().with(FACING, Direction.DOWN).with(WATERLOGGED, false));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if(!canPlaceAt(state, world, pos)) world.breakBlock(pos, true);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction dir = state.get(FACING);
        BlockPos off = pos.offset(dir.getOpposite());
        BlockState offState = world.getBlockState(off);
        return offState.isSideSolidFullSquare(world, off, dir);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        switch (direction) {
            case NORTH -> {
                return this.northShape;
            }
            case SOUTH -> {
                return this.southShape;
            }
            case EAST -> {
                return this.eastShape;
            }
            case WEST -> {
                return this.westShape;
            }
            case DOWN -> {
                return this.downShape;
            }
            default -> {
                return this.upShape;
            }
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState()
                .with(FACING, ctx.getSide())
                .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return type == NavigationType.WATER && world.getFluidState(pos).isIn(FluidTags.WATER);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }
}
