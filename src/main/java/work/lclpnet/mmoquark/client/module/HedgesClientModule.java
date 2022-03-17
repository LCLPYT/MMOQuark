package work.lclpnet.mmoquark.client.module;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmocontent.client.util.ClientCommon;
import work.lclpnet.mmoquark.block.HedgeBlock;
import work.lclpnet.mmoquark.module.HedgesModule;

public class HedgesClientModule implements IClientModule {

    @Override
    public void registerClient() {
        HedgesModule.hedgeBlocks.forEach(hedgeBlock -> MMORenderLayers.setBlockRenderType(hedgeBlock, RenderLayer.getCutout()));

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            HedgeBlock hedgeBlock = (HedgeBlock) state.getBlock();
            BlockState leafState = hedgeBlock.leaf.getDefaultState();
            BlockColors colors = MinecraftClient.getInstance().getBlockColors();

            return colors.getColor(leafState, world, pos, tintIndex);
        }, HedgesModule.hedgeBlocks.toArray(new HedgeBlock[0]));

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            ItemColors colors = ClientCommon.getItemColors();
            HedgeBlock hedgeBlock = (HedgeBlock) ((BlockItem) stack.getItem()).getBlock();
            ItemStack leafStack = new ItemStack(hedgeBlock.leaf);

            return colors.getColor(leafStack, tintIndex);
        }, HedgesModule.hedgeItems.toArray(new BlockItem[0]));
    }
}
