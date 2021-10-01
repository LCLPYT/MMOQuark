package work.lclpnet.mmoblocks;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.block.CaveCrystalBlock;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.VerticalSlabBlock;

import static net.minecraft.block.AbstractBlock.Settings.copy;

public class MMOBlocks implements ModInitializer {

	public static final String MOD_ID = "mmoblocks";

	@Override
	public void onInitialize() {
		registerVanillaVerticalSlabs();

		new MMOBlockRegistrar(copy(Blocks.BLACKSTONE))
				.withSlab().withStairs().withWall().withVerticalSlab()
				.register("weeping_blackstone_bricks");

		new MMOBlockRegistrar(copy(Blocks.NETHER_BRICKS))
				.withSlab().withStairs().withWall().withVerticalSlab()
				.register("charred_nether_bricks");

		new MMOBlockRegistrar(copy(Blocks.IRON_BLOCK))
				.withSlab().withStairs().withVerticalSlab()
				.register("iron_plate");

		new MMOBlockRegistrar(copy(Blocks.IRON_BLOCK))
				.withSlab().withStairs().withVerticalSlab()
				.register("rusty_iron_plate");

		new MMOBlockRegistrar(copy(Blocks.STONE))
				.withSlab().withStairs().withWall().withVerticalSlab()
				.register("marble");

		new MMOBlockRegistrar(copy(Blocks.STONE))
				.withSlab().withStairs().withWall().withVerticalSlab()
				.register("marble_bricks");

		new MMOBlockRegistrar(copy(Blocks.STONE))
				.withSlab().withStairs().withVerticalSlab()
				.register("polished_marble");

		new MMOBlockRegistrar(copy(Blocks.STONE_BRICKS))
				.withSlab().withStairs().withWall().withVerticalSlab()
				.register("cobblestone_bricks");

		new MMOBlockRegistrar(copy(Blocks.STONE_BRICKS))
				.withSlab().withWall().withStairs().withVerticalSlab()
				.register("mossy_cobblestone_bricks");

		registerCrystals();
	}

	private void registerVanillaVerticalSlabs() {
		ImmutableSet.of(
				Blocks.ACACIA_SLAB, Blocks.ANDESITE_SLAB, Blocks.BIRCH_SLAB, Blocks.BRICK_SLAB, Blocks.COBBLESTONE_SLAB,
				Blocks.CUT_RED_SANDSTONE_SLAB, Blocks.CUT_SANDSTONE_SLAB, Blocks.DARK_OAK_SLAB, Blocks.DARK_PRISMARINE_SLAB, Blocks.DIORITE_SLAB,
				Blocks.END_STONE_BRICK_SLAB, Blocks.GRANITE_SLAB, Blocks.JUNGLE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB,
				Blocks.NETHER_BRICK_SLAB, Blocks.OAK_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.POLISHED_GRANITE_SLAB,
				Blocks.PRISMARINE_SLAB, Blocks.PRISMARINE_BRICK_SLAB, Blocks.PURPUR_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_NETHER_BRICK_SLAB,
				Blocks.RED_SANDSTONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB,
				Blocks.SMOOTH_STONE_SLAB, Blocks.SPRUCE_SLAB, Blocks.STONE_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.BLACKSTONE_SLAB, Blocks.POLISHED_BLACKSTONE_SLAB,
				Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, Blocks.CRIMSON_SLAB, Blocks.WARPED_SLAB
		).forEach(b -> {
			Identifier key = Registry.BLOCK.getId(b);
			String path = key.getPath();
			if (path.equals("air") && key.getNamespace().equals("minecraft")) return; // default value, if block does not exist in registry

			new MMOBlockRegistrar(new VerticalSlabBlock(b))
					.register(path.replace("_slab", "_vertical_slab"));
		});
	}

	private void registerCrystals() {
		crystal("red", 0xff0000, MaterialColor.RED);
		crystal("orange", 0xff8000, MaterialColor.ORANGE);
		crystal("yellow", 0xffff00, MaterialColor.YELLOW);
		crystal("green", 0x00ff00, MaterialColor.GREEN);
		crystal("blue", 0x00ffff, MaterialColor.LIGHT_BLUE);
		crystal("indigo", 0x0000ff, MaterialColor.BLUE);
		crystal("violet", 0xff00ff, MaterialColor.MAGENTA);
		crystal("white", 0xffffff, MaterialColor.WHITE);
		crystal("black", 0x000000, MaterialColor.BLACK);
	}

	private void crystal(String name, int color, MaterialColor material) {
		CaveCrystalBlock crystal = new CaveCrystalBlock(material, color);
		new MMOBlockRegistrar(crystal).register(String.format("%s_crystal", name));

//		new QuarkInheritedPaneBlock(crystal);
//		CaveCrystalClusterBlock cluster = new CaveCrystalClusterBlock(crystal);
//
//		ClusterConnection connection = new ClusterConnection(cluster);
//		IIndirectConnector.INDIRECT_STICKY_BLOCKS.add(Pair.of(connection::isValidState, connection));
	}
}
