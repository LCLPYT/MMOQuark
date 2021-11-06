package work.lclpnet.mmoblocks.block;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import work.lclpnet.mmoblocks.block.ext.MMOBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class VerticalSlabBlock extends MMOBlock implements Waterloggable {

    public static final EnumProperty<VerticalSlabType> TYPE = EnumProperty.of("type", VerticalSlabType.class);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

//    public final Block parent;

    public VerticalSlabBlock(Block parent) {
        super(Settings.copy(parent));
//        this.parent = parent;

        if(!(parent instanceof SlabBlock)) throw new IllegalArgumentException("Can't rotate a non-slab block into a vertical slab.");

        setDefaultState(getDefaultState().with(TYPE, VerticalSlabType.NORTH).with(WATERLOGGED, false));
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.get(TYPE) == VerticalSlabType.DOUBLE ? state : state.with(TYPE, VerticalSlabType.fromDirection(rotation.rotate(state.get(TYPE).direction)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirrorIn) {
        VerticalSlabType type = state.get(TYPE);
        if(type == VerticalSlabType.DOUBLE || mirrorIn == BlockMirror.NONE)
            return state;

        if((mirrorIn == BlockMirror.LEFT_RIGHT && type.direction.getAxis() == Direction.Axis.Z)
                || (mirrorIn == BlockMirror.FRONT_BACK && type.direction.getAxis() == Direction.Axis.X))
            return state.with(TYPE, VerticalSlabType.fromDirection(state.get(TYPE).direction.getOpposite()));

        return state;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return state.get(TYPE) != VerticalSlabType.DOUBLE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    @Nonnull
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(TYPE).shape;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockPos blockpos = context.getBlockPos();
        BlockState blockstate = context.getWorld().getBlockState(blockpos);
        if(blockstate.getBlock() == this)
            return blockstate.with(TYPE, VerticalSlabType.DOUBLE).with(WATERLOGGED, false);

        FluidState fluid = context.getWorld().getFluidState(blockpos);
        BlockState retState = getDefaultState().with(WATERLOGGED, fluid.getFluid() == Fluids.WATER);
        Direction direction = getDirectionForPlacement(context);
        VerticalSlabType type = VerticalSlabType.fromDirection(direction);

        return retState.with(TYPE, type);
    }

    private Direction getDirectionForPlacement(ItemPlacementContext context) {
        Direction direction = context.getSide();
        if(direction.getAxis() != Direction.Axis.Y)
            return direction;

        BlockPos pos = context.getBlockPos();
        Vec3d vec = context.getHitPos().subtract(new Vec3d(pos.getX(), pos.getY(), pos.getZ())).subtract(0.5, 0, 0.5);
        double angle = Math.atan2(vec.x, vec.z) * -180.0 / Math.PI;
        return Direction.fromRotation(angle).getOpposite();
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        ItemStack itemstack = context.getStack();
        VerticalSlabType slabtype = state.get(TYPE);
        return slabtype != VerticalSlabType.DOUBLE && itemstack.getItem() == this.asItem()  &&
                (context.canReplaceExisting() && (context.getSide() == slabtype.direction && getDirectionForPlacement(context) == slabtype.direction)
                        || (!context.canReplaceExisting() && context.getSide().getAxis() != slabtype.direction.getAxis()));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean tryFillWithFluid(@Nonnull WorldAccess worldIn, @Nonnull BlockPos pos, BlockState state, @Nonnull FluidState fluidStateIn) {
        return state.get(TYPE) != VerticalSlabType.DOUBLE && Waterloggable.super.tryFillWithFluid(worldIn, pos, state, fluidStateIn);
    }

    @Override
    public boolean canFillWithFluid(BlockView worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return state.get(TYPE) != VerticalSlabType.DOUBLE && Waterloggable.super.canFillWithFluid(worldIn, pos, state, fluidIn);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if(state.get(WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public boolean canPathfindThrough(@Nonnull BlockState state, @Nonnull BlockView worldIn, @Nonnull BlockPos pos, NavigationType type) {
        return type == NavigationType.WATER && worldIn.getFluidState(pos).isIn(FluidTags.WATER);
    }

    public enum VerticalSlabType implements StringIdentifiable {
        NORTH(Direction.NORTH),
        SOUTH(Direction.SOUTH),
        WEST(Direction.WEST),
        EAST(Direction.EAST),
        DOUBLE(null);

        private final String name;
        public final Direction direction;
        public final VoxelShape shape;

        VerticalSlabType(Direction direction) {
            this.name = direction == null ? "double" : direction.getName();
            this.direction = direction;

            if(direction == null)
                shape = VoxelShapes.fullCube();
            else {
                double min = 0;
                double max = 8;
                if(direction.getDirection() == Direction.AxisDirection.NEGATIVE) {
                    min = 8;
                    max = 16;
                }

                if(direction.getAxis() == Direction.Axis.X)
                    shape = Block.createCuboidShape(min, 0, 0, max, 16, 16);
                else shape = Block.createCuboidShape(0, 0, min, 16, 16, max);
            }
        }

        @Override
        public String toString() {
            return name;
        }

        @Nonnull
        @Override
        public String asString() {
            return name;
        }

        public static VerticalSlabType fromDirection(Direction direction) {
            for(VerticalSlabType type : VerticalSlabType.values())
                if(type.direction != null && direction == type.direction)
                    return type;

            return null;
        }
    }
}
