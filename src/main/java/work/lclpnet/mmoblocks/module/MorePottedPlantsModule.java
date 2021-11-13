package work.lclpnet.mmoblocks.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.ext.IBlockColorProvider;
import work.lclpnet.mmoblocks.block.ext.MMOFlowerPotBlock;
import work.lclpnet.mmoblocks.util.Env;
import work.lclpnet.mmoblocks.util.MMOBlockColors;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MorePottedPlantsModule implements IModule {

    @Override
    public void register() {
        addPottedPlant(Blocks.BEETROOTS, "beetroot");
        addPottedPlant(Blocks.SWEET_BERRY_BUSH, "berries");
        addPottedPlant(Blocks.CARROTS, "carrot");
        addPottedPlant(Blocks.CHORUS_FLOWER, "chorus");
        addPottedPlant(Blocks.COCOA, "cocoa_bean");
        Block grass = addPottedPlant(Blocks.GRASS, "grass");
        addPottedPlant(Blocks.PEONY, "peony");
        Block largeFern = addPottedPlant(Blocks.LARGE_FERN, "large_fern");
        addPottedPlant(Blocks.LILAC, "lilac");
        addPottedPlant(Blocks.MELON_STEM, "melon");
        addPottedPlant(Blocks.NETHER_SPROUTS, "nether_sprouts");
        addPottedPlant(Blocks.NETHER_WART, "nether_wart");
        addPottedPlant(Blocks.POTATOES, "potato");
        addPottedPlant(Blocks.PUMPKIN_STEM, "pumpkin");
        addPottedPlant(Blocks.ROSE_BUSH, "rose");
        addPottedPlant(Blocks.SEA_PICKLE, "sea_pickle", p -> p.luminance(b -> 3));
        Block sugarCane = addPottedPlant(Blocks.SUGAR_CANE, "sugar_cane");
        addPottedPlant(Blocks.SUNFLOWER, "sunflower");
        Block tallGrass = addPottedPlant(Blocks.TALL_GRASS, "tall_grass");
        addPottedPlant(Blocks.TWISTING_VINES, "twisting_vines");
        Block vine = addPottedPlant(Blocks.VINE, "vine");
        addPottedPlant(Blocks.WEEPING_VINES, "weeping_vines");
        addPottedPlant(Blocks.WHEAT, "wheat");

        if (Env.isClient()) {
            final Map<Block, Block> tintedBlocks = new HashMap<>();
            tintedBlocks.put(grass, Blocks.GRASS);
            tintedBlocks.put(largeFern, Blocks.LARGE_FERN);
            tintedBlocks.put(sugarCane, Blocks.SUGAR_CANE);
            tintedBlocks.put(tallGrass, Blocks.TALL_GRASS);
            tintedBlocks.put(vine, Blocks.VINE);

            registerTintedBlocks(tintedBlocks);
        }
    }

    @Environment(EnvType.CLIENT)
    private void registerTintedBlocks(Map<Block, Block> tintedBlocks) {
        tintedBlocks.forEach((potted, parent) -> MMOBlockColors.registerBlockColorProvider(new IBlockColorProvider() {
            @Override
            public void registerBlockColor(BlockColors colors) {
                colors.registerColorProvider((state, world, pos, tintIndex) -> colors.getColor(parent.getDefaultState(), world, pos, tintIndex), potted);
            }

            @Override
            public void registerItemColor(ItemColors colors) {}
        }));
    }

    static FlowerPotBlock addPottedPlant(Block block, String name) {
        return addPottedPlant(block, name, Function.identity());
    }

    static FlowerPotBlock addPottedPlant(Block block, String name, Function<AbstractBlock.Settings, AbstractBlock.Settings> transformer) {
        AbstractBlock.Settings settings = transformer.apply(AbstractBlock.Settings.of(Material.SUPPORTED)
                .breakInstantly()
                .nonOpaque());

        MMOFlowerPotBlock potted = new MMOFlowerPotBlock(block, settings);

        new MMOBlockRegistrar(potted)
                .register(String.format("potted_%s", name));

        return potted;
    }
}
