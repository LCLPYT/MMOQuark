package work.lclpnet.mmoquark.block.ext;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import work.lclpnet.mmoquark.util.States;

public class MMOStairsBlock extends StairsBlock implements IMMOBlock {

    public MMOStairsBlock(Block parent) {
        super(parent.getDefaultState(), States.copyState(parent));
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }
}
