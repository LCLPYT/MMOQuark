package work.lclpnet.mmoquark.module;

import net.minecraft.block.FlowerPotBlock;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.item.MMOItemRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.RootBlock;

public class CaveRootModule implements IModule {

    public static RootBlock root;
    public static FlowerPotBlock pottedRoots;

    @Override
    public void register() {
        root = new RootBlock();
        new MMOBlockRegistrar(root)
                .register(MMOQuark.identifier("root"), ItemGroup.DECORATIONS);

        new MMOItemRegistrar(settings -> new Item(settings
                .food(new FoodComponent.Builder()
                        .hunger(3)
                        .saturationModifier(0.4F)
                        .build())
        )).register(MMOQuark.identifier("root_item"), ItemGroup.FOOD);

        pottedRoots = MorePottedPlantsModule.addPottedPlant(root, "cave_root");
    }
}
