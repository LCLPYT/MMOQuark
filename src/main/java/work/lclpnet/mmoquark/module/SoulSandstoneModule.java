package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import work.lclpnet.mmoquark.block.MMOBlockRegistrar;

public class SoulSandstoneModule implements IModule {

    @Override
    public void register() {
        AbstractBlock.Settings settings = FabricBlockSettings.of(Material.STONE, MaterialColor.BROWN)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES)
                .strength(0.8F, 0.8F);

        new MMOBlockRegistrar(settings)
                .withSlab().withVerticalSlab().withStairs().withWall()
                .register("soul_sandstone");

        new MMOBlockRegistrar(settings)
                .register("chiseled_soul_sandstone");

        new MMOBlockRegistrar(settings)
                .withSlab().withVerticalSlab()
                .register("cut_soul_sandstone");

        new MMOBlockRegistrar(settings)
                .withSlab().withVerticalSlab().withStairs()
                .register("smooth_soul_sandstone");
    }
}
