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
import work.lclpnet.mmoblocks.block.ext.MMOBlock;
import work.lclpnet.mmoblocks.module.*;
import work.lclpnet.mmoblocks.sound.MMOSounds;

import java.util.Set;

import static net.minecraft.block.AbstractBlock.Settings.copy;

public class MMOBlocks implements ModInitializer {

	public static final String MOD_ID = "mmoblocks";

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
			new WoodPostsModule(),
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
			new BlossomTreesModule(),
			/* depends on BlossomTrees */ new LeafCarpetModule(),
		    /* depends on BlossomTrees */ new HedgesModule(),
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
			new CrabsModule()
	);

	@Override
	public void onInitialize() {
		new MMOBlockRegistrar(copy(Blocks.IRON_BLOCK))
				.withSlab().withStairs().withVerticalSlab()
				.register("iron_plate");

		new MMOBlockRegistrar(copy(Blocks.IRON_BLOCK))
				.withSlab().withStairs().withVerticalSlab()
				.register("rusty_iron_plate");

		new MMOBlockRegistrar(FabricBlockSettings.of(Material.STONE, MaterialColor.GRAY)
				.requiresTool()
				.breakByTool(FabricToolTags.PICKAXES)
				.strength(1.5F, 10F)
				.sounds(BlockSoundGroup.STONE))
				.withSlab().withStairs().withWall().withVerticalSlab()
				.register("cobbedstone");

		new MMOBlockRegistrar(new TurfBlock(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK)))
				.withSlab().withStairs().withVerticalSlab()
				.register("turf");

		MMOBlock brimstone = new MMOBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.RED)
				.requiresTool()
				.breakByTool(FabricToolTags.PICKAXES)
				.strength(1.5F, 10F)
				.sounds(BlockSoundGroup.STONE));

		new MMOBlockRegistrar(brimstone)
				.withStairs().withSlab().withWall().withVerticalSlab()
				.register("brimstone");

		new MMOBlockRegistrar(FabricBlockSettings.copyOf(brimstone))
				.withStairs().withSlab().withWall().withVerticalSlab()
				.register("brimstone_bricks");

		new MMOBlockRegistrar(new ThatchBlock())
				.withSlab().withStairs().withVerticalSlab()
				.register("thatch");

		final MMOBlock elderPrismarine = new MMOBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.ORANGE)
				.requiresTool()
				.breakByTool(FabricToolTags.PICKAXES)
				.strength(1.5F, 10F)
				.sounds(BlockSoundGroup.STONE));

		new MMOBlockRegistrar(elderPrismarine)
				.withSlab().withVerticalSlab().withStairs().withWall()
				.register("elder_prismarine");

		new MMOBlockRegistrar(FabricBlockSettings.copy(elderPrismarine))
				.withSlab().withVerticalSlab().withStairs()
				.register("elder_prismarine_bricks");

		new MMOBlockRegistrar(FabricBlockSettings.copy(elderPrismarine))
				.withSlab().withVerticalSlab().withStairs()
				.register("dark_elder_prismarine");

		new MMOBlockRegistrar(FabricBlockSettings.of(Material.GLASS, MaterialColor.ORANGE)
				.strength(0.3F, 0.3F)
				.luminance(b -> 15) // lightValue
				.sounds(BlockSoundGroup.GLASS))
				.register("elder_sea_lantern");

		new MMOBlockRegistrar(new BambooMatBlock())
				.register("bamboo_mat");

		new MMOBlockRegistrar(new SturdyStoneBlock())
				.register("sturdy_stone");

		new MMOBlockRegistrar(new MMOBlock(AbstractBlock.Settings.copy(Blocks.REDSTONE_LAMP)
				.luminance(b -> 15)))
				.register("lit_lamp", ItemGroup.DECORATIONS);

		new MMOBlockRegistrar(new ObsidianPressurePlateBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK)
				.requiresTool()
				.breakByTool(FabricToolTags.PICKAXES)
				.noCollision()
				.strength(2F, 1200F)))
				.register("obsidian_pressure_plate", ItemGroup.REDSTONE);

		new MMOBlockRegistrar(new IronRodBlock())
				.register("iron_rod", ItemGroup.DECORATIONS);

		// modules
		MODULES.forEach(IModule::register);

		MMOSounds.init();
	}

	public static Identifier identifier(String path) {
		return new Identifier(MOD_ID, path);
	}
}
