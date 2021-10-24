package work.lclpnet.mmoblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import work.lclpnet.mmoblocks.util.States;

public class MMOStairsBlock extends StairsBlock {

    protected MMOStairsBlock(Block parent) {
        super(parent.getDefaultState(), States.copyState(parent));
    }
}
