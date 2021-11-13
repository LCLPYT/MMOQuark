package work.lclpnet.mmoblocks.module;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmoblocks.block.ChorusVegetationBlock;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;

public class ChorusVegetationModule implements IModule {

    @Override
    public void register() {
        Block chorus_weeds, chorus_twist;

        new MMOBlockRegistrar(chorus_weeds = new ChorusVegetationBlock(true))
                .register("chorus_weeds", ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(chorus_twist = new ChorusVegetationBlock(false))
                .register("chorus_twist", ItemGroup.DECORATIONS);

        MorePottedPlantsModule.addPottedPlant(chorus_weeds, "chorus_weeds");
        MorePottedPlantsModule.addPottedPlant(chorus_twist, "chorus_twist");
    }
}
