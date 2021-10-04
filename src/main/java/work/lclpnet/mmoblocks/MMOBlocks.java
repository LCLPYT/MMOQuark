package work.lclpnet.mmoblocks;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.module.CaveCrystalsModule;
import work.lclpnet.mmoblocks.module.FramedGlassModule;
import work.lclpnet.mmoblocks.module.IModule;
import work.lclpnet.mmoblocks.module.VanillaVerticalSlabsModule;

import static net.minecraft.block.AbstractBlock.Settings.copy;

public class MMOBlocks implements ModInitializer {

	public static final String MOD_ID = "mmoblocks";

	@Override
	public void onInitialize() {
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

		new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS))
				.register("potato_crate", ItemGroup.DECORATIONS);

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

		// modules
		ImmutableSet.of(
				new VanillaVerticalSlabsModule(),
				new CaveCrystalsModule(),
				new FramedGlassModule()
		).forEach(IModule::register);
	}
}
