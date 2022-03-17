package work.lclpnet.mmoquark.client.module;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import work.lclpnet.mmocontent.client.util.ClientCommon;
import work.lclpnet.mmoquark.module.TurfModule;

public class TurfClientModule implements IClientModule {

    @Override
    public void registerClient() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            BlockColors colors = MinecraftClient.getInstance().getBlockColors();
            BlockState grass = Blocks.GRASS_BLOCK.getDefaultState();

            return colors.getColor(grass, world, pos, tintIndex);
        }, TurfModule.turfBlocks.toArray(new Block[0]));

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            ItemColors colors = ClientCommon.getItemColors();
            ItemStack grass = new ItemStack(Blocks.GRASS_BLOCK);

            return colors.getColor(grass, tintIndex);
        }, TurfModule.turfItems.toArray(new BlockItem[0]));
    }
}
