package work.lclpnet.mmoblocks;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import work.lclpnet.mmoblocks.blockentity.MMOItemBlockEntities;
import work.lclpnet.mmoblocks.module.IClientModule;
import work.lclpnet.mmoblocks.module.WoodExtraModule;

import java.util.*;

public class MMOBlocksClient implements ClientModInitializer {

    public static final Set<IClientModule> CLIENT_MODULES = ImmutableSet.of(
            new WoodExtraModule()
    );

    @Environment(EnvType.CLIENT)
    private static Map<Block, RenderLayer> blockRenders = new HashMap<>();
    private static Map<Block, List<Block>> inheritances = new HashMap<>();

    @Override
    public void onInitializeClient() {
        blockRenders.forEach((block, renderLayer) -> {
            BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);

            if (!inheritances.containsKey(block)) return;
            List<Block> inherit = inheritances.get(block);
            if (inherit != null) inherit.forEach(b -> BlockRenderLayerMap.INSTANCE.putBlock(b, renderLayer));
        });

        blockRenders = null;
        inheritances = null;

        CLIENT_MODULES.forEach(IClientModule::registerClient);

        // init dummy block entity item entities
        MMOItemBlockEntities.init();

        ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE)
                .register((atlasTexture, registry) -> CLIENT_MODULES.forEach(module -> module.registerSprites(atlasTexture, registry)));
    }

    @Environment(EnvType.CLIENT)
    public static void setBlockRenderType(Block b, RenderLayer renderType) {
        blockRenders.put(b, renderType);
    }

    @Environment(EnvType.CLIENT)
    public static void inheritRenderLayer(Block b, Block parent) {
        inheritances.computeIfAbsent(parent, k -> new ArrayList<>()).add(b);
    }
}
