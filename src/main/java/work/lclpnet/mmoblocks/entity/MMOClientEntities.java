package work.lclpnet.mmoblocks.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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

    public static void spawnEntity(EntitySpawnS2CPacket packet, ClientWorld world, @Nullable Consumer<Entity> transformer) {
        if (world == null) throw new IllegalStateException("World must not be null");

        double x = packet.getX();
        double y = packet.getY();
        double z = packet.getZ();
        EntityType<?> type = packet.getEntityTypeId();
        Entity entity = MMOClientEntities.createEntity(type, world);
        if (entity == null) return;

        int i = packet.getId();
        entity.updateTrackedPosition(x, y, z);
        entity.refreshPositionAfterTeleport(x, y, z);
        entity.pitch = (float)(packet.getPitch() * 360) / 256.0F;
        entity.yaw = (float)(packet.getYaw() * 360) / 256.0F;
        entity.setEntityId(i);
        entity.setUuid(packet.getUuid());
        if (transformer != null) transformer.accept(entity);
        world.addEntity(i, entity);
    }

    public interface EntityFactory<T extends Entity> {
        T create(EntityType<? extends Entity> type, World world);
    }
}
