package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.FramedGlassBlock;

public class FramedGlassModule implements IModule {

    @Override
    public void register() {
        AbstractBlock.Settings props = FabricBlockSettings.of(Material.GLASS)
                .strength(3F, 10F)
                .sounds(BlockSoundGroup.GLASS)
                .breakByTool(FabricToolTags.PICKAXES, 1);

        new MMOBlockRegistrar(new FramedGlassBlock(props, false))
                .withPane()
                .register(MMOQuark.identifier("framed_glass"));

        for (DyeColor color : DyeColor.values())
            new MMOBlockRegistrar(new FramedGlassBlock(props, true))
                    .withPane()
                    .register(MMOQuark.identifier(String.format("%s_framed_glass", color.getName())));
    }
}
