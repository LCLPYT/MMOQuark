package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import work.lclpnet.mmoquark.block.FramedGlassBlock;
import work.lclpnet.mmoquark.block.MMOBlockRegistrar;

public class FramedGlassModule implements IModule {

    @Override
    public void register() {
        AbstractBlock.Settings props = FabricBlockSettings.of(Material.GLASS)
                .strength(3F, 10F)
                .sounds(BlockSoundGroup.GLASS)
                .breakByTool(FabricToolTags.PICKAXES, 1);

        new MMOBlockRegistrar(new FramedGlassBlock(props, false))
                .withPane()
                .register("framed_glass");

        for (DyeColor color : DyeColor.values())
            new MMOBlockRegistrar(new FramedGlassBlock(props, true))
                    .withPane()
                    .register(color.getName() + "_framed_glass");
    }
}
