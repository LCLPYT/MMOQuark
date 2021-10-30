package work.lclpnet.mmoblocks.module;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.block.BlossomLeavesBlock;
import work.lclpnet.mmoblocks.block.LeafCarpetBlock;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;

public class TreesModule implements IModule {

    @Override
    public void register() {
        ImmutableSet.of(Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.BIRCH_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES)
                .forEach(this::addLeafCarpet);

        addBlossomTree("blue", MaterialColor.LIGHT_BLUE);
        addBlossomTree("lavender", MaterialColor.PINK);
        addBlossomTree("orange", MaterialColor.ORANGE_TERRACOTTA);
        addBlossomTree("pink", MaterialColor.PINK);
        addBlossomTree("yellow", MaterialColor.YELLOW);
        addBlossomTree("red", MaterialColor.RED);
    }

    private void addLeafCarpet(Block baseBlock) {
        Identifier key = Registry.BLOCK.getId(baseBlock);
        String fencePath = key.getPath();
        if (fencePath.equals("air") && key.getNamespace().equals("minecraft")) return; // default value, if block does not exist in registry

        new MMOBlockRegistrar(new LeafCarpetBlock(baseBlock))
                .register(fencePath.replaceAll("_leaves", "_leaf_carpet"));
    }

    private void addBlossomTree(String colorName, MaterialColor color) {
        new MMOBlockRegistrar(new BlossomLeavesBlock(color))
                .register(String.format("%s_blossom_leaves", colorName), ItemGroup.DECORATIONS);
//        BlossomTree tree = new BlossomTree(leaves);
//        BlossomSaplingBlock sapling = new BlossomSaplingBlock(colorName, this, tree, leaves);
//        VariantHandler.addFlowerPot(sapling, sapling.getRegistryName().getPath(), Functions.identity());
//        trees.add(tree);
    }
}
