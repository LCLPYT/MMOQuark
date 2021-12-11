package work.lclpnet.mmoquark.client;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.render.TexturedRenderLayers;
import work.lclpnet.mmoquark.client.module.*;
import work.lclpnet.mmoquark.module.IClientModule;
import work.lclpnet.mmoquark.networking.MCNetworking;
import work.lclpnet.mmoquark.util.MMORenderLayers;

import java.util.Set;

@Environment(EnvType.CLIENT)
public class MMOQuarkClient implements ClientModInitializer {

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
