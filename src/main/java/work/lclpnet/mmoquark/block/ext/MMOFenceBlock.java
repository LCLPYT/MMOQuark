package work.lclpnet.mmoquark.block.ext;

import net.minecraft.block.FenceBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class MMOFenceBlock extends FenceBlock implements IMMOBlock {

    public MMOFenceBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }
}
