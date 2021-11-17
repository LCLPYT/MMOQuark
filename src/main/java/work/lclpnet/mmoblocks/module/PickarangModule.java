package work.lclpnet.mmoblocks.module;


import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.entity.MMOClientEntities;
import work.lclpnet.mmoblocks.entity.PickarangEntity;
import work.lclpnet.mmoblocks.entity.render.PickarangRenderer;
import work.lclpnet.mmoblocks.item.MMOItemRegistrar;
import work.lclpnet.mmoblocks.item.PickarangItem;

public class PickarangModule implements IModule, IClientModule {

    public static EntityType<PickarangEntity> pickarangType;
    private static final ThreadLocal<PickarangEntity> ACTIVE_PICKARANG = new ThreadLocal<>();

    public static Item pickarang;

    @Override
    public void register() {
        EntityType.EntityFactory<PickarangEntity> factory = PickarangEntity::new;
        pickarangType = Registry.register(
                Registry.ENTITY_TYPE,
                MMOBlocks.identifier("pickarang"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory)
                        .dimensions(EntityDimensions.fixed(6 / 16F, 0.5F))
                        .trackRangeChunks(3)
                        .trackedUpdateRate(Integer.MAX_VALUE)
                        .build()
        );

        new MMOItemRegistrar(settings -> new PickarangItem(settingsFor(800, false), false))
                .register("pickarang", ItemGroup.COMBAT);

        new MMOItemRegistrar(settings -> new PickarangItem(settingsFor(1040, true), true))
                .register("flamerang", ItemGroup.COMBAT);
    }

    private static Item.Settings settingsFor(int durability, boolean netherite) {
        Item.Settings settings = new FabricItemSettings()
                .maxCount(1)
                .group(ItemGroup.TOOLS);

        if (durability > 0) settings.maxDamage(durability);
        if(netherite) settings.fireproof();

        return settings;
    }

    @Override
    public void registerClient() {
        MMOClientEntities.registerNonLiving(pickarangType, (type, world) -> new PickarangEntity(pickarangType, world));
        EntityRendererRegistry.INSTANCE.register(pickarangType, (manager, context) -> new PickarangRenderer(manager));
    }

    public static void setActivePickarang(PickarangEntity pickarang) {
        ACTIVE_PICKARANG.set(pickarang);
    }
}
