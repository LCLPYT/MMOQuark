package work.lclpnet.mmoblocks;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;

public class MMOBlocks implements ModInitializer {

	public static final String MOD_ID = "mmoblocks";

	@Override
	public void onInitialize() {
		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.BLACKSTONE))
				.withSlab().withStairs().withWall().register("weeping_blackstone_bricks");
	}
}
