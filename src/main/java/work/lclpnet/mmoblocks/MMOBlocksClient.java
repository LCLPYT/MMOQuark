package work.lclpnet.mmoblocks;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.render.TexturedRenderLayers;
import work.lclpnet.mmoblocks.blockentity.MMOItemBlockEntities;
import work.lclpnet.mmoblocks.module.*;
import work.lclpnet.mmoblocks.util.MMORenderLayers;

import java.util.Set;

public class MMOBlocksClient implements ClientModInitializer {

    public static final Set<IClientModule> CLIENT_MODULES = ImmutableSet.of(
            new VariantChestsModule(),
            new StoolsModule(),
            new StonelingsModule(),
            new PickarangModule()
    );

    @Override
    public void onInitializeClient() {
        MMORenderLayers.init();

        CLIENT_MODULES.forEach(IClientModule::registerClient);

        // init dummy block entity item entities
        MMOItemBlockEntities.init();

        ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE)
                .register((atlasTexture, registry) -> CLIENT_MODULES.forEach(module -> module.registerSprites(atlasTexture, registry)));
    }

}
