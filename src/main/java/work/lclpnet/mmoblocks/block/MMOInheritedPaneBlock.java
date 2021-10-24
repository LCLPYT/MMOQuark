package work.lclpnet.mmoblocks.block;

import net.minecraft.block.Block;
import work.lclpnet.mmoblocks.MMOBlocksClient;
import work.lclpnet.mmoblocks.util.Env;

public class MMOInheritedPaneBlock extends MMOPaneBlock {

    protected MMOInheritedPaneBlock(Block parent) {
        super(parent);

        if (Env.isClient()) MMOBlocksClient.inheritRenderLayer(this, parent);
    }
}
