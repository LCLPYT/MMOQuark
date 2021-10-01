package work.lclpnet.mmoblocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class MMOBlocksClient implements ClientModInitializer {

    @Environment(EnvType.CLIENT)
    private static Map<Block, RenderLayer> blockRenders = new HashMap<>();

    @Override
    public void onInitializeClient() {
        blockRenders.forEach((block, renderLayer) -> {
            System.out.println(block + " client");
            BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
        });
    }

    @Environment(EnvType.CLIENT)
    public static void setBlockRenderType(Block b, RenderLayer renderType) {
        blockRenders.put(b, renderType);
    }
}
