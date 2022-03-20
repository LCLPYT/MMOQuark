package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.block.ext.MMOBlock;
import work.lclpnet.mmocontent.block.ext.MMOPillarBlock;
import work.lclpnet.mmoquark.MMOQuark;

public class CompressedBlocksModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(FabricBlockSettings.of(Material.STONE, MapColor.BLACK)
                .requiresTool()
                .strength(0.5F, 10F)
                .sounds(BlockSoundGroup.STONE))
                .register(MMOQuark.identifier("charcoal_block"));

        pillar("sugar_cane", MapColor.LIME);
        pillar("bamboo", MapColor.YELLOW);
        pillar("cactus", MapColor.GREEN);
        pillar("chorus_fruit", MapColor.PURPLE);
        pillar("stick", MapColor.OAK_TAN);

        crate("golden_apple", MapColor.GOLD);
        crate("apple", MapColor.RED);
        crate("potato", MapColor.ORANGE);
        crate("carrot", MapColor.TERRACOTTA_ORANGE);
        crate("beetroot", MapColor.RED);

        sack("cocoa_beans", MapColor.BROWN);
        sack("nether_wart", MapColor.RED);
        sack("gunpowder", MapColor.GRAY);
        sack("berry", MapColor.RED);
        sack("glowberry", MapColor.YELLOW);

        new MMOBlockRegistrar(AbstractBlock.Settings.of(Material.GLASS, DyeColor.YELLOW)
                .strength(0.3F, 0.3F)
                .sounds(BlockSoundGroup.GLASS)
                .luminance(b -> 15))
                .register(MMOQuark.identifier("blaze_lantern"));

        new MMOBlockRegistrar(AbstractBlock.Settings.of(Material.WOOL, DyeColor.ORANGE)
                .strength(0.4F, 0.4F)
                .sounds(BlockSoundGroup.WOOL))
                .register(MMOQuark.identifier("bonded_leather"));

        new MMOBlockRegistrar(AbstractBlock.Settings.of(Material.WOOL, DyeColor.WHITE)
                .strength(0.4F, 0.4F)
                .sounds(BlockSoundGroup.WOOL))
                .register(MMOQuark.identifier("bonded_rabbit_hide"));

        new MMOBlockRegistrar(AbstractBlock.Settings.of(Material.WOOL, DyeColor.BLACK)
                .strength(1F, 1F)
                .sounds(BlockSoundGroup.WOOL))
                .register(MMOQuark.identifier("bonded_ravager_hide"));
    }

    private void pillar(String name, MapColor color) {
        Block block = new MMOPillarBlock(AbstractBlock.Settings.of(Material.WOOD, color)
                .strength(0.5F, 0.5F)
                .sounds(BlockSoundGroup.WOOD));

        new MMOBlockRegistrar(block).register(MMOQuark.identifier("%s_block", name));
    }

    private void crate(String name, MapColor color) {
        Block block = new MMOBlock(AbstractBlock.Settings.of(Material.WOOD, color)
                .strength(1.5F, 1.5F)
                .sounds(BlockSoundGroup.WOOD));

        new MMOBlockRegistrar(block).register(MMOQuark.identifier("%s_crate", name), ItemGroup.DECORATIONS);
    }

    private void sack(String name, MapColor color) {
        Block block = new MMOBlock(AbstractBlock.Settings.of(Material.WOOD, color)
                .strength(0.5F, 0.5F)
                .sounds(BlockSoundGroup.WOOL));

        new MMOBlockRegistrar(block).register(MMOQuark.identifier("%s_sack", name), ItemGroup.DECORATIONS);
    }
}
