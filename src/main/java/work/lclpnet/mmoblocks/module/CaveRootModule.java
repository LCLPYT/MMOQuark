package work.lclpnet.mmoblocks.module;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.RootBlock;
import work.lclpnet.mmoblocks.item.MMOItemRegistrar;

public class CaveRootModule implements IModule {

    @Override
    public void register() {
        RootBlock root;
        new MMOBlockRegistrar(root = new RootBlock())
                .register("root", ItemGroup.DECORATIONS);

        new MMOItemRegistrar(settings -> new Item(settings
                .food(new FoodComponent.Builder()
                        .hunger(3)
                        .saturationModifier(0.4F)
                        .build())
        )).register("root_item", ItemGroup.FOOD);

        MorePottedPlantsModule.addPottedPlant(root, "cave_root");
    }
}
