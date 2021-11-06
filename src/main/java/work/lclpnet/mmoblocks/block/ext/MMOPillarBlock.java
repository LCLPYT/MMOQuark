package work.lclpnet.mmoblocks.block.ext;

import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class MMOPillarBlock extends PillarBlock implements IMMOBlock {

    public MMOPillarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }
}
