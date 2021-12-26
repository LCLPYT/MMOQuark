package work.lclpnet.mmoquark.client.module;

import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmoquark.module.WoodPostsModule;

public class WoodPostsClientModule implements IClientModule {

    @Override
    public void registerClient() {
        WoodPostsModule.woodPosts.forEach(woodPostBlock -> {
            MMORenderLayers.setBlockRenderType(woodPostBlock, RenderLayer.getCutout());

            if (woodPostBlock.strippedBlock != null)
                MMORenderLayers.setBlockRenderType(woodPostBlock.strippedBlock, RenderLayer.getCutout());
        });
    }
}
