package work.lclpnet.mmoquark.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.mmoquark.block.ext.MMOBlock;
import work.lclpnet.mmoquark.blockentity.FeedingTroughBlockEntity;

public class FeedingTroughBlock extends MMOBlock implements BlockEntityProvider {

    private static final BlockSoundGroup WOOD_WITH_PLANT_STEP = new BlockSoundGroup(1.0F, 1.0F, SoundEvents.BLOCK_WOOD_BREAK, SoundEvents.BLOCK_GRASS_STEP, SoundEvents.BLOCK_WOOD_PLACE, SoundEvents.BLOCK_WOOD_HIT, SoundEvents.BLOCK_WOOD_FALL);

    public static BooleanProperty FULL = BooleanProperty.of("full");

    public static final VoxelShape CUBOID_SHAPE = createCuboidShape(0, 0, 0, 16, 8, 16);
    public static final VoxelShape EMPTY_SHAPE = VoxelShapes.combineAndSimplify(CUBOID_SHAPE,
            createCuboidShape(2, 2, 2, 14, 8, 14), BooleanBiFunction.ONLY_FIRST);

    public static final VoxelShape FULL_SHAPE = VoxelShapes.combineAndSimplify(CUBOID_SHAPE,
            createCuboidShape(2, 6, 2, 14, 8, 14), BooleanBiFunction.ONLY_FIRST);

    public FeedingTroughBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FULL, false));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return EMPTY_SHAPE;
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return CUBOID_SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(FULL) ? FULL_SHAPE : EMPTY_SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FULL);
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState state) {
        if (state.get(FULL)) return WOOD_WITH_PLANT_STEP;
        return super.getSoundGroup(state);
    }

    @Override
    public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
        if (world.getBlockState(pos).get(FULL)) entity.handleFallDamage(distance, 0.2F);
        else super.onLandedUpon(world, pos, entity, distance);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof FeedingTroughBlockEntity) {
                ItemScatterer.spawn(world, pos, (FeedingTroughBlockEntity) tile);
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new FeedingTroughBlockEntity();
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory container = this.createScreenHandlerFactory(state, world, pos);
            if (container != null) player.openHandledScreen(container);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onSyncedBlockEvent(state, world, pos, type, data);
        BlockEntity tile = world.getBlockEntity(pos);
        return tile != null && tile.onSyncedBlockEvent(type, data);
    }

    @Nullable
    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity tile = world.getBlockEntity(pos);
        return tile instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory) tile : null;
    }
}
