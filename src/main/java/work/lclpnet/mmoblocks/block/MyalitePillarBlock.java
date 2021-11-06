package work.lclpnet.mmoblocks.block;

import net.minecraft.block.Block;
import work.lclpnet.mmoblocks.block.ext.IMyaliteColorProvider;
import work.lclpnet.mmoblocks.block.ext.MMOPillarBlock;

public class MyalitePillarBlock extends MMOPillarBlock implements IMyaliteColorProvider {

    public MyalitePillarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Block getMyaliteBlock() {
        return this;
    }
}
