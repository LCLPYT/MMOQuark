package work.lclpnet.mmoblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmoblocks.block.ext.MMOGlassBlock;
import work.lclpnet.mmoblocks.util.Env;
import work.lclpnet.mmoblocks.util.MMORenderLayers;

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
