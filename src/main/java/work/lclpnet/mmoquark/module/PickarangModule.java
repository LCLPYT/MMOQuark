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
import work.lclpnet.mmoquark.entity.PickarangEntity;
import work.lclpnet.mmoquark.item.PickarangItem;

public class PickarangModule implements IModule {

    public static EntityType<PickarangEntity> pickarangType;
    private static final ThreadLocal<PickarangEntity> ACTIVE_PICKARANG = new ThreadLocal<>();

    public static Item pickarang;

    @Override
    public void register() {
        pickarangType = Registry.register(
                Registry.ENTITY_TYPE,
                MMOQuark.identifier("pickarang"),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, (EntityType.EntityFactory<PickarangEntity>) PickarangEntity::new)
                        .dimensions(EntityDimensions.fixed(6 / 16F, 0.5F))
                        .trackRangeChunks(3)
                        .trackedUpdateRate(Integer.MAX_VALUE)
                        .build()
        );

        new MMOItemRegistrar(settings -> new PickarangItem(settingsFor(settings, 800, false), false))
                .register(MMOQuark.identifier("pickarang"), ItemGroup.COMBAT);

        new MMOItemRegistrar(settings -> new PickarangItem(settingsFor(settings, 1040, true), true))
                .register(MMOQuark.identifier("flamerang"), ItemGroup.COMBAT);
    }

    private static Item.Settings settingsFor(Item.Settings ref, int durability, boolean netherite) {
        ref.maxCount(1);

        if (durability > 0) ref.maxDamage(durability);
        if (netherite) ref.fireproof();

        return ref;
    }

    public static void setActivePickarang(PickarangEntity pickarang) {
        ACTIVE_PICKARANG.set(pickarang);
    }
}
