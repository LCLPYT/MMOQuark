package work.lclpnet.mmoquark.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.Block;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.math.BlockPos;
import work.lclpnet.mmoquark.blockentity.VariantChestBlockEntity;
import work.lclpnet.mmoquark.blockentity.VariantTrappedChestBlockEntity;
import work.lclpnet.mmoquark.client.render.blockentity.VariantChestBlockEntityRenderer;
import work.lclpnet.mmoquark.module.VariantChestsModule;

import java.util.function.Consumer;

import static work.lclpnet.mmoquark.module.VariantChestsModule.VARIANT_CHESTS;
import static work.lclpnet.mmoquark.module.VariantChestsModule.VARIANT_TRAPPED_CHESTS;

@Environment(EnvType.CLIENT)
public class VariantChestsClientModule implements IClientModule {

    @Override
    public void registerClient() {
        VARIANT_CHESTS.forEach(chest -> chest.displayBlockEntity = new VariantChestBlockEntity(BlockPos.ORIGIN, chest.getDefaultState()));
        VARIANT_TRAPPED_CHESTS.forEach(trappedChest -> trappedChest.displayBlockEntity = new VariantTrappedChestBlockEntity(BlockPos.ORIGIN, trappedChest.getDefaultState()));

        BlockEntityRendererRegistry.register(VariantChestsModule.VARIANT_CHEST_ENTITY, VariantChestBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(VariantChestsModule.VARIANT_TRAPPED_CHEST_ENTITY, VariantChestBlockEntityRenderer::new);
    }

    @Override
    public void registerSprites(SpriteAtlasTexture atlasTexture, ClientSpriteRegistryCallback.Registry registry) {
        if (atlasTexture.getId().toString().equals("minecraft:textures/atlas/chest.png")) {
            Consumer<Block> consumer = b -> VariantChestBlockEntityRenderer.accept(atlasTexture, registry, b);
            VARIANT_CHESTS.forEach(consumer);
            VARIANT_TRAPPED_CHESTS.forEach(consumer);
        }
    }
}
