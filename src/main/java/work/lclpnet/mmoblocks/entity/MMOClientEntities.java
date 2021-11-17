package work.lclpnet.mmoblocks.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class MMOClientEntities {

    private static final Map<EntityType<? extends Entity>, EntityFactory<? extends Entity>> registry = new HashMap<>();

    public static <T extends Entity> void registerNonLiving(EntityType<T> entityType, EntityFactory<T> factory) {
        registry.put(entityType, factory);
    }

    @Nullable
    public static Entity createEntity(EntityType<? extends Entity> entityType, World world) {
        EntityFactory<? extends Entity> entityFactory = registry.get(entityType);
        if (entityFactory == null) return null;

        return entityFactory.create(entityType, world);
    }

    public interface EntityFactory<T extends Entity> {
        T create(EntityType<? extends Entity> type, World world);
    }
}
