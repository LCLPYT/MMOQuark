package work.lclpnet.mmoquark.client.module;

import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmoquark.module.GlowshroomModule;

import java.util.stream.Stream;

public class GlowshroomClientModule implements IClientModule {

    @Override
    public void registerClient() {
        Stream.of(GlowshroomModule.glowshroom_block, GlowshroomModule.glowshroom_stem)
                .forEach(block -> MMORenderLayers.setBlockRenderType(block, RenderLayer.getTranslucent()));

        Stream.of(GlowshroomModule.glowshroom, GlowshroomModule.potted_glowshroom)
                .forEach(block -> MMORenderLayers.setBlockRenderType(block, RenderLayer.getCutout()));

        MMORenderLayers.setBlockRenderType(GlowshroomModule.glow_lichen_growth, RenderLayer.getCutout());
    }
}
