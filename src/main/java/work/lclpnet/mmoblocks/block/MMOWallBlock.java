package work.lclpnet.mmoblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;
import work.lclpnet.mmoblocks.util.States;

public class MMOWallBlock extends WallBlock {

    public MMOWallBlock(Block parent) {
        super(States.copyState(parent));
    }
}
