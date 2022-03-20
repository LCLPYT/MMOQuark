package work.lclpnet.mmoquark;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.block.ext.MMOBlock;
import work.lclpnet.mmoquark.block.BambooMatBlock;
import work.lclpnet.mmoquark.block.ObsidianPressurePlateBlock;
import work.lclpnet.mmoquark.block.SturdyStoneBlock;
import work.lclpnet.mmoquark.block.ThatchBlock;
import work.lclpnet.mmoquark.module.*;
import work.lclpnet.mmoquark.sound.MMOSounds;

import java.util.Set;

import static net.minecraft.block.AbstractBlock.Settings.copy;

public class MMOQuark implements ModInitializer {

	public static final String MOD_ID = "quark";

	public static final Set<IModule> MODULES = ImmutableSet.of(
			new VanillaVerticalSlabsModule(),
			new CaveCrystalsModule(),
			new FramedGlassModule(),
			new PermafrostModule(),
			new NewStoneTypesModule(),
			new MoreBricksModule(),
			new CobblestoneBricksModule(),
			new VariantBookshelvesModule(),
			new VariantLaddersModule(),
			new BurntVinesModule(),
			new CompressedBlocksModule(),
			new DuskboundBlocksModule(),
			new TallowAndCandlesModule(),
			new SoulSandstoneModule(),
			new RopeModule(),
			new QuiltedWoolModule(),
			new PaperDecorModule(),
			new NetherBrickFenceModule(),
			new MoreStoneVariantsModule(),
			new MorePottedPlantsModule(),
			new MidoriModule(),
			new VariantChestsModule(),
			new GrateModule(),
			new GoldBarsModule(),
			new AzaleaWoodModule(),
			new BlossomTreesModule(),
			/* depends on BlossomTrees */ new LeafCarpetModule(),
		    /* depends on BlossomTrees */ new HedgesModule(),
			/* depends on AzaleaWood, BlossomTrees*/ new WoodPostsModule(),
			new StoolsModule(),
			new BiotiteModule(),
			new GlowshroomModule(),
			new CaveRootModule(),
			new ChorusVegetationModule(),
			new SpeleothemsModule(),
			new StonelingsModule(),
			new MetalButtonsModule(),
			new FeedingTroughModule(),
			new EnderWatcherModule(),
			new PickarangModule(),
			new CrabsModule(),
			new PipesModule(),
			new ItemFramesModule(),
			new FrogsModule(),
			new ForgottenModule(),
			new TurfModule(),
			new IronRodModule(),
			new StainedPlanksModule()
	);

	@Override
	public void onInitialize() {
		new MMOBlockRegistrar(copy(Blocks.IRON_BLOCK))
				.withSlab().withStairs().withVerticalSlab()
				.register(MMOQuark.identifier("iron_plate"));

		new MMOBlockRegistrar(copy(Blocks.IRON_BLOCK))
				.withSlab().withStairs().withVerticalSlab()
				.register(MMOQuark.identifier("rusty_iron_plate"));

		new MMOBlockRegistrar(FabricBlockSettings.of(Material.STONE, MapColor.GRAY)
				.requiresTool()
				.breakByTool(FabricToolTags.PICKAXES)
				.strength(1.5F, 10F)
				.sounds(BlockSoundGroup.STONE))
				.withSlab().withStairs().withWall().withVerticalSlab()
				.register(MMOQuark.identifier("cobbedstone"));

		MMOBlock brimstone = new MMOBlock(FabricBlockSettings.of(Material.STONE, MapColor.RED)
				.requiresTool()
				.breakByTool(FabricToolTags.PICKAXES)
				.strength(1.5F, 10F)
				.sounds(BlockSoundGroup.STONE));

		new MMOBlockRegistrar(brimstone)
				.withStairs().withSlab().withWall().withVerticalSlab()
				.register(MMOQuark.identifier("brimstone"));

		new MMOBlockRegistrar(FabricBlockSettings.copyOf(brimstone))
				.withStairs().withSlab().withWall().withVerticalSlab()
				.register(MMOQuark.identifier("brimstone_bricks"));

		new MMOBlockRegistrar(new ThatchBlock())
				.withSlab().withStairs().withVerticalSlab()
				.register(MMOQuark.identifier("thatch"));

		final MMOBlock elderPrismarine = new MMOBlock(FabricBlockSettings.of(Material.STONE, MapColor.ORANGE)
				.requiresTool()
				.breakByTool(FabricToolTags.PICKAXES)
				.strength(1.5F, 10F)
				.sounds(BlockSoundGroup.STONE));

		new MMOBlockRegistrar(elderPrismarine)
				.withSlab().withVerticalSlab().withStairs().withWall()
				.register(MMOQuark.identifier("elder_prismarine"));

		new MMOBlockRegistrar(FabricBlockSettings.copy(elderPrismarine))
				.withSlab().withVerticalSlab().withStairs()
				.register(MMOQuark.identifier("elder_prismarine_bricks"));

		new MMOBlockRegistrar(FabricBlockSettings.copy(elderPrismarine))
				.withSlab().withVerticalSlab().withStairs()
				.register(MMOQuark.identifier("dark_elder_prismarine"));

		new MMOBlockRegistrar(FabricBlockSettings.of(Material.GLASS, MapColor.ORANGE)
				.strength(0.3F, 0.3F)
				.luminance(b -> 15) // lightValue
				.sounds(BlockSoundGroup.GLASS))
				.register(MMOQuark.identifier("elder_sea_lantern"));

		new MMOBlockRegistrar(new BambooMatBlock())
				.register(MMOQuark.identifier("bamboo_mat"), ItemGroup.DECORATIONS);

		new MMOBlockRegistrar(new SturdyStoneBlock())
				.register(MMOQuark.identifier("sturdy_stone"));

		new MMOBlockRegistrar(new MMOBlock(AbstractBlock.Settings.copy(Blocks.REDSTONE_LAMP)
				.luminance(b -> 15)))
				.register(MMOQuark.identifier("lit_lamp"), ItemGroup.DECORATIONS);

		new MMOBlockRegistrar(new ObsidianPressurePlateBlock(FabricBlockSettings.of(Material.STONE, MapColor.BLACK)
				.requiresTool()
				.breakByTool(FabricToolTags.PICKAXES)
				.noCollision()
				.strength(2F, 1200F)))
				.register(MMOQuark.identifier("obsidian_pressure_plate"), ItemGroup.REDSTONE);

		// modules
		MODULES.forEach(IModule::register);

		MMOSounds.init();
	}

	public static Identifier identifier(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static Identifier identifier(String path, Object... substitutes) {
		return identifier(String.format(path, substitutes));
	}
}
