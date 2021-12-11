package work.lclpnet.mmoquark.module;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.ChorusVegetationBlock;

public class ChorusVegetationModule implements IModule {

    @Override
    public void register() {
        Block chorus_weeds, chorus_twist;

        new MMOBlockRegistrar(chorus_weeds = new ChorusVegetationBlock(true))
                .register(MMOQuark.identifier("chorus_weeds"), ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(chorus_twist = new ChorusVegetationBlock(false))
                .register(MMOQuark.identifier("chorus_twist"), ItemGroup.DECORATIONS);

        MorePottedPlantsModule.addPottedPlant(chorus_weeds, "chorus_weeds");
        MorePottedPlantsModule.addPottedPlant(chorus_twist, "chorus_twist");
    }
}
