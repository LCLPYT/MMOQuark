package work.lclpnet.mmoquark.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.mmocontent.block.ext.MMOBlock;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmocontent.util.Env;
import work.lclpnet.mmoquark.blockentity.PipeBlockEntity;

import java.util.HashSet;
import java.util.Set;

public class PipeBlock extends MMOBlock implements Waterloggable, BlockEntityProvider {

    private static final VoxelShape CENTER_SHAPE = VoxelShapes.cuboid(0.3125, 0.3125, 0.3125, 0.6875, 0.6875, 0.6875);

    private static final VoxelShape DOWN_SHAPE = VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.6875, 0.6875);
    private static final VoxelShape UP_SHAPE = VoxelShapes.cuboid(0.3125, 0.3125, 0.3125, 0.6875, 1, 0.6875);
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.cuboid(0.3125, 0.3125, 0, 0.6875, 0.6875, 0.6875);
    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.cuboid(0.3125, 0.3125, 0.3125, 0.6875, 0.6875, 1);
    private static final VoxelShape WEST_SHAPE = VoxelShapes.cuboid(0, 0.3125, 0.3125, 0.6875, 0.6875, 0.6875);
    private static final VoxelShape EAST_SHAPE = VoxelShapes.cuboid(0.3125, 0.3125, 0.3125, 1, 0.6875, 0.6875);

    public static final BooleanProperty DOWN = BooleanProperty.of("down");
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty WEST = BooleanProperty.of("west");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final BooleanProperty[] CONNECTIONS = new BooleanProperty[] {
            DOWN, UP, NORTH, SOUTH, WEST, EAST
    };

    private static final VoxelShape[] SIDE_BOXES = new VoxelShape[] {
            DOWN_SHAPE, UP_SHAPE, NORTH_SHAPE, SOUTH_SHAPE, WEST_SHAPE, EAST_SHAPE
    };

    private static final VoxelShape[] shapeCache = new VoxelShape[64];

    public PipeBlock() {
        super(Settings.of(Material.GLASS)
                .strength(3F, 10F)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque());

        setDefaultState(getDefaultState()
                .with(DOWN, false).with(UP, false)
                .with(NORTH, false).with(SOUTH, false)
                .with(WEST, false).with(EAST, false)
                .with(WATERLOGGED, false));

        if (Env.isClient()) registerRenderLayer();
    }

    @Environment(EnvType.CLIENT)
    protected void registerRenderLayer() {
        MMORenderLayers.setBlockRenderType(this, RenderLayer.getCutout());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);

        // fix pipes if they're ruined
        if (stack.getItem() == Items.STICK) {
            Set<BlockPos> found = new HashSet<>();
            boolean fixedAny = false;

            Set<BlockPos> candidates = new HashSet<>();
            Set<BlockPos> newCandidates = new HashSet<>();

            candidates.add(pos);
            do {
                for (BlockPos cand : candidates) {
                    for (Direction d : Direction.values()) {
                        BlockPos offPos = cand.offset(d);
                        BlockState offState = world.getBlockState(offPos);
                        if (offState.getBlock() == this && !candidates.contains(offPos) && !found.contains(offPos))
                            newCandidates.add(offPos);
                    }

                    BlockState curr = world.getBlockState(cand);
                    BlockState target = getTargetState(world, cand, curr.get(WATERLOGGED));
                    if (!target.equals(curr)) {
                        fixedAny = true;
                        world.setBlockState(cand, target, 2 | 4);
                    }
                }

                found.addAll(candidates);
                candidates = newCandidates;
                newCandidates = new HashSet<>();
            } while (!candidates.isEmpty());

            if (fixedAny) return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        BlockState targetState = getTargetState(world, pos, state.get(WATERLOGGED));
        if (!targetState.equals(state)) world.setBlockState(pos, targetState, 2 | 4);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getTargetState(ctx.getWorld(), ctx.getBlockPos(), ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    private BlockState getTargetState(World worldIn, BlockPos pos, boolean waterlog) {
        BlockState newState = getDefaultState();
        newState = newState.with(WATERLOGGED, waterlog);

        for(Direction facing : Direction.values()) {
            BooleanProperty prop = CONNECTIONS[facing.ordinal()];
            PipeBlockEntity.ConnectionType type = PipeBlockEntity.getConnectionTo(worldIn, pos, facing);

            newState = newState.with(prop, type.isSolid);
        }

        return newState;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int index = 0;
        for (Direction dir : Direction.values()) {
            int ord = dir.ordinal();
            if (state.get(CONNECTIONS[ord])) index += (1 << ord);
        }

        VoxelShape cached = shapeCache[index];
        if (cached == null) {
            VoxelShape currShape = CENTER_SHAPE;

            for (Direction dir : Direction.values()) {
                boolean connected = isConnected(state, dir);
                if (connected) currShape = VoxelShapes.union(currShape, SIDE_BOXES[dir.ordinal()]);
            }

            shapeCache[index] = currShape;
            cached = currShape;
        }

        return cached;
    }

    public static boolean isConnected(BlockState state, Direction side) {
        BooleanProperty prop = CONNECTIONS[side.ordinal()];
        return state.get(prop);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, SOUTH, WEST, EAST, WATERLOGGED);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new PipeBlockEntity();
    }
}
