package work.lclpnet.mmoquark.module;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.item.MMOItemRegistrar;
import work.lclpnet.mmoquark.MMOQuark;

public class FrogsModule implements IModule {

    @Override
    public void register() {
        // No Frog Entity, only items I'm sry :(
        new MMOItemRegistrar(settings -> new Item(settings
                .food(new FoodComponent.Builder()
                        .meat()
                        .hunger(2)
                        .saturationModifier(0.3F)
                        .build())))
                .register(MMOQuark.identifier("frog_leg"), ItemGroup.FOOD);

        new MMOItemRegistrar(settings -> new Item(settings
                .food(new FoodComponent.Builder()
                        .meat()
                        .hunger(4)
                        .saturationModifier(1.25F)
                        .build())))
                .register(MMOQuark.identifier("cooked_frog_leg"), ItemGroup.FOOD);

        new MMOItemRegistrar(settings -> new Item(settings
                .food(new FoodComponent.Builder()
                        .meat()
                        .hunger(4)
                        .saturationModifier(2.5F)
                        .build())))
                .register(MMOQuark.identifier("golden_frog_leg"), ItemGroup.FOOD);
    }
}
