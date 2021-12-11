package work.lclpnet.mmoquark.client.module;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import work.lclpnet.mmocontent.block.ext.IBlockColorProvider;
import work.lclpnet.mmocontent.client.render.block.MMOBlockColors;
import work.lclpnet.mmoquark.module.IClientModule;
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
    }

    public static void registerTintedPottedPlant(Block potted, Block parent) {
        MMOBlockColors.registerBlockColorProvider(new IBlockColorProvider() {
            @Override
            public void registerBlockColor(BlockColors colors) {
                colors.registerColorProvider((state, world, pos, tintIndex) -> colors.getColor(parent.getDefaultState(), world, pos, tintIndex), potted);
            }

            @Override
            public void registerItemColor(ItemColors colors) {
            }
        });
    }
}
