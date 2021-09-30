package work.lclpnet.mmoblocks.block;

import net.minecraft.block.SlabBlock;

public class MMOSlabBlock extends SlabBlock {

    public MMOSlabBlock(MMOBlock parent) {
        super(Settings.copy(parent));
    }
}
