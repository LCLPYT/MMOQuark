package work.lclpnet.mmoquark.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.block.ext.MMOPaneBlock;
import work.lclpnet.mmoquark.MMOQuark;

public class GoldBarsModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(new MMOPaneBlock(AbstractBlock.Settings.copy(Blocks.IRON_BARS), true))
                .register(MMOQuark.identifier("gold_bars"), ItemGroup.DECORATIONS);
    }
}
