package work.lclpnet.mmoblocks;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import work.lclpnet.mmoblocks.block.MMOBlock;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;

public class MMOBlocks implements ModInitializer {

	public static final String MOD_ID = "mmoblocks";

	@Override
	public void onInitialize() {
		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.BLACKSTONE))
				.withSlab().withStairs().withWall().register("weeping_blackstone_bricks");
		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.NETHER_BRICKS))
				.withSlab().withStairs().withWall().register("charred_nether_bricks");
		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK))
				.withSlab().withStairs().register("iron_plate");
		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK))
				.withSlab().withStairs().register("rusty_iron_plate");
	}
}
