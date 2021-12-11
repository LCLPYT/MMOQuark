package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.entity.CrabEntity;
import work.lclpnet.mmoquark.item.MMOItemRegistrar;
import work.lclpnet.mmoquark.util.MMOEntityAttributes;

public class CrabsModule implements IModule {

    public static EntityType<CrabEntity> crabType;

    public static Tag<Block> crabSpawnableTag;

    @Override
    public void register() {
        new MMOItemRegistrar(settings -> new Item(settings
                .food(new FoodComponent.Builder()
                        .meat()
                        .hunger(1)
                        .saturationModifier(0.3F)
                        .build())
        )).register("crab_leg", ItemGroup.FOOD);

        new MMOItemRegistrar(settings -> new Item(settings
                .food(new FoodComponent.Builder()
                        .meat()
                        .hunger(8)
                        .saturationModifier(0.8F)
                        .build())
        )).register("cooked_crab_leg", ItemGroup.FOOD);

        new MMOItemRegistrar()
                .register("crab_shell", ItemGroup.BREWING);

        crabSpawnableTag = TagRegistry.block(MMOQuark.identifier("crab_spawnable"));

        crabType = Registry.register(
                Registry.ENTITY_TYPE,
                MMOQuark.identifier("crab"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, (EntityType.EntityFactory<CrabEntity>) CrabEntity::new)
                        .dimensions(EntityDimensions.changing(0.9F, 0.5F))
                        .trackRangeChunks(8)
                        .build()
        );

        MMOEntityAttributes.registerDefaultAttributes(crabType, CrabEntity.createMobAttributes());

        MMOItemRegistrar.registerSpawnEgg(crabType, "crab", 0x893c22, 0x916548);
    }
}
