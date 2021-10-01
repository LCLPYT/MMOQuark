package work.lclpnet.mmoblocks.block;

import net.minecraft.block.PaneBlock;
import work.lclpnet.mmoblocks.util.States;

public class MMOPaneBlock extends PaneBlock {

    protected MMOPaneBlock(MMOBlock parent) {
        super(States.copyState(parent));
    }
}
