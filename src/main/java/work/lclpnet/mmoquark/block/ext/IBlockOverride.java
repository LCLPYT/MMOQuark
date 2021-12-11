package work.lclpnet.mmoquark.block.ext;

import net.minecraft.block.*;
import work.lclpnet.mmoquark.block.VerticalSlabBlock;
import work.lclpnet.mmoquark.util.States;

public interface IBlockOverride {

    default SlabBlock provideSlab(Block baseBlock) {
        return new MMOSlabBlock(baseBlock);
    }

    default StairsBlock provideStairs(Block baseBlock) {
        return new MMOStairsBlock(baseBlock);
    }

    default VerticalSlabBlock provideVerticalSlab(SlabBlock baseBlock) {
        return new VerticalSlabBlock(baseBlock);
    }

    default WallBlock provideWall(Block baseBlock) {
        return new MMOWallBlock(baseBlock);
    }

    default PaneBlock providePane(Block baseBlock) {
        return new MMOPaneBlock(States.copyState(baseBlock), true);
    }
}
