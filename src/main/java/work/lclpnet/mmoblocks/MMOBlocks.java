package work.lclpnet.mmoblocks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MMOBlocks implements ModInitializer {

	public static final String MOD_ID = "mmoblocks";
	public static final Block WEEPING_BLACKSTONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).strength(2.0F));

	@Override
	public void onInitialize() {
		System.out.println("Hello Fabric world!");

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "weeping_blackstone_bricks"), WEEPING_BLACKSTONE_BRICKS);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "weeping_blackstone_bricks"), new BlockItem(WEEPING_BLACKSTONE_BRICKS, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
	}
}
