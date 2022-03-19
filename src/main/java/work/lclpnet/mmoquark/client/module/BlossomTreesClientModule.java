package work.lclpnet.mmoquark.client.module;

import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmocontent.client.entity.MMOBoatClientUtility;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmoquark.module.BlossomTreesModule;

public class BlossomTreesClientModule implements IClientModule {

    @Override
    public void registerClient() {
        MMOBoatClientUtility.enableMMOBoatClientIntegration();

        BlossomTreesModule.blossomLeaveBlocks
                .forEach(block -> MMORenderLayers.setBlockRenderType(block, RenderLayer.getCutoutMipped()));

        BlossomTreesModule.blossomSaplingBlocks
                .forEach(block -> MMORenderLayers.setBlockRenderType(block, RenderLayer.getCutout()));

        BlossomTreesModule.flowerPotBlocks
                .forEach(block -> MMORenderLayers.setBlockRenderType(block, RenderLayer.getCutout()));
    }
}
