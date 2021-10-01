package work.lclpnet.mmoblocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

import java.util.HashMap;
import java.util.Map;

public class MMOBlocksClient implements ClientModInitializer {

    @Environment(EnvType.CLIENT)
    private static final Map<Block, RenderLayer> blockRenders = new HashMap<>();

    @Override
    public void onInitializeClient() {
        blockRenders.forEach(BlockRenderLayerMap.INSTANCE::putBlock);
    }

    @Environment(EnvType.CLIENT)
    public static void setBlockRenderType(Block b, RenderLayer renderType) {
        blockRenders.put(b, renderType);
    }
}
