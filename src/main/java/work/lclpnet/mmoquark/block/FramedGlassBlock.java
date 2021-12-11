package work.lclpnet.mmoquark.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmocontent.block.ext.MMOGlassBlock;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmocontent.util.Env;

public class FramedGlassBlock extends MMOGlassBlock {

    public FramedGlassBlock(Settings settings, boolean translucent) {
        super(settings);
        if (Env.isClient()) registerRenderLayer(translucent);
    }

    @Environment(EnvType.CLIENT)
    private void registerRenderLayer(boolean translucent) {
        MMORenderLayers.setBlockRenderType(this, translucent ? RenderLayer.getTranslucent() : RenderLayer.getCutout());
    }
}
