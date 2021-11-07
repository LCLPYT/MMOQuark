package work.lclpnet.mmoblocks.block.ext;

import net.minecraft.block.PaneBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import work.lclpnet.mmoblocks.util.Env;
import work.lclpnet.mmoblocks.util.MMORenderLayers;

public class MMOPaneBlock extends PaneBlock implements IMMOBlock {

    public MMOPaneBlock(Settings settings, boolean registerRenderLayer) {
        super(settings);

        if (registerRenderLayer && Env.isClient()) registerRenderLayer();
    }

    protected void registerRenderLayer() {
        MMORenderLayers.setBlockRenderType(this, RenderLayer.getCutout());
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }
}
