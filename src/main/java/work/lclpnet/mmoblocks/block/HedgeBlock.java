package work.lclpnet.mmoblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import work.lclpnet.mmoblocks.MMOBlocksClient;
import work.lclpnet.mmoblocks.module.WoodExtraModule;
import work.lclpnet.mmoblocks.util.Env;

public class HedgeBlock extends FenceBlock {

	final Block leaf;

	public static final BooleanProperty EXTEND = BooleanProperty.of("extend");

	public HedgeBlock(Block fence, Block leaf) {
		super(AbstractBlock.Settings.copy(fence));

		this.leaf = leaf;

		/*if (leaf instanceof BlossomLeavesBlock) {
			String colorName = leaf.getRegistryName().getPath().replaceAll("_blossom_leaves", "");
  		    RegistryHelper.registerBlock(this, colorName + "_blossom_hedge");
		} else {
			RegistryHelper.registerBlock(this, fence.getRegistryName().getPath().replaceAll("_fence", "_hedge"));
		}*/
		
		setDefaultState(getDefaultState().with(EXTEND, false));
		if (Env.isClient()) registerRenderLayer();
	}

	@Environment(EnvType.CLIENT)
	private void registerRenderLayer() {
		MMOBlocksClient.setBlockRenderType(this, RenderLayer.getCutout());
	}
	
	@Override
	public boolean canConnect(BlockState state, boolean isSideSolid, Direction direction) {
		return state.getBlock().isIn(WoodExtraModule.hedgesTag);
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
