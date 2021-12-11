package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmocontent.item.MMOItemRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.entity.GlassItemFrameEntity;
import work.lclpnet.mmoquark.item.CustomItemFrameItem;

public class ItemFramesModule implements IModule {

    public static EntityType<GlassItemFrameEntity> glassFrameEntity;

    public static Item glassFrame, glowingGlassFrame;

    @Override
    public void register() {
        glassFrameEntity = Registry.register(
                Registry.ENTITY_TYPE,
                MMOQuark.identifier("glass_frame"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType.EntityFactory<GlassItemFrameEntity>) GlassItemFrameEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5F, 0.5F))
                        .trackRangeChunks(10)
                        .trackedUpdateRate(Integer.MAX_VALUE)
                        .forceTrackedVelocityUpdates(false)
                        .build()
        );

        new MMOItemRegistrar(settings -> glassFrame = new CustomItemFrameItem(settings, GlassItemFrameEntity::new))
                .register(MMOQuark.identifier("glass_item_frame"), ItemGroup.DECORATIONS);

        new MMOItemRegistrar(settings -> glowingGlassFrame = new CustomItemFrameItem(settings, (w, p, d) -> {
            GlassItemFrameEntity e = new GlassItemFrameEntity(w, p, d);
            e.getDataTracker().set(GlassItemFrameEntity.IS_SHINY, true);
            return e;
        })).register(MMOQuark.identifier("glowing_glass_item_frame"), ItemGroup.DECORATIONS);
    }
}
