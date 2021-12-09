package work.lclpnet.mmoblocks.module;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.entity.GlassItemFrameEntity;
import work.lclpnet.mmoblocks.item.MMOItemFrameItem;
import work.lclpnet.mmoblocks.item.MMOItemRegistrar;

public class ItemFramesModule implements IModule {

    public static EntityType<GlassItemFrameEntity> glassFrameEntity;

    public static Item glassFrame, glowingGlassFrame;

    @Override
    public void register() {
        glassFrameEntity = Registry.register(
                Registry.ENTITY_TYPE,
                MMOBlocks.identifier("glass_frame"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType.EntityFactory<GlassItemFrameEntity>) GlassItemFrameEntity::new)
                        .dimensions(EntityDimensions.fixed(0.5F, 0.5F))
                        .trackRangeChunks(10)
                        .trackedUpdateRate(Integer.MAX_VALUE)
                        .forceTrackedVelocityUpdates(false)
                        .build()
        );

        new MMOItemRegistrar(settings -> glassFrame = new MMOItemFrameItem(settings, GlassItemFrameEntity::new))
                .register("glass_item_frame", ItemGroup.DECORATIONS);

        new MMOItemRegistrar(settings -> glowingGlassFrame = new MMOItemFrameItem(settings, (w, p, d) -> {
            GlassItemFrameEntity e = new GlassItemFrameEntity(w, p, d);
            e.getDataTracker().set(GlassItemFrameEntity.IS_SHINY, true);
            return e;
        })).register("glowing_glass_item_frame", ItemGroup.DECORATIONS);
    }
}
