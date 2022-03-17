package work.lclpnet.mmoquark.block.ext;

import net.minecraft.block.PlantBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import work.lclpnet.mmocontent.block.ext.IMMOBlock;

public class QPlantBlock extends PlantBlock implements IMMOBlock {

    public QPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }
}
