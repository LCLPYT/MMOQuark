package work.lclpnet.mmoquark.client.module;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmocontent.client.util.ClientCommon;
import work.lclpnet.mmoquark.block.LeafCarpetBlock;
import work.lclpnet.mmoquark.module.LeafCarpetModule;

public class LeafCarpetClientModule implements IClientModule {

    @Override
    public void registerClient() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            BlockColors colors = MinecraftClient.getInstance().getBlockColors();
            LeafCarpetBlock leafCarpetBlock = (LeafCarpetBlock) state.getBlock();

            return colors.getColor(leafCarpetBlock.baseState, world, pos, tintIndex);
        }, LeafCarpetModule.leafCarpetBlocks.toArray(new LeafCarpetBlock[0]));

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            LeafCarpetBlock leafCarpetBlock = (LeafCarpetBlock) ((BlockItem) stack.getItem()).getBlock();
            ItemColors colors = ClientCommon.getItemColors();

            return colors.getColor(leafCarpetBlock.getBaseStack(), tintIndex);
        }, LeafCarpetModule.leafCarpetItems.toArray(new BlockItem[0]));

        LeafCarpetModule.leafCarpetBlocks
                .forEach(leafCarpetBlock -> MMORenderLayers.setBlockRenderType(leafCarpetBlock, RenderLayer.getCutout()));
    }
}
