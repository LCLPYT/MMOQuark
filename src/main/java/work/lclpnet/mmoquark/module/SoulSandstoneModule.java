package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;

public class SoulSandstoneModule implements IModule {

    @Override
    public void register() {
        AbstractBlock.Settings settings = FabricBlockSettings.of(Material.STONE, MapColor.BROWN)
                .requiresTool()
                .strength(0.8F, 0.8F);

        new MMOBlockRegistrar(settings)
                .withSlab().withVerticalSlab().withStairs().withWall()
                .register(MMOQuark.identifier("soul_sandstone"));

        new MMOBlockRegistrar(settings)
                .register(MMOQuark.identifier("chiseled_soul_sandstone"));

        new MMOBlockRegistrar(settings)
                .withSlab().withVerticalSlab()
                .register(MMOQuark.identifier("cut_soul_sandstone"));

        new MMOBlockRegistrar(settings)
                .withSlab().withVerticalSlab().withStairs()
                .register(MMOQuark.identifier("smooth_soul_sandstone"));
    }
}
