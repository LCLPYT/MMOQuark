package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.block.ext.MMOFenceGateBlock;
import work.lclpnet.mmoquark.MMOQuark;

public class NetherBrickFenceModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(new MMOFenceGateBlock(FabricBlockSettings.of(Material.STONE, MapColor.DARK_RED)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES)
                .sounds(BlockSoundGroup.NETHER_BRICKS)
                .strength(2.0F, 6.0F)))
                .register(MMOQuark.identifier("nether_brick_fence_gate"), ItemGroup.REDSTONE);
    }
}
