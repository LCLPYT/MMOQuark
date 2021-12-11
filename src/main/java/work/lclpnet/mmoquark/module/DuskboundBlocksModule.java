package work.lclpnet.mmoquark.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;

public class DuskboundBlocksModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.PURPUR_BLOCK))
                .withSlab()
                .withStairs()
                .withVerticalSlab()
                .register(MMOQuark.identifier("duskbound_block"));

        new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.PURPUR_BLOCK)
                .luminance(b -> 15))
                .register(MMOQuark.identifier("duskbound_lantern"));
    }
}
