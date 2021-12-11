package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import work.lclpnet.mmoquark.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.block.ext.MMOFenceGateBlock;

public class NetherBrickFenceModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(new MMOFenceGateBlock(FabricBlockSettings.of(Material.STONE, MaterialColor.NETHER)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES)
                .sounds(BlockSoundGroup.NETHER_BRICKS)
                .strength(2.0F, 6.0F)))
                .register("nether_brick_fence_gate", ItemGroup.REDSTONE);
    }
}
