package work.lclpnet.mmoquark.module;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.util.RegistryUtil;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.LeafCarpetBlock;

import java.util.ArrayList;
import java.util.List;

public class LeafCarpetModule implements IModule {

    public static List<LeafCarpetBlock> leafCarpetBlocks = new ArrayList<>();
    public static List<BlockItem> leafCarpetItems = new ArrayList<>();

    @Override
    public void register() {
        ImmutableSet.of(Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES)
                .forEach(this::addLeafCarpet);

        // BlossomTreesModule must be loaded before this module
        BlossomTreesModule.trees.forEach(tree -> addLeafCarpet(tree.leaf.getBlock()));
    }

    private void addLeafCarpet(Block baseBlock) {
        String fencePath = RegistryUtil.getRegistryPath(baseBlock);
        if (fencePath == null) return;

        LeafCarpetBlock leafCarpetBlock = new LeafCarpetBlock(baseBlock);
        MMOBlockRegistrar.Result result = new MMOBlockRegistrar(leafCarpetBlock)
                .register(MMOQuark.identifier(fencePath.replaceAll("_leaves", "_leaf_carpet")),
                        ItemGroup.DECORATIONS);

        leafCarpetBlocks.add(leafCarpetBlock);
        leafCarpetItems.add(result.item());
    }
}
