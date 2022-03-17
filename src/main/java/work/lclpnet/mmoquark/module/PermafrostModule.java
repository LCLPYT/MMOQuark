package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.block.ext.MMOBlock;
import work.lclpnet.mmoquark.MMOQuark;

public class PermafrostModule implements IModule {

    @Override
    public void register() {
        final MMOBlock permafrost = new MMOBlock(FabricBlockSettings.of(Material.STONE, MapColor.LIGHT_BLUE)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES)
                .strength(1.5F, 10F)
                .sounds(BlockSoundGroup.STONE));

        new MMOBlockRegistrar(permafrost)
                .withSlab().withVerticalSlab().withStairs().withWall()
                .register(MMOQuark.identifier("permafrost"));

        new MMOBlockRegistrar(FabricBlockSettings.copyOf(permafrost))
                .withSlab().withVerticalSlab().withStairs().withWall()
                .register(MMOQuark.identifier("permafrost_bricks"));
    }
}
