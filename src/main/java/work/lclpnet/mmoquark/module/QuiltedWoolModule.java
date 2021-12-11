package work.lclpnet.mmoquark.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import work.lclpnet.mmoquark.block.MMOBlockRegistrar;

public class QuiltedWoolModule implements IModule {

    @Override
    public void register() {
        for (DyeColor dye : DyeColor.values()) {
            new MMOBlockRegistrar(AbstractBlock.Settings.of(Material.WOOL, dye.getMaterialColor())
                    .strength(0.8F, 0.8F)
                    .sounds(BlockSoundGroup.WOOL))
                    .register(String.format("%s_quilted_wool", dye.getName()));
        }
    }
}
