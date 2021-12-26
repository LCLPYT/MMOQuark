package work.lclpnet.mmoquark.module;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.GlowceliumBlock;
import work.lclpnet.mmoquark.block.GlowshroomBlock;
import work.lclpnet.mmoquark.block.GlowshroomPlantBlock;

public class GlowshroomModule implements IModule {

    public static Block glowcelium, glowshroom, glowshroom_block, glowshroom_stem;

    @Override
    public void register() {
        new MMOBlockRegistrar(glowcelium = new GlowceliumBlock())
                .register(MMOQuark.identifier("glowcelium"));

        new MMOBlockRegistrar(glowshroom = new GlowshroomPlantBlock())
                .register(MMOQuark.identifier("glowshroom"), ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(glowshroom_block = new GlowshroomBlock())
                .register(MMOQuark.identifier("glowshroom_block"));

        new MMOBlockRegistrar(glowshroom_stem = new GlowshroomBlock())
                .register(MMOQuark.identifier("glowshroom_stem"));

        MorePottedPlantsModule.addPottedPlant(glowshroom, "glowshroom", s -> s.luminance(b -> 14));
    }
}
