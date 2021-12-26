package work.lclpnet.mmoquark.module;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.ChorusVegetationBlock;

public class ChorusVegetationModule implements IModule {

    public static Block chorusWeeds, chorusTwist;
    public static FlowerPotBlock chorusWeedsPot, chorusTwistPot;

    @Override
    public void register() {
        chorusWeeds = new ChorusVegetationBlock(true);
        chorusTwist = new ChorusVegetationBlock(false);

        new MMOBlockRegistrar(chorusWeeds)
                .register(MMOQuark.identifier("chorus_weeds"), ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(chorusTwist)
                .register(MMOQuark.identifier("chorus_twist"), ItemGroup.DECORATIONS);

        chorusWeedsPot = MorePottedPlantsModule.addPottedPlant(chorusWeeds, "chorus_weeds");
        chorusTwistPot = MorePottedPlantsModule.addPottedPlant(chorusTwist, "chorus_twist");
    }
}
