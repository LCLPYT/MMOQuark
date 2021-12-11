package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmocontent.entity.MMOEntityAttributes;
import work.lclpnet.mmocontent.item.MMOItemRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.entity.StonelingEntity;
import work.lclpnet.mmoquark.item.DiamondHeartItem;

public class StonelingsModule implements IModule {

    public static EntityType<StonelingEntity> stonelingType;

    @Override
    public void register() {
        new MMOItemRegistrar(DiamondHeartItem::new)
                .register(MMOQuark.identifier("diamond_heart"), ItemGroup.MISC);

        stonelingType = Registry.register(
                Registry.ENTITY_TYPE,
                MMOQuark.identifier("stoneling"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, StonelingEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5F, 0.9F))
                        .trackRangeChunks(8)
                        .build()
        );

        MMOEntityAttributes.registerDefaultAttributes(stonelingType, StonelingEntity.createMobAttributes());

        MMOItemRegistrar.registerSpawnEgg(stonelingType, "stoneling", 0xA1A1A1, 0x505050, MMOQuark::identifier);
    }
}
