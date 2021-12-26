package work.lclpnet.mmoquark.client.module;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmocontent.client.util.ClientCommon;
import work.lclpnet.mmoquark.module.BurntVinesModule;

public class BurntVinesClientModule implements IClientModule {

    @Override
    public void registerClient() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            BlockState vine = Blocks.VINE.getDefaultState();
            BlockColors colors = MinecraftClient.getInstance().getBlockColors();
            return colors.getColor(vine, world, pos, tintIndex);
        }, BurntVinesModule.burnt_vine);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            ItemStack vine = new ItemStack(Items.VINE);
            ItemColors colors = ClientCommon.getItemColors();
            return colors.getColorMultiplier(vine, tintIndex);
        }, BurntVinesModule.burnt_vine_item);

        MMORenderLayers.setBlockRenderType(BurntVinesModule.burnt_vine, RenderLayer.getCutout());
    }
}
