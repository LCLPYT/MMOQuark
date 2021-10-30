package work.lclpnet.mmoblocks;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import work.lclpnet.mmoblocks.block.*;
import work.lclpnet.mmoblocks.module.*;

import java.util.Set;

import static net.minecraft.block.AbstractBlock.Settings.copy;

public class MMOBlocks implements ModInitializer {

	public static final String MOD_ID = "mmoblocks";

	public static final Set<IModule> MODULES = ImmutableSet.of(
			new VanillaVerticalSlabsModule(),
			new CaveCrystalsModule(),
			new FramedGlassModule(),
			new MoreBricksModule(),
			new WoodExtraModule(),
			new TreesModule()
	);

	@Override
	public void onInitialize() {
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

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS))
				.register("potato_crate", ItemGroup.DECORATIONS);

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS))
				.register("carrot_crate");

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS))
				.register("beetroot_crate");

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS))
				.register("apple_crate");

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS))
				.register("golden_apple_crate");

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL))
				.register("berry_sack");

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL))
				.register("cocoa_beans_sack");

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL))
				.register("gunpowder_sack");

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL))
				.register("nether_wart_sack");

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.STONE))
				.withSlab().withStairs().withWall().withVerticalSlab()
				.register("permafrost");

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.STONE))
				.withSlab().withWall().withStairs().withVerticalSlab()
				.register("permafrost_bricks");

		new MMOBlockRegistrar(FabricBlockSettings.of(Material.STONE, MaterialColor.GRAY)
				.requiresTool()
				.breakByTool(FabricToolTags.PICKAXES)
				.strength(1.5F, 10F)
				.sounds(BlockSoundGroup.STONE))
				.withSlab().withStairs().withWall().withVerticalSlab()
				.register("cobbedstone");

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.MAGMA_BLOCK)
				.strength(1.5F, 10F)
				.emissiveLighting((s, r, p) -> true))
				.withSlab().withStairs().withWall().withVerticalSlab()
				.register("magma_bricks");

		new MMOBlockRegistrar(new TurfBlock(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK)))
				.withSlab().withStairs().withVerticalSlab()
				.register("turf");

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.STONE))
				.withStairs().withSlab().withWall().withVerticalSlab().register("brimstone");

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.STONE))
				.withStairs().withSlab().withWall().withVerticalSlab().register("brimstone_bricks");

		new MMOBlockRegistrar(new MyaliteBlock(AbstractBlock.Settings.copy(Blocks.STONE)))
				.withStairs().withSlab().withWall().withVerticalSlab().register("myalite");

		new MMOBlockRegistrar(new MyaliteBlock(AbstractBlock.Settings.copy(Blocks.STONE)))
				.withStairs().withSlab().withVerticalSlab().register("polished_myalite");

		new MMOBlockRegistrar(new MyaliteBlock(AbstractBlock.Settings.copy(Blocks.STONE)))
				.withStairs().withSlab().withWall().withVerticalSlab().register("myalite_bricks");

		new MMOBlockRegistrar(new MyaliteBlock(AbstractBlock.Settings.copy(Blocks.STONE)))
				.register("chiseled_myalite_bricks");

		new MMOBlockRegistrar(new MyalitePillarBlock(AbstractBlock.Settings.copy(Blocks.STONE)))
				.register("myalite_pillar");

		new MMOBlockRegistrar(new MyaliteBlock(AbstractBlock.Settings.copy(Blocks.STONE)))
				.register("myalite_pavement");

		new MMOBlockRegistrar(new GlowceliumBlock())
				.register("glowcelium");

		final MMOBlock ELDER_PRISMARINE = new MMOBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.ORANGE)
				.requiresTool()
				.breakByTool(FabricToolTags.PICKAXES)
				.strength(1.5F, 10F)
				.sounds(BlockSoundGroup.STONE));
		new MMOBlockRegistrar(ELDER_PRISMARINE)
				.withSlab().withVerticalSlab().withStairs().withWall()
				.register("elder_prismarine");

		new MMOBlockRegistrar(FabricBlockSettings.copy(ELDER_PRISMARINE))
				.withSlab().withVerticalSlab().withStairs()
				.register("elder_prismarine_bricks");

		new MMOBlockRegistrar(FabricBlockSettings.copy(ELDER_PRISMARINE))
				.withSlab().withVerticalSlab().withStairs()
				.register("dark_elder_prismarine");

		new MMOBlockRegistrar(FabricBlockSettings.of(Material.GLASS, MaterialColor.ORANGE)
				.strength(0.3F, 0.3F)
				.luminance(b -> 15) // lightValue
				.sounds(BlockSoundGroup.GLASS))
				.register("elder_sea_lantern");

		new MMOBlockRegistrar(FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK)
				.requiresTool()
				.breakByTool(FabricToolTags.PICKAXES)
				.strength(5F, 10F)
				.sounds(BlockSoundGroup.STONE))
				.register("charcoal_block");

		// modules
		MODULES.forEach(IModule::register);
	}

	public static Identifier identifier(String path) {
		return new Identifier(MOD_ID, path);
	}
}
