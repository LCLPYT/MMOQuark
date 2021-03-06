package work.lclpnet.mmoquark.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;

public class QuiltedWoolModule implements IModule {

    @Override
    public void register() {
        for (DyeColor dye : DyeColor.values()) {
            new MMOBlockRegistrar(AbstractBlock.Settings.of(Material.WOOL, dye.getMapColor())
                    .strength(0.8F, 0.8F)
                    .sounds(BlockSoundGroup.WOOL))
                    .register(MMOQuark.identifier("%s_quilted_wool", dye.getName()));
        }
    }
}
