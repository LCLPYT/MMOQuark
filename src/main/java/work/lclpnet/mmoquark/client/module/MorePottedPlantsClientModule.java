package work.lclpnet.mmoquark.client.module;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmoquark.module.MorePottedPlantsModule;

public class MorePottedPlantsClientModule implements IClientModule {

    @Override
    public void registerClient() {
        ImmutableMap.of(
                MorePottedPlantsModule.grass, Blocks.GRASS,
                MorePottedPlantsModule.largeFern, Blocks.LARGE_FERN,
                MorePottedPlantsModule.sugarCane, Blocks.SUGAR_CANE,
                MorePottedPlantsModule.tallGrass, Blocks.TALL_GRASS,
                MorePottedPlantsModule.vine, Blocks.VINE
        ).forEach(MorePottedPlantsClientModule::registerTintedPottedPlant);

        MorePottedPlantsModule.pottedPlants
                .forEach(block -> MMORenderLayers.setBlockRenderType(block, RenderLayer.getCutout()));
    }

    public static void registerTintedPottedPlant(Block potted, Block parent) {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            BlockColors colors = MinecraftClient.getInstance().getBlockColors();
            return colors.getColor(parent.getDefaultState(), world, pos, tintIndex);
        }, potted);
    }
}
