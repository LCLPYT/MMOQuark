package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.Tag;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.util.RegistryUtil;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.BlossomLeavesBlock;
import work.lclpnet.mmoquark.block.HedgeBlock;

import java.util.ArrayList;
import java.util.List;

public class HedgesModule implements IModule {

    public static Tag<Block> hedgesTag;
    public static List<HedgeBlock> hedgeBlocks = new ArrayList<>();
    public static List<BlockItem> hedgeItems = new ArrayList<>();

    @Override
    public void register() {
        addHedge(Blocks.OAK_FENCE, Blocks.OAK_LEAVES);
        addHedge(Blocks.BIRCH_FENCE, Blocks.BIRCH_LEAVES);
        addHedge(Blocks.SPRUCE_FENCE, Blocks.SPRUCE_LEAVES);
        addHedge(Blocks.JUNGLE_FENCE, Blocks.JUNGLE_LEAVES);
        addHedge(Blocks.ACACIA_FENCE, Blocks.ACACIA_LEAVES);
        addHedge(Blocks.DARK_OAK_FENCE, Blocks.DARK_OAK_LEAVES);

        // BlossomTreesModule must be loaded before this module
        BlossomTreesModule.trees.forEach(tree -> addHedge(Blocks.SPRUCE_FENCE, tree.leaf.getBlock()));

        hedgesTag = TagRegistry.block(MMOQuark.identifier("hedges"));
    }

    private void addHedge(Block fence, Block leaves) {
        String path;
        if (leaves instanceof BlossomLeavesBlock) {
            String leavesPath = RegistryUtil.getRegistryPath(leaves);
            if (leavesPath == null) return;

            path = leavesPath.replaceAll("_blossom_leaves", "_blossom_hedge");
        } else {
            String fencePath = RegistryUtil.getRegistryPath(fence);
            if (fencePath == null) return;

            path = fencePath.replaceAll("_fence", "_hedge");
        }

        HedgeBlock hedgeBlock = new HedgeBlock(fence, leaves);
        MMOBlockRegistrar.Result result = new MMOBlockRegistrar(hedgeBlock)
                .register(MMOQuark.identifier(path), ItemGroup.DECORATIONS);

        hedgeBlocks.add(hedgeBlock);
        hedgeItems.add(result.item());
    }
}
