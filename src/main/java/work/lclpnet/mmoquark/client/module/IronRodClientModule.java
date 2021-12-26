package work.lclpnet.mmoquark.client.module;

import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmoquark.module.IronRodModule;

public class IronRodClientModule implements IClientModule {

    @Override
    public void registerClient() {
        MMORenderLayers.setBlockRenderType(IronRodModule.ironRodBlock, RenderLayer.getCutout());
    }
}
