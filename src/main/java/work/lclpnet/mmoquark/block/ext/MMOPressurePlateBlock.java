package work.lclpnet.mmoquark.block.ext;

import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class MMOPressurePlateBlock extends PressurePlateBlock implements IMMOBlock {

    protected MMOPressurePlateBlock(ActivationRule type, Settings settings) {
        super(type, settings);
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }
}
