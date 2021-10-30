package work.lclpnet.mmoblocks.block;

import net.minecraft.block.Block;

public class MyalitePillarBlock extends MMOPillarBlock implements IMyaliteColorProvider {

    public MyalitePillarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Block getMyaliteBlock() {
        return this;
    }
}
