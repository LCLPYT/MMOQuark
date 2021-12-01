package work.lclpnet.mmoblocks.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import work.lclpnet.mmoblocks.networking.packets.MMOEntitySpawnS2CPacket;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MCNetworking {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<Identifier, IPacketDecoder<? extends MCPacket>> packetDecoderMap = new HashMap<>();

    /* */

    public static void registerPackets() {
        register(MMOEntitySpawnS2CPacket.ID, new MMOEntitySpawnS2CPacket.Decoder());
    }

    /* */

    private static void register(Identifier id, IPacketDecoder<? extends MCPacket> serializer) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(serializer);

        packetDecoderMap.put(id, serializer);
    }

    @Environment(EnvType.CLIENT)
    public static void registerClientPacketHandlers() {
        packetDecoderMap.forEach((id, serializer) -> ClientPlayNetworking.registerGlobalReceiver(id,
                (client, handler, buf, responseSender) -> {
                    try {
                        serializer.handleClient(serializer.decode(buf), client, handler, responseSender);
                    } catch (IOException e) {
                        LOGGER.error("Error decoding packet", e);
                    }
                })
        );
    }

    public static void registerServerPacketHandlers() {
        packetDecoderMap.forEach((id, serializer) -> ServerPlayNetworking.registerGlobalReceiver(id,
                (server, player, handler, buf, responseSender) -> {
                    try {
                        serializer.handleServer(serializer.decode(buf), server, player, handler, responseSender);
                    } catch (IOException e) {
                        LOGGER.error("Error decoding packet", e);
                    }
                })
        );
    }

    public static void sendPacketTo(MCPacket packet, ServerPlayerEntity player) {
        try {
            PacketByteBuf buf = toPacketBuffer(packet);
            ServerPlayNetworking.send(player, packet.getIdentifier(), buf);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public static void sendToAllTracking(Entity tracked, MCPacket packet) {
        Objects.requireNonNull(tracked);
        Objects.requireNonNull(packet);
        PlayerLookup.tracking(tracked).forEach(p -> sendPacketTo(packet, p));
    }

    public static void sendToAllTrackingIncludingSelf(LivingEntity living, MCPacket packet) {
        MCNetworking.sendToAllTracking(living, packet);

        // if the entity is a player, the packet will not be sent to the player, since players do not track themselves.
        if (living instanceof ServerPlayerEntity) MCNetworking.sendPacketTo(packet, (ServerPlayerEntity) living);
    }

    public static Packet<?> createVanillaS2CPacket(MCPacket packet) throws IOException {
        Objects.requireNonNull(packet, "Packet cannot be null");

        Identifier channelName = packet.getIdentifier();
        Objects.requireNonNull(channelName, "Channel name cannot be null");

        PacketByteBuf buf = toPacketBuffer(packet);
        return ServerPlayNetworking.createS2CPacket(channelName, buf);
    }

    @Environment(EnvType.CLIENT)
    public static void sendPacketToServer(MCPacket packet) {
        try {
            PacketByteBuf buf = toPacketBuffer(packet);
            ClientPlayNetworking.send(packet.getIdentifier(), buf);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    @Nonnull
    private static PacketByteBuf toPacketBuffer(MCPacket packet) throws IOException {
        PacketByteBuf buf = PacketByteBufs.create();
        packet.encodeTo(buf);
        return buf;
    }

    public static Packet<?> createMMOSpawnPacket(Entity entity) {
        try {
            return MCNetworking.createVanillaS2CPacket(new MMOEntitySpawnS2CPacket(entity));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create spawn packet", e);
        }
    }
}
