package work.lclpnet.mmoblocks.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public interface IClientPacketHandler {

    @Environment(EnvType.CLIENT)
    void handleClient(MinecraftClient client, ClientPlayNetworkHandler handler, PacketSender sender);

}
