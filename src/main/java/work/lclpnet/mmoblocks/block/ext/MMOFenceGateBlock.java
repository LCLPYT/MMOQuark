package work.lclpnet.mmoblocks.block.ext;

import net.minecraft.block.FenceGateBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class MMOFenceGateBlock extends FenceGateBlock implements IMMOBlock {

    public MMOFenceGateBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }
}
