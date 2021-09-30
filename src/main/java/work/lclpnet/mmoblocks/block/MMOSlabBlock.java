package work.lclpnet.mmoblocks.block;

import net.minecraft.block.SlabBlock;
import work.lclpnet.mmoblocks.util.States;

public class MMOSlabBlock extends SlabBlock {

    public MMOSlabBlock(MMOBlock parent) {
        super(States.copyState(parent));
    }
}
