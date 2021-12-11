package work.lclpnet.mmoquark.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.DyeColor;
import work.lclpnet.mmoquark.block.CandleBlock;
import work.lclpnet.mmoquark.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.item.MMOItemRegistrar;

public class TallowAndCandlesModule implements IModule {

    @Override
    public void register() {
        new MMOItemRegistrar()
                .register("tallow", ItemGroup.MATERIALS);

        for (DyeColor dye : DyeColor.values())
            new MMOBlockRegistrar(new CandleBlock(dye))
                    .register(String.format("%s_candle", dye.getName()), ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.HONEYCOMB_BLOCK))
                .register("tallow_block");
    }

}
