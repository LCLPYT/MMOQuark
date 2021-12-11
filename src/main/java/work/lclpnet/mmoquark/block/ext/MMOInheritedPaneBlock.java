package work.lclpnet.mmoquark.block.ext;

import net.minecraft.block.Block;
import work.lclpnet.mmoquark.util.Env;
import work.lclpnet.mmoquark.util.MMORenderLayers;
import work.lclpnet.mmoquark.util.States;

public class MMOInheritedPaneBlock extends MMOPaneBlock {

    public MMOInheritedPaneBlock(Block parent) {
        super(States.copyState(parent), false);

        if (Env.isClient()) MMORenderLayers.inheritRenderLayer(this, parent);
    }
}
