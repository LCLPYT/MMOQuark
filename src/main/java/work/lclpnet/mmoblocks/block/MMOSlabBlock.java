package work.lclpnet.mmoblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import work.lclpnet.mmoblocks.util.States;

public class MMOSlabBlock extends SlabBlock {

    public MMOSlabBlock(Block parent) {
        super(States.copyState(parent));
    }
}
