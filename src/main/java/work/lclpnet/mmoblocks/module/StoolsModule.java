package work.lclpnet.mmoblocks.module;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.StoolBlock;
import work.lclpnet.mmoblocks.entity.StoolEntity;
import work.lclpnet.mmoblocks.entity.render.StoolEntityRenderer;

public class StoolsModule implements IModule, IClientModule {

    public static EntityType<StoolEntity> stoolEntity;

    @Override
    public void register() {
        for (DyeColor dye : DyeColor.values()) {
            new MMOBlockRegistrar(new StoolBlock(dye))
                    .register(String.format("%s_stool", dye.getName()), ItemGroup.DECORATIONS);
        }

        stoolEntity = Registry.register(
                Registry.ENTITY_TYPE,
                MMOBlocks.identifier("stool"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, StoolEntity::new)
                        .dimensions(EntityDimensions.fixed(6 / 16F, 0.5F))
                        .trackRangeChunks(3)
                        .trackedUpdateRate(Integer.MAX_VALUE)
                        .build()
        );
    }

    @Override
    public void registerClient() {
        EntityRendererRegistry.INSTANCE.register(stoolEntity, (manager, context) -> new StoolEntityRenderer(manager));
    }
}
