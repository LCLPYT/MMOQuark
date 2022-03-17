package work.lclpnet.mmoquark.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.mmocontent.block.ext.MMOBlock;

import java.util.Locale;

public class SpeleothemBlock extends MMOBlock implements Waterloggable {

    public static final EnumProperty<SpeleothemSize> SIZE = EnumProperty.of("size", SpeleothemSize.class);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public SpeleothemBlock(Block parent, boolean nether) {
        super(Settings.copy(parent)
                .strength(nether ? 0.4F : 1.5F, nether? 0.4F : 1.5F)
                .nonOpaque());

        setDefaultState(getDefaultState().with(SIZE, SpeleothemSize.BIG).with(WATERLOGGED, false));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return getBearing(world, pos) > 0;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.getBlock() instanceof SpeleothemBlock) {
            boolean invalidSpot = getBearing(world, pos) < state.get(SIZE).strength + 1;
            if (invalidSpot) {
                if (state.get(WATERLOGGED)) {
                    state = Blocks.WATER.getDefaultState();
                } else {
                    state = Blocks.AIR.getDefaultState();
                }
            }
        }
        return state;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        SpeleothemSize size = SpeleothemSize.values()[Math.max(0, getBearing(ctx.getWorld(), ctx.getBlockPos()) - 1)];
        return getDefaultState().with(SIZE, size).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        int size = state.get(SIZE).strength;
        if (getBearing(world, pos) < size + 1) world.breakBlock(pos, false);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return type == NavigationType.WATER && world.getFluidState(pos).isIn(FluidTags.WATER);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float distance) {
        if (world.getBlockState(pos).get(SIZE) == SpeleothemSize.SMALL) entity.handleFallDamage(distance, 1.5F, DamageSource.FALL);
        else super.onLandedUpon(world, state, pos, entity, distance);
    }

    private int getBearing(WorldView world, BlockPos pos) {
        return Math.max(getStrength(world, pos.down()), getStrength(world, pos.up()));
    }

    private int getStrength(WorldView world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.isOpaque()) return 3;
        else if (state.getEntries().containsKey(SIZE)) return state.get(SIZE).strength;
        else return 0;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(SIZE).shape;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SIZE, WATERLOGGED);
    }

    public enum SpeleothemSize implements StringIdentifiable {

        SMALL(0, 2),
        MEDIUM(1, 4),
        BIG(2, 8);

        SpeleothemSize(int strength, int width) {
            this.strength = strength;

            int pad = (16 - width) / 2;
            shape = Block.createCuboidShape(pad, 0, pad, 16 - pad, 16, 16 - pad);
        }

        public final int strength;
        public final VoxelShape shape;

        @Override
        public String asString() {
            return name().toLowerCase(Locale.ROOT);
        }

    }
}
