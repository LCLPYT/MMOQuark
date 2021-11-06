package work.lclpnet.mmoblocks.block.ext;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public interface IMMOBlock {

    BlockItem provideBlockItem(Item.Settings settings);
}
