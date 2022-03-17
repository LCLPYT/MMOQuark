package work.lclpnet.mmoquark.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.Block;
import net.minecraft.client.texture.SpriteAtlasTexture;
import work.lclpnet.mmoquark.client.render.blockentity.VariantChestBlockEntityRenderer;
import work.lclpnet.mmoquark.module.VariantChestsModule;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class VariantChestsClientModule implements IClientModule {

    @Override
    public void registerClient() {
        BlockEntityRendererRegistry.register(VariantChestsModule.VARIANT_CHEST_ENTITY, VariantChestBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(VariantChestsModule.VARIANT_TRAPPED_CHEST_ENTITY, VariantChestBlockEntityRenderer::new);
    }

    @Override
    public void registerSprites(SpriteAtlasTexture atlasTexture, ClientSpriteRegistryCallback.Registry registry) {
        if (atlasTexture.getId().toString().equals("minecraft:textures/atlas/chest.png")) {
            Consumer<Block> consumer = b -> VariantChestBlockEntityRenderer.accept(atlasTexture, registry, b);
            VariantChestsModule.VARIANT_CHESTS.forEach(consumer);
            VariantChestsModule.VARIANT_TRAPPED_CHESTS.forEach(consumer);
        }
    }
}
