package work.lclpnet.mmoblocks.block.ext;

import net.minecraft.block.Block;
import net.minecraft.block.PaneBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import work.lclpnet.mmoblocks.util.States;

public class MMOPaneBlock extends PaneBlock implements IMMOBlock {

    protected MMOPaneBlock(Block parent) {
        super(States.copyState(parent));
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }
}