package work.lclpnet.mmoblocks.module;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.ext.MMOBlock;
import work.lclpnet.mmoblocks.block.ext.MMOPillarBlock;

public class CompressedBlocksModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES)
                .strength(0.5F, 10F)
                .sounds(BlockSoundGroup.STONE))
                .register("charcoal_block");

        pillar("sugar_cane", MaterialColor.LIME);
        pillar("bamboo", MaterialColor.YELLOW);
        pillar("cactus", MaterialColor.GREEN);
        pillar("chorus_fruit", MaterialColor.PURPLE);
        pillar("stick", MaterialColor.WOOD);

        crate("golden_apple", MaterialColor.GOLD);
        crate("apple", MaterialColor.RED);
        crate("potato", MaterialColor.ORANGE);
        crate("carrot", MaterialColor.ORANGE_TERRACOTTA);
        crate("beetroot", MaterialColor.RED);

        sack("cocoa_beans", MaterialColor.BROWN);
        sack("nether_wart", MaterialColor.RED);
        sack("gunpowder", MaterialColor.GRAY);
        sack("berry", MaterialColor.RED);

        new MMOBlockRegistrar(AbstractBlock.Settings.of(Material.GLASS, DyeColor.YELLOW)
                .strength(0.3F, 0.3F)
                .sounds(BlockSoundGroup.GLASS)
                .luminance(b -> 15))
                .register("blaze_lantern");

        new MMOBlockRegistrar(AbstractBlock.Settings.of(Material.WOOL, DyeColor.ORANGE)
                .strength(0.4F, 0.4F)
                .sounds(BlockSoundGroup.WOOL))
                .register("bonded_leather");

        new MMOBlockRegistrar(AbstractBlock.Settings.of(Material.WOOL, DyeColor.WHITE)
                .strength(0.4F, 0.4F)
                .sounds(BlockSoundGroup.WOOL))
                .register("bonded_rabbit_hide");

        new MMOBlockRegistrar(AbstractBlock.Settings.of(Material.WOOL, DyeColor.BLACK)
                .strength(1F, 1F)
                .sounds(BlockSoundGroup.WOOL))
                .register("bonded_ravager_hide");
    }

    private void pillar(String name, MaterialColor color) {
        Block block = new MMOPillarBlock(AbstractBlock.Settings.of(Material.WOOD, color)
                .strength(0.5F, 0.5F)
                .sounds(BlockSoundGroup.WOOD));

        new MMOBlockRegistrar(block).register(name + "_block");
    }

    private void crate(String name, MaterialColor color) {
        Block block = new MMOBlock(AbstractBlock.Settings.of(Material.WOOD, color)
                .strength(1.5F, 1.5F)
                .sounds(BlockSoundGroup.WOOD));

        new MMOBlockRegistrar(block).register(name + "_crate", ItemGroup.DECORATIONS);
    }

    private void sack(String name, MaterialColor color) {
        Block block = new MMOBlock(AbstractBlock.Settings.of(Material.WOOD, color)
                .strength(0.5F, 0.5F)
                .sounds(BlockSoundGroup.WOOL));

        new MMOBlockRegistrar(block).register(name + "_sack", ItemGroup.DECORATIONS);
    }
}
