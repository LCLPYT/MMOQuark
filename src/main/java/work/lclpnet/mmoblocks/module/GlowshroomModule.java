package work.lclpnet.mmoblocks.module;

import net.minecraft.block.Block;
import work.lclpnet.mmoblocks.block.GlowceliumBlock;
import work.lclpnet.mmoblocks.block.GlowshroomBlock;
import work.lclpnet.mmoblocks.block.GlowshroomPlantBlock;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;

public class GlowshroomModule implements IModule {

    public static Block glowcelium, glowshroom_block, glowshroom_stem;

    @Override
    public void register() {
        new MMOBlockRegistrar(glowcelium = new GlowceliumBlock())
                .register("glowcelium");

        new MMOBlockRegistrar(new GlowshroomPlantBlock())
                .register("glowshroom");

        new MMOBlockRegistrar(glowshroom_block = new GlowshroomBlock())
                .register("glowshroom_block");

        new MMOBlockRegistrar(glowshroom_stem = new GlowshroomBlock())
                .register("glowshroom_stem");
    }
}
