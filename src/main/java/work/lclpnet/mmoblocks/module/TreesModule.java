package work.lclpnet.mmoblocks.module;

import net.minecraft.block.MaterialColor;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmoblocks.block.BlossomLeavesBlock;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;

public class TreesModule implements IModule {

    @Override
    public void register() {
        addBlossomTree("blue", MaterialColor.LIGHT_BLUE);
        addBlossomTree("lavender", MaterialColor.PINK);
        addBlossomTree("orange", MaterialColor.ORANGE_TERRACOTTA);
        addBlossomTree("pink", MaterialColor.PINK);
        addBlossomTree("yellow", MaterialColor.YELLOW);
        addBlossomTree("red", MaterialColor.RED);
    }

    private void addBlossomTree(String colorName, MaterialColor color) {
        new MMOBlockRegistrar(new BlossomLeavesBlock(color)).register(String.format("%s_blossom_leaves", colorName), ItemGroup.DECORATIONS);
//        BlossomTree tree = new BlossomTree(leaves);
//        BlossomSaplingBlock sapling = new BlossomSaplingBlock(colorName, this, tree, leaves);
//        VariantHandler.addFlowerPot(sapling, sapling.getRegistryName().getPath(), Functions.identity());
//        trees.add(tree);
    }
}
