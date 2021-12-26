package work.lclpnet.mmoquark.client.module;

import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmoquark.module.GrateModule;

public class GrateClientModule implements IClientModule {

    @Override
    public void registerClient() {
        MMORenderLayers.setBlockRenderType(GrateModule.grateBlock, RenderLayer.getCutout());
    }
}
