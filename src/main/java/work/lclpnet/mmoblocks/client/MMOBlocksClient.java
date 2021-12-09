package work.lclpnet.mmoblocks.client;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.render.TexturedRenderLayers;
import work.lclpnet.mmoblocks.client.module.*;
import work.lclpnet.mmoblocks.module.IClientModule;
import work.lclpnet.mmoblocks.networking.MCNetworking;
import work.lclpnet.mmoblocks.util.MMORenderLayers;

import java.util.Set;

@Environment(EnvType.CLIENT)
public class MMOBlocksClient implements ClientModInitializer {

    public static final Set<IClientModule> CLIENT_MODULES = ImmutableSet.of(
            new VariantChestsClientModule(),
            new StoolsClientModule(),
            new StonelingsClientModule(),
            new PickarangClientModule(),
            new CrabsClientModule(),
            new PipesClientModule(),
            new ItemFramesClientModule(),
            new MorePottedPlantsClientModule()
    );

    @Override
    public void onInitializeClient() {
        MMORenderLayers.init();
        MCNetworking.registerClientPacketHandlers();

        CLIENT_MODULES.forEach(IClientModule::registerClient);

        ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE)
                .register((atlasTexture, registry) -> CLIENT_MODULES.forEach(module -> module.registerSprites(atlasTexture, registry)));
    }

}
