package work.lclpnet.mmoblocks.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.IOException;

public interface IPacketDecoder<T extends MCPacket> {

    T decode(PacketByteBuf buffer) throws IOException;

    @Environment(EnvType.CLIENT)
    default void handleClient(MCPacket msg, MinecraftClient client, ClientPlayNetworkHandler handler, PacketSender responseSender) {
        if (msg instanceof IClientPacketHandler) ((IClientPacketHandler) msg).handleClient(client, handler, responseSender);
        else System.err.printf("Unhandled packet \"%s\" received on client.%n", msg.getIdentifier());
    }

    default void handleServer(MCPacket msg, MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketSender responseSender) {
        if (msg instanceof IServerPacketHandler) ((IServerPacketHandler) msg).handleServer(server, player, handler, responseSender);
        else System.err.printf("Unhandled packet \"%s\" received on server.%n", msg.getIdentifier());
    }

}
