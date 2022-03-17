package work.lclpnet.mmoquark.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.RopeBlock;

public class RopeModule implements IModule {

    public static RopeBlock ropeBlock;

    @Override
    public void register() {
        ropeBlock = new RopeBlock(AbstractBlock.Settings.of(Material.WOOL, MapColor.BROWN)
                .strength(0.5F, 0.5F)
                .sounds(BlockSoundGroup.WOOL));

        new MMOBlockRegistrar(ropeBlock)
                .register(MMOQuark.identifier("rope"), ItemGroup.DECORATIONS);
    }
}
