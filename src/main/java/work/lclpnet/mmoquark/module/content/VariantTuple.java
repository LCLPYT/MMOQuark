package work.lclpnet.mmoquark.module.content;

import net.minecraft.block.Block;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;

public record VariantTuple(Block block, MMOBlockRegistrar.Result extra) {
}
