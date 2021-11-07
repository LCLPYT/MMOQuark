package work.lclpnet.mmoblocks.module;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.block.BlossomLeavesBlock;
import work.lclpnet.mmoblocks.block.HedgeBlock;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;

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

        hedgesTag = TagRegistry.block(MMOBlocks.identifier("hedges"));
    }

    private void addHedge(Block fence, Block leaves) {
        String path;
        if (leaves instanceof BlossomLeavesBlock) {
            Identifier key = Registry.BLOCK.getId(leaves);
            String leavesPath = key.getPath();
            if (leavesPath.equals("air") && key.getNamespace().equals("minecraft")) return; // default value, if block does not exist in registry

            path = leavesPath.replaceAll("_blossom_leaves", "_blossom_hedge");
        } else {
            Identifier key = Registry.BLOCK.getId(fence);
            String fencePath = key.getPath();
            if (fencePath.equals("air") && key.getNamespace().equals("minecraft")) return; // default value, if block does not exist in registry

            path = fencePath.replaceAll("_fence", "_hedge");
        }

        new MMOBlockRegistrar(new HedgeBlock(fence, leaves)).register(path, ItemGroup.DECORATIONS);
    }
}
