package work.lclpnet.mmoquark.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.Identifier;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.client.MMOClientEntities;
import work.lclpnet.mmoquark.entity.AdditionalSpawnData;
import work.lclpnet.mmoquark.networking.IClientPacketHandler;
import work.lclpnet.mmoquark.networking.IPacketDecoder;
import work.lclpnet.mmoquark.networking.MCPacket;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Objects;

public class MMOEntitySpawnS2CPacket extends MCPacket implements IClientPacketHandler {

    public static final Identifier ID = MMOQuark.identifier("entity_spawn");

    protected EntitySpawnS2CPacket originalPacket;
    @Nullable
    protected final PacketByteBuf additionalData;

    public MMOEntitySpawnS2CPacket(Entity entity) {
        super(ID);
        this.originalPacket = new EntitySpawnS2CPacket(entity);
        if (entity instanceof AdditionalSpawnData) {
            additionalData = PacketByteBufs.create();
            ((AdditionalSpawnData) entity).writeSpawnData(additionalData);
        } else this.additionalData = null;
    }

    public MMOEntitySpawnS2CPacket(EntitySpawnS2CPacket originalPacket, @Nullable PacketByteBuf additionalData) {
        super(ID);
        this.originalPacket = Objects.requireNonNull(originalPacket);
        this.additionalData = additionalData;
    }

    @Override
    public void encodeTo(PacketByteBuf buffer) throws IOException {
        originalPacket.write(buffer);
        if (additionalData != null) buffer.writeBytes(additionalData);
    }

    @Override
    public void handleClient(MinecraftClient client, ClientPlayNetworkHandler handler, PacketSender sender) {
        ClientWorld world = handler.getWorld();

        client.execute(() -> MMOClientEntities.spawnEntity(this.originalPacket, world, entity -> {
            if (entity instanceof AdditionalSpawnData && this.additionalData != null)
                ((AdditionalSpawnData) entity).readSpawnData(this.additionalData);
        }));
    }

    public static class Decoder implements IPacketDecoder<MMOEntitySpawnS2CPacket> {

        @Override
        public MMOEntitySpawnS2CPacket decode(PacketByteBuf buffer) throws IOException {
            EntitySpawnS2CPacket originalPacket = new EntitySpawnS2CPacket();
            originalPacket.read(buffer);

            PacketByteBuf additionalData = null;
            if (buffer.readableBytes() > 0) additionalData = PacketByteBufs.copy(buffer.retainedSlice());

            return new MMOEntitySpawnS2CPacket(originalPacket, additionalData);
        }
    }
}
