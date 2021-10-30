package work.lclpnet.mmoblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import work.lclpnet.mmoblocks.util.States;

public class MMOStairsBlock extends StairsBlock implements IMMOBlock {

    protected MMOStairsBlock(Block parent) {
        super(parent.getDefaultState(), States.copyState(parent));
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }
}
