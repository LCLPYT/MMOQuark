package work.lclpnet.mmoblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import work.lclpnet.mmoblocks.util.States;

public class MMOWallBlock extends WallBlock implements IMMOBlock {

    public MMOWallBlock(Block parent) {
        super(States.copyState(parent));
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }
}
