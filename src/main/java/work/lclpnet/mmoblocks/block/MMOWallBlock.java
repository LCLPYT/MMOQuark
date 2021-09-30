package work.lclpnet.mmoblocks.block;

import net.minecraft.block.WallBlock;

public class MMOWallBlock extends WallBlock {

    public MMOWallBlock(MMOBlock parent) {
        super(Settings.copy(parent));
    }
}
