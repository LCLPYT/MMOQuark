package work.lclpnet.mmoquark.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.DyeColor;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.item.MMOItemRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.CandleBlock;

public class TallowAndCandlesModule implements IModule {

    @Override
    public void register() {
        new MMOItemRegistrar()
                .register(MMOQuark.identifier("tallow"), ItemGroup.MATERIALS);

        for (DyeColor dye : DyeColor.values())
            new MMOBlockRegistrar(new CandleBlock(dye))
                    .register(MMOQuark.identifier("%s_candle", dye.getName()), ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.HONEYCOMB_BLOCK))
                .register(MMOQuark.identifier("tallow_block"));
    }

}
