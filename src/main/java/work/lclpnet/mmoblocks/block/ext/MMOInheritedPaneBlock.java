package work.lclpnet.mmoblocks.block.ext;

import net.minecraft.block.Block;
import work.lclpnet.mmoblocks.util.Env;
import work.lclpnet.mmoblocks.util.MMORenderLayers;

public class MMOInheritedPaneBlock extends MMOPaneBlock {

    public MMOInheritedPaneBlock(Block parent) {
        super(parent);

        if (Env.isClient()) MMORenderLayers.inheritRenderLayer(this, parent);
    }
}
