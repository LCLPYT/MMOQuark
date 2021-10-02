package work.lclpnet.mmoblocks.block;

import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmoblocks.MMOBlocksClient;

public class FramedGlassBlock extends MMOGlassBlock {

    public FramedGlassBlock(Settings settings, boolean translucent) {
        super(settings);

        MMOBlocksClient.setBlockRenderType(this, translucent ? RenderLayer.getTranslucent() : RenderLayer.getCutout());
    }
}
