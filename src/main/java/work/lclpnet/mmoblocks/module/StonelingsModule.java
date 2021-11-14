package work.lclpnet.mmoblocks.module;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
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

    @Override
    public void registerClient() {
        EntityRendererRegistry.INSTANCE.register(stonelingType, (manager, context) -> new StonelingRenderer(manager));
    }
}
