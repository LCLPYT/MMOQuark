package work.lclpnet.mmoquark.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.RopeBlock;

public class RopeModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(new RopeBlock(AbstractBlock.Settings.of(Material.WOOL, MaterialColor.BROWN)
                .strength(0.5F, 0.5F)
                .sounds(BlockSoundGroup.WOOL)))
                .register(MMOQuark.identifier("rope"), ItemGroup.DECORATIONS);
    }
}
