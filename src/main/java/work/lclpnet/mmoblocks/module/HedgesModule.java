package work.lclpnet.mmoblocks.module;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.Tag;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.block.BlossomLeavesBlock;
import work.lclpnet.mmoblocks.block.HedgeBlock;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.util.MiscUtil;

public class HedgesModule implements IModule {

    public static Tag<Block> hedgesTag;

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

        hedgesTag = TagRegistry.block(MMOBlocks.identifier("hedges"));
    }

    private void addHedge(Block fence, Block leaves) {
        String path;
        if (leaves instanceof BlossomLeavesBlock) {
            String leavesPath = MiscUtil.getRegistryPath(leaves);
            if (leavesPath == null) return;

            path = leavesPath.replaceAll("_blossom_leaves", "_blossom_hedge");
        } else {
            String fencePath = MiscUtil.getRegistryPath(fence);
            if (fencePath == null) return;

            path = fencePath.replaceAll("_fence", "_hedge");
        }

        new MMOBlockRegistrar(new HedgeBlock(fence, leaves)).register(path, ItemGroup.DECORATIONS);
    }
}
