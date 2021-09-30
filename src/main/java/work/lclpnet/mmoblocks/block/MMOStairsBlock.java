package work.lclpnet.mmoblocks.block;

import net.minecraft.block.StairsBlock;

public class MMOStairsBlock extends StairsBlock {

    protected MMOStairsBlock(MMOBlock parent) {
        super(parent.getDefaultState(), Settings.copy(parent));
    }
}
