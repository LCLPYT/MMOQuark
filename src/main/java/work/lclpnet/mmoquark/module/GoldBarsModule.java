package work.lclpnet.mmoquark.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmoquark.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.block.ext.MMOPaneBlock;

public class GoldBarsModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(new MMOPaneBlock(AbstractBlock.Settings.copy(Blocks.IRON_BARS), true))
                .register("gold_bars", ItemGroup.DECORATIONS);
    }
}
