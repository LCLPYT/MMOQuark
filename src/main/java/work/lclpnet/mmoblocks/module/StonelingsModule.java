package work.lclpnet.mmoblocks.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.entity.StonelingEntity;
import work.lclpnet.mmoblocks.entity.render.StonelingRenderer;
import work.lclpnet.mmoblocks.item.DiamondHeartItem;
import work.lclpnet.mmoblocks.item.MMOItemRegistrar;
import work.lclpnet.mmoblocks.util.MMOEntityAttributes;

public class StonelingsModule implements IModule, IClientModule {

    public static EntityType<StonelingEntity> stonelingType;

    @Override
    public void register() {
        new MMOItemRegistrar(DiamondHeartItem::new)
                .register("diamond_heart", ItemGroup.MISC);

        stonelingType = Registry.register(
                Registry.ENTITY_TYPE,
                MMOBlocks.identifier("stoneling"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, StonelingEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5F, 0.9F))
                        .trackRangeChunks(8)
                        .build()
        );

        MMOEntityAttributes.registerDefaultAttributes(stonelingType, StonelingEntity.createMobAttributes());

        MMOItemRegistrar.registerSpawnEgg(stonelingType, "stoneling", 0xA1A1A1, 0x505050);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void registerClient() {
        // Leave this an anonymous class, as the KnotClassLoader does not strip the generated lambda method
        EntityRendererRegistry.INSTANCE.register(stonelingType, new EntityRendererRegistry.Factory() {
            @Override
            public EntityRenderer<? extends Entity> create(EntityRenderDispatcher manager, EntityRendererRegistry.Context context) {
                return new StonelingRenderer(manager);
            }
        });
    }
}
