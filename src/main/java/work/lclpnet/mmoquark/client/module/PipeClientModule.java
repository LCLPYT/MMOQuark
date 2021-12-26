package work.lclpnet.mmoquark.client.module;

import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmoquark.module.PipesModule;

public class PipeClientModule implements IClientModule {

    @Override
    public void registerClient() {
        MMORenderLayers.setBlockRenderType(PipesModule.pipeBlock, RenderLayer.getCutout());
    }
}
