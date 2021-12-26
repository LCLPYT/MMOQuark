package work.lclpnet.mmoquark.client.module;

import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmoquark.module.CaveRootModule;

import java.util.stream.Stream;

public class CaveRootClientModule implements IClientModule {

    @Override
    public void registerClient() {
        Stream.of(CaveRootModule.root, CaveRootModule.pottedRoots)
                .forEach(block -> MMORenderLayers.setBlockRenderType(block, RenderLayer.getCutout()));
    }
}
