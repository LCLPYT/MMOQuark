package work.lclpnet.mmoquark.module;

import net.minecraft.block.Blocks;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.block.ext.MMOPillarBlock;
import work.lclpnet.mmoquark.MMOQuark;

import static net.minecraft.block.AbstractBlock.Settings.copy;

public class IndustrialPaletteModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(copy(Blocks.IRON_BLOCK))
                .withSlab().withStairs().withVerticalSlab()
                .register(MMOQuark.identifier("iron_plate"));

        new MMOBlockRegistrar(copy(Blocks.IRON_BLOCK))
                .withSlab().withStairs().withVerticalSlab()
                .register(MMOQuark.identifier("rusty_iron_plate"));

        new MMOBlockRegistrar(new MMOPillarBlock(copy(Blocks.IRON_BLOCK)))
                .register(MMOQuark.identifier("iron_pillar"));
    }
}
