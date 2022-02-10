package work.lclpnet.mmoquark.module;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.BlossomLeavesBlock;
import work.lclpnet.mmoquark.block.BlossomSaplingBlock;

import java.util.ArrayList;
import java.util.List;

public class BlossomTreesModule implements IModule {

    public static final List<Block> blossomLeaveBlocks = new ArrayList<>(),
            blossomSaplingBlocks = new ArrayList<>(),
            flowerPotBlocks = new ArrayList<>();
    public static final List<BlossomSaplingBlock.BlossomSaplingGenerator> trees = new ArrayList<>();

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
        BlossomLeavesBlock leaves = new BlossomLeavesBlock(color);
        new MMOBlockRegistrar(leaves)
                .register(MMOQuark.identifier("%s_blossom_leaves", colorName), ItemGroup.DECORATIONS);

        blossomLeaveBlocks.add(leaves);

        BlossomSaplingBlock.BlossomSaplingGenerator tree = new BlossomSaplingBlock.BlossomSaplingGenerator(leaves);

        BlossomSaplingBlock sapling = new BlossomSaplingBlock(tree);
        String saplingId = String.format("%s_blossom_sapling", colorName);
        new MMOBlockRegistrar(sapling)
                .register(MMOQuark.identifier(saplingId), ItemGroup.DECORATIONS);

        blossomSaplingBlocks.add(sapling);

        FlowerPotBlock flowerPotBlock = MorePottedPlantsModule.addPottedPlant(sapling, saplingId);
        flowerPotBlocks.add(flowerPotBlock);

        trees.add(tree);
    }
}
