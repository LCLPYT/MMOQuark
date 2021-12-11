package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.block.ext.MMOPillarBlock;
import work.lclpnet.mmocontent.item.MMOItemRegistrar;
import work.lclpnet.mmoquark.MMOQuark;

public class BiotiteModule implements IModule {

    @Override
    public void register() {
        new MMOItemRegistrar()
                .register(MMOQuark.identifier("biotite"), ItemGroup.MATERIALS);

        new MMOBlockRegistrar(FabricBlockSettings.of(Material.STONE, MaterialColor.SAND)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES)
                .strength(3.2F, 15F)
                .sounds(BlockSoundGroup.STONE))
                .register(MMOQuark.identifier("biotite_ore"));

        FabricBlockSettings settings = FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES)
                .strength(0.8F, 0.8F);

        new MMOBlockRegistrar(settings)
                .withSlab().withVerticalSlab().withStairs()
                .register(MMOQuark.identifier("biotite_block"));

        new MMOBlockRegistrar(settings)
                .withSlab().withVerticalSlab().withStairs()
                .register(MMOQuark.identifier("smooth_biotite"));

        new MMOBlockRegistrar(settings)
                .register(MMOQuark.identifier("chiseled_biotite_block"));

        new MMOBlockRegistrar(new MMOPillarBlock(settings))
                .register(MMOQuark.identifier("biotite_pillar"));

        new MMOBlockRegistrar(settings)
                .register(MMOQuark.identifier("biotite_bricks"));
    }
}
