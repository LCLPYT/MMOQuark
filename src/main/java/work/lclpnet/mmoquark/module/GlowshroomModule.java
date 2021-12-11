package work.lclpnet.mmoquark.module;

import net.minecraft.block.Block;
import work.lclpnet.mmoquark.block.GlowceliumBlock;
import work.lclpnet.mmoquark.block.GlowshroomBlock;
import work.lclpnet.mmoquark.block.GlowshroomPlantBlock;
import work.lclpnet.mmoquark.block.MMOBlockRegistrar;

public class GlowshroomModule implements IModule {

    public static Block glowcelium, glowshroom, glowshroom_block, glowshroom_stem;

    @Override
    public void register() {
        new MMOBlockRegistrar(glowcelium = new GlowceliumBlock())
                .register("glowcelium");

        new MMOBlockRegistrar(glowshroom = new GlowshroomPlantBlock())
                .register("glowshroom");

        new MMOBlockRegistrar(glowshroom_block = new GlowshroomBlock())
                .register("glowshroom_block");

        new MMOBlockRegistrar(glowshroom_stem = new GlowshroomBlock())
                .register("glowshroom_stem");

        MorePottedPlantsModule.addPottedPlant(glowshroom, "glowshroom", s -> s.luminance(b -> 14));
    }
}
