package work.lclpnet.mmoquark.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import work.lclpnet.mmocontent.block.MMOPottedPlantUtil;
import work.lclpnet.mmoquark.MMOQuark;

import java.util.function.Function;

public class MorePottedPlantsModule implements IModule {

    public static Block grass, largeFern, sugarCane, tallGrass, vine;

    @Override
    public void register() {
        addPottedPlant(Blocks.BEETROOTS, "beetroot");
        addPottedPlant(Blocks.SWEET_BERRY_BUSH, "berries");
        addPottedPlant(Blocks.CARROTS, "carrot");
        addPottedPlant(Blocks.CHORUS_FLOWER, "chorus");
        addPottedPlant(Blocks.COCOA, "cocoa_bean");
        grass = addPottedPlant(Blocks.GRASS, "grass");
        addPottedPlant(Blocks.PEONY, "peony");
        largeFern = addPottedPlant(Blocks.LARGE_FERN, "large_fern");
        addPottedPlant(Blocks.LILAC, "lilac");
        addPottedPlant(Blocks.MELON_STEM, "melon");
        addPottedPlant(Blocks.NETHER_SPROUTS, "nether_sprouts");
        addPottedPlant(Blocks.NETHER_WART, "nether_wart");
        addPottedPlant(Blocks.POTATOES, "potato");
        addPottedPlant(Blocks.PUMPKIN_STEM, "pumpkin");
        addPottedPlant(Blocks.ROSE_BUSH, "rose");
        addPottedPlant(Blocks.SEA_PICKLE, "sea_pickle", p -> p.luminance(b -> 3));
        sugarCane = addPottedPlant(Blocks.SUGAR_CANE, "sugar_cane");
        addPottedPlant(Blocks.SUNFLOWER, "sunflower");
        tallGrass = addPottedPlant(Blocks.TALL_GRASS, "tall_grass");
        addPottedPlant(Blocks.TWISTING_VINES, "twisting_vines");
        vine = addPottedPlant(Blocks.VINE, "vine");
        addPottedPlant(Blocks.WEEPING_VINES, "weeping_vines");
        addPottedPlant(Blocks.WHEAT, "wheat");
    }

    /* Comfort methods */

    static FlowerPotBlock addPottedPlant(Block block, String name) {
        return MMOPottedPlantUtil.addPottedPlant(block, name, Function.identity(), MMOQuark::identifier);
    }

    static FlowerPotBlock addPottedPlant(Block block, String name, Function<AbstractBlock.Settings, AbstractBlock.Settings> transformer) {
        return MMOPottedPlantUtil.addPottedPlant(block, name, transformer, MMOQuark::identifier);
    }
}
