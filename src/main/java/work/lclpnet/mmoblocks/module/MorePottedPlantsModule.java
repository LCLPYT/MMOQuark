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
        add(Blocks.BEETROOTS, "beetroot");
        add(Blocks.SWEET_BERRY_BUSH, "berries");
        add(Blocks.CARROTS, "carrot");
        add(Blocks.CHORUS_FLOWER, "chorus");
        add(Blocks.COCOA, "cocoa_bean");
        Block grass = add(Blocks.GRASS, "grass");
        add(Blocks.PEONY, "peony");
        Block largeFern = add(Blocks.LARGE_FERN, "large_fern");
        add(Blocks.LILAC, "lilac");
        add(Blocks.MELON_STEM, "melon");
        add(Blocks.NETHER_SPROUTS, "nether_sprouts");
        add(Blocks.NETHER_WART, "nether_wart");
        add(Blocks.POTATOES, "potato");
        add(Blocks.PUMPKIN_STEM, "pumpkin");
        add(Blocks.ROSE_BUSH, "rose");
        add(Blocks.SEA_PICKLE, "sea_pickle", p -> p.luminance(b -> 3));
        Block sugarCane = add(Blocks.SUGAR_CANE, "sugar_cane");
        add(Blocks.SUNFLOWER, "sunflower");
        Block tallGrass = add(Blocks.TALL_GRASS, "tall_grass");
        add(Blocks.TWISTING_VINES, "twisting_vines");
        Block vine = add(Blocks.VINE, "vine");
        add(Blocks.WEEPING_VINES, "weeping_vines");
        add(Blocks.WHEAT, "wheat");

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
                colors.registerColorProvider((state, world, pos, tintIndex) -> colors.getColor(parent.getDefaultState(), world, pos, tintIndex));
            }

            @Override
            public void registerItemColor(ItemColors colors) {}
        }));
    }

    private FlowerPotBlock add(Block block, String name) {
        return add(block, name, Function.identity());
    }

    private FlowerPotBlock add(Block block, String name, Function<AbstractBlock.Settings, AbstractBlock.Settings> transformer) {
        AbstractBlock.Settings settings = transformer.apply(AbstractBlock.Settings.of(Material.SUPPORTED)
                .breakInstantly()
                .nonOpaque());

        MMOFlowerPotBlock potted = new MMOFlowerPotBlock(block, settings);

//        Identifier key = Registry.BLOCK.getId(block);
//        String path = key.getPath();
//        if (path.equals("air") && key.getNamespace().equals("minecraft")) throw new IllegalStateException("Could not find registry item"); // default value, if block does not exist in registry

        new MMOBlockRegistrar(potted)
                .register(String.format("potted_%s", name));

        return potted;
    }
}
