package work.lclpnet.mmoblocks.module;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
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
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.entity.CrabEntity;
import work.lclpnet.mmoblocks.entity.render.CrabRenderer;
import work.lclpnet.mmoblocks.item.MMOItemRegistrar;
import work.lclpnet.mmoblocks.util.MMOEntityAttributes;

public class CrabsModule implements IModule, IClientModule {

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

        crabSpawnableTag = TagRegistry.block(MMOBlocks.identifier("crab_spawnable"));



        crabType = Registry.register(
                Registry.ENTITY_TYPE,
                MMOBlocks.identifier("crab"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, (EntityType.EntityFactory<CrabEntity>) CrabEntity::new)
                        .dimensions(EntityDimensions.changing(0.9F, 0.5F))
                        .trackRangeChunks(8)
                        .build()
        );

        MMOEntityAttributes.registerDefaultAttributes(crabType, CrabEntity.createMobAttributes());

        MMOItemRegistrar.registerSpawnEgg(crabType, "crab", 0x893c22, 0x916548);
    }

    @Override
    public void registerClient() {
        EntityRendererRegistry.INSTANCE.register(crabType, (manager, context) -> new CrabRenderer(manager));
    }
}
