package work.lclpnet.mmoquark.module.content;

import net.minecraft.block.Block;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;

public class VariantTuple {

    public final Block block;
    public final MMOBlockRegistrar.Result extra;

    public VariantTuple(Block block, MMOBlockRegistrar.Result extra) {
        this.block = block;
        this.extra = extra;
    }
}
