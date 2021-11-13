package work.lclpnet.mmoblocks.module;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.SpeleothemBlock;

public class SpeleothemsModule implements IModule {

    @Override
    public void register() {
        make("stone", Blocks.STONE, false);
        make("netherrack", Blocks.NETHERRACK, true);
        make("granite", Blocks.GRANITE, false);
        make("diorite", Blocks.DIORITE, false);
        make("andesite", Blocks.ANDESITE, false);

        make("marble", NewStoneTypesModule.marbleBlock, false);
        make("limestone", NewStoneTypesModule.limestoneBlock, false);
        make("jasper", NewStoneTypesModule.jasperBlock, false);
        make("slate", NewStoneTypesModule.slateBlock, false);
        make("basalt", NewStoneTypesModule.basaltBlock, false);
    }

    private void make(String name, Block parent, boolean nether) {
        new MMOBlockRegistrar(new SpeleothemBlock(parent, nether))
                .register(String.format("%s_speleothem", name), ItemGroup.DECORATIONS);
    }
}
