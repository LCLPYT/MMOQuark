package work.lclpnet.mmoquark.module;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.apache.commons.lang3.tuple.Pair;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.block.ext.MMOVerticalSlabBlock;
import work.lclpnet.mmocontent.util.RegistryUtil;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.WeatheringCopperVerticalSlabBlock;
import work.lclpnet.mmoquark.util.QWaxedBlocks;

import java.util.ArrayList;
import java.util.List;

public class VanillaVerticalSlabsModule implements IModule {

    @Override
    public void register() {
        registerVanillaVerticalSlabs();
    }

    private void registerVanillaVerticalSlabs() {
        ImmutableSet.of(
                Blocks.ACACIA_SLAB, Blocks.ANDESITE_SLAB, Blocks.BIRCH_SLAB, Blocks.BRICK_SLAB, Blocks.COBBLESTONE_SLAB,
                Blocks.CUT_RED_SANDSTONE_SLAB, Blocks.CUT_SANDSTONE_SLAB, Blocks.DARK_OAK_SLAB, Blocks.DARK_PRISMARINE_SLAB, Blocks.DIORITE_SLAB,
                Blocks.END_STONE_BRICK_SLAB, Blocks.GRANITE_SLAB, Blocks.JUNGLE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB,
                Blocks.NETHER_BRICK_SLAB, Blocks.OAK_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.POLISHED_GRANITE_SLAB,
                Blocks.PRISMARINE_SLAB, Blocks.PRISMARINE_BRICK_SLAB, Blocks.PURPUR_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_NETHER_BRICK_SLAB,
                Blocks.RED_SANDSTONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB,
                Blocks.SMOOTH_STONE_SLAB, Blocks.SPRUCE_SLAB, Blocks.STONE_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.BLACKSTONE_SLAB, Blocks.POLISHED_BLACKSTONE_SLAB,
                Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, Blocks.CRIMSON_SLAB, Blocks.WARPED_SLAB, Blocks.COBBLED_DEEPSLATE_SLAB, Blocks.POLISHED_DEEPSLATE_SLAB,
                Blocks.DEEPSLATE_BRICK_SLAB, Blocks.DEEPSLATE_TILE_SLAB
        ).forEach(b -> {
            String path = RegistryUtil.getRegistryPath(b);
            if (path == null) return;

            new MMOBlockRegistrar(new MMOVerticalSlabBlock(b))
                    .register(MMOQuark.identifier(path.replace("_slab", "_vertical_slab")));
        });

        List<WeatheringCopperVerticalSlabBlock> copperVerticalSlabs = new ArrayList<>();
        ImmutableSet.of(
                Pair.of(Blocks.CUT_COPPER_SLAB, Blocks.WAXED_CUT_COPPER_SLAB),
                Pair.of(Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB),
                Pair.of(Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB),
                Pair.of(Blocks.OXIDIZED_CUT_COPPER_SLAB, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB)
        ).forEach(p -> {
            final Block clean = p.getLeft();
            WeatheringCopperVerticalSlabBlock cleanSlab = new WeatheringCopperVerticalSlabBlock(clean);
            String cleanPath = RegistryUtil.getRegistryPath(clean);
            if (cleanPath == null) return;

            new MMOBlockRegistrar(cleanSlab)
                    .register(MMOQuark.identifier(cleanPath.replace("_slab", "_vertical_slab")));

            final Block waxed = p.getRight();
            MMOVerticalSlabBlock waxedSlab = new MMOVerticalSlabBlock(waxed);
            String waxedPath = RegistryUtil.getRegistryPath(waxed);
            if (waxedPath == null) return;

            new MMOBlockRegistrar(waxedSlab)
                    .register(MMOQuark.identifier(waxedPath.replace("_slab", "_vertical_slab")));

            copperVerticalSlabs.add(cleanSlab);
            QWaxedBlocks.register(cleanSlab, waxedSlab);
        });

        for (int i = 1; i < copperVerticalSlabs.size(); i++) {
            WeatheringCopperVerticalSlabBlock prev = copperVerticalSlabs.get(i - 1);
            WeatheringCopperVerticalSlabBlock next = copperVerticalSlabs.get(i);
            QWaxedBlocks.registerAxeScrape(next, prev);

            prev.next = next;
        }
    }
}
