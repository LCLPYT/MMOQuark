package work.lclpnet.mmoquark.client.module;

import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmoquark.module.FramedGlassModule;

import java.util.stream.Stream;

public class FramedGlassClientModule implements IClientModule {

    @Override
    public void registerClient() {
        Stream.of(FramedGlassModule.framedGlass, FramedGlassModule.framedGlassPane)
                .forEach(block -> MMORenderLayers.setBlockRenderType(block, RenderLayer.getCutout()));

        Stream.concat(FramedGlassModule.framedGlassBlocks.stream(), FramedGlassModule.framedGlassPanes.stream())
                .forEach(block -> MMORenderLayers.setBlockRenderType(block, RenderLayer.getTranslucent()));
    }
}
