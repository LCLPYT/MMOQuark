package work.lclpnet.mmoblocks.module;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.ext.MMOPillarBlock;
import work.lclpnet.mmoblocks.item.MMOItemRegistrar;

public class MidoriModule implements IModule {

    @Override
    public void register() {
        new MMOItemRegistrar()
                .register("cactus_paste", ItemGroup.MATERIALS);

        AbstractBlock.Settings settings = FabricBlockSettings.of(Material.STONE, MaterialColor.LIME)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES)
                .strength(1.5F, 6.0F);

        new MMOBlockRegistrar(settings)
                .withSlab().withVerticalSlab().withStairs()
                .register("midori_block");

        new MMOBlockRegistrar(new MMOPillarBlock(settings))
                .register("midori_pillar");
    }
}
