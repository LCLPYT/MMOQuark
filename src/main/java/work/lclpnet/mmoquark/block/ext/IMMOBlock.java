package work.lclpnet.mmoquark.block.ext;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;

public interface IMMOBlock {

    @Nullable
    BlockItem provideBlockItem(Item.Settings settings);
}
