package work.lclpnet.mmoblocks.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.ext.MMOPaneBlock;

public class GoldBarsModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(new MMOPaneBlock(AbstractBlock.Settings.copy(Blocks.IRON_BARS), true))
                .register("gold_bars", ItemGroup.DECORATIONS);
    }
}
