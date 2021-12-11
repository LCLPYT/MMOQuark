package work.lclpnet.mmoquark.module;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import work.lclpnet.mmoquark.block.LeafCarpetBlock;
import work.lclpnet.mmoquark.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.util.MiscUtil;

public class LeafCarpetModule implements IModule {

    @Override
    public void register() {
        ImmutableSet.of(Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES)
                .forEach(this::addLeafCarpet);

        // BlossomTreesModule must be loaded before this module
        BlossomTreesModule.trees.forEach(tree -> addLeafCarpet(tree.leaf.getBlock()));
    }

    private void addLeafCarpet(Block baseBlock) {
        String fencePath = MiscUtil.getRegistryPath(baseBlock);
        if (fencePath == null) return;

        new MMOBlockRegistrar(new LeafCarpetBlock(baseBlock))
                .register(fencePath.replaceAll("_leaves", "_leaf_carpet"));
    }
}
