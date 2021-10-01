package work.lclpnet.mmoblocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MMOBlocksClient implements ClientModInitializer {

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
