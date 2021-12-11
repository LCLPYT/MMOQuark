package work.lclpnet.mmoquark.module;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import work.lclpnet.mmoquark.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.block.VerticalSlabBlock;
import work.lclpnet.mmoquark.util.MiscUtil;

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
                Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, Blocks.CRIMSON_SLAB, Blocks.WARPED_SLAB
        ).forEach(b -> {
            String path = MiscUtil.getRegistryPath(b);
            if (path == null) return;

            new MMOBlockRegistrar(new VerticalSlabBlock(b))
                    .register(path.replace("_slab", "_vertical_slab"));
        });
    }
}
