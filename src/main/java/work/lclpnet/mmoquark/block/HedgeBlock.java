package work.lclpnet.mmoquark.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import work.lclpnet.mmocontent.block.ext.MMOFenceBlock;
import work.lclpnet.mmoquark.module.HedgesModule;

public class HedgeBlock extends MMOFenceBlock {

	public final Block leaf;

	public static final BooleanProperty EXTEND = BooleanProperty.of("extend");

	public HedgeBlock(Block fence, Block leaf) {
		super(AbstractBlock.Settings.copy(fence));

		this.leaf = leaf;

		setDefaultState(getDefaultState().with(EXTEND, false));
	}

	@Override
	public boolean canConnect(BlockState state, boolean isSideSolid, Direction direction) {
		return state.getBlock().isIn(HedgesModule.hedgesTag);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		WorldAccess world = ctx.getWorld();
		BlockPos blockpos = ctx.getBlockPos();
		BlockPos down = blockpos.down();
		BlockState downState = world.getBlockState(down);

		BlockState placementState = super.getPlacementState(ctx);
		return placementState == null ? null : placementState.with(EXTEND, downState.getBlock() instanceof HedgeBlock);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState stateIn, Direction facing, BlockState facingState, WorldAccess worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) worldIn.getFluidTickScheduler().schedule(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));

		if (facing == Direction.DOWN) return stateIn.with(EXTEND, facingState.getBlock() instanceof HedgeBlock);
		else return super.getStateForNeighborUpdate(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(EXTEND);
	}
}
