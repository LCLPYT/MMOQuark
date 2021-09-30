package work.lclpnet.mmoblocks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;

public class MMOBlocks implements ModInitializer {

	public static final String MOD_ID = "mmoblocks";

	@Override
	public void onInitialize() {
		new MMOBlockRegistrar(FabricBlockSettings.of(Material.STONE).strength(2.0F))
				.withSlab().withStairs().withWall().register("weeping_blackstone_bricks");
	}
}
