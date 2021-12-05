package work.lclpnet.mmoblocks.module;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmoblocks.item.MMOItemRegistrar;

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
                .register("frog_leg", ItemGroup.DECORATIONS);

        new MMOItemRegistrar(settings -> new Item(settings
                .food(new FoodComponent.Builder()
                        .meat()
                        .hunger(4)
                        .saturationModifier(1.25F)
                        .build())))
                .register("cooked_frog_leg", ItemGroup.DECORATIONS);

        new MMOItemRegistrar(settings -> new Item(settings
                .food(new FoodComponent.Builder()
                        .meat()
                        .hunger(4)
                        .saturationModifier(2.5F)
                        .build())))
                .register("golden_frog_leg", ItemGroup.DECORATIONS);
    }
}
