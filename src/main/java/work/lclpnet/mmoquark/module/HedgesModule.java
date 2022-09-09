package work.lclpnet.mmoquark.module;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.util.RegistryUtil;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.BlossomLeavesBlock;
import work.lclpnet.mmoquark.block.HedgeBlock;

import java.util.ArrayList;
import java.util.List;

public class HedgesModule implements IModule {

    public static TagKey<Block> hedgesTag;
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
        addHedge(Blocks.OAK_FENCE, Blocks.AZALEA_LEAVES);
        addHedge(Blocks.OAK_FENCE, Blocks.FLOWERING_AZALEA_LEAVES);

        // BlossomTreesModule must be loaded before this module
        BlossomTreesModule.leaves.forEach(leaf -> addHedge(BlossomTreesModule.blossomWood.fence, leaf.getBlock()));

        hedgesTag = TagKey.of(Registry.BLOCK_KEY, MMOQuark.identifier("hedges"));
    }

    private void addHedge(Block fence, Block leaves) {
        final String leavesPath = RegistryUtil.getRegistryPath(leaves);
        if (leavesPath == null) return;

        String path;
        if (leaves instanceof BlossomLeavesBlock) {
            path = leavesPath.replaceAll("_blossom_leaves", "_blossom_hedge");
        } else {
            path = leavesPath.replaceAll("_leaves", "_hedge");
        }

        HedgeBlock hedgeBlock = new HedgeBlock(fence, leaves);
        MMOBlockRegistrar.Result result = new MMOBlockRegistrar(hedgeBlock)
                .register(MMOQuark.identifier(path), ItemGroup.DECORATIONS);

        hedgeBlocks.add(hedgeBlock);
        hedgeItems.add(result.item());
    }
}
