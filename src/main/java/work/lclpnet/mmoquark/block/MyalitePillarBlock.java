package work.lclpnet.mmoquark.block;

import net.minecraft.block.Block;
import work.lclpnet.mmocontent.block.ext.MMOPillarBlock;

public class MyalitePillarBlock extends MMOPillarBlock implements IMyaliteColorProvider {

    public MyalitePillarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Block getMyaliteBlock() {
        return this;
    }
}
