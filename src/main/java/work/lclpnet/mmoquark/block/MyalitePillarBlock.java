package work.lclpnet.mmoquark.block;

import net.minecraft.block.Block;
import work.lclpnet.mmoquark.block.ext.IMyaliteColorProvider;
import work.lclpnet.mmoquark.block.ext.MMOPillarBlock;

public class MyalitePillarBlock extends MMOPillarBlock implements IMyaliteColorProvider {

    public MyalitePillarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Block getMyaliteBlock() {
        return this;
    }
}
