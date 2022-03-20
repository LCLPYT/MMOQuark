package work.lclpnet.mmoquark.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.LadderBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import work.lclpnet.mmocontent.block.ext.IMMOBlock;

public class VariantLadderBlock extends LadderBlock implements IMMOBlock {

    public VariantLadderBlock() {
        this(FabricBlockSettings.copyOf(Blocks.LADDER));
    }

    public VariantLadderBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }
}
