package work.lclpnet.mmoblocks;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.VerticalSlabBlock;

public class MMOBlocks implements ModInitializer {

	public static final String MOD_ID = "mmoblocks";

	@Override
	public void onInitialize() {
		registerVanillaVerticalSlabs();

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.BLACKSTONE))
				.withSlab().withStairs().withWall().withVerticalSlab().register("weeping_blackstone_bricks");
		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.NETHER_BRICKS))
				.withSlab().withStairs().withWall().register("charred_nether_bricks");
		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK))
				.withSlab().withStairs().register("iron_plate");
		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK))
				.withSlab().withStairs().register("rusty_iron_plate");
		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.STONE))
				.withSlab().withStairs().withWall().register("marble");
		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.STONE))
				.withSlab().withStairs().withWall().register("marble_bricks");
		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.STONE))
				.withSlab().withStairs().register("polished_marble");
		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS))
				.withSlab().withStairs().withWall().register("cobblestone_bricks");
		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS))
				.withSlab().withWall().withStairs().register("mossy_cobblestone_bricks");
	}

	private void registerVanillaVerticalSlabs() {
		ImmutableSet.of(Blocks.ACACIA_SLAB, Blocks.ANDESITE_SLAB, Blocks.BIRCH_SLAB, Blocks.BRICK_SLAB, Blocks.COBBLESTONE_SLAB,
						Blocks.CUT_RED_SANDSTONE_SLAB, Blocks.CUT_SANDSTONE_SLAB, Blocks.DARK_OAK_SLAB, Blocks.DARK_PRISMARINE_SLAB, Blocks.DIORITE_SLAB,
						Blocks.END_STONE_BRICK_SLAB, Blocks.GRANITE_SLAB, Blocks.JUNGLE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB,
						Blocks.NETHER_BRICK_SLAB, Blocks.OAK_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.POLISHED_GRANITE_SLAB,
						Blocks.PRISMARINE_SLAB, Blocks.PRISMARINE_BRICK_SLAB, Blocks.PURPUR_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_NETHER_BRICK_SLAB,
						Blocks.RED_SANDSTONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB,
						Blocks.SMOOTH_STONE_SLAB, Blocks.SPRUCE_SLAB, Blocks.STONE_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.BLACKSTONE_SLAB, Blocks.POLISHED_BLACKSTONE_SLAB,
						Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, Blocks.CRIMSON_SLAB, Blocks.WARPED_SLAB)
				.forEach(b -> {
					Identifier key = Registry.BLOCK.getId(b);
					String path = key.getPath();
					if (path.equals("air") && key.getNamespace().equals("minecraft")) return; // default value, if block does not exist in registry

					new MMOBlockRegistrar(new VerticalSlabBlock(b))
							.register(path.replace("_slab", "_vertical_slab"));
				});
	}
}
