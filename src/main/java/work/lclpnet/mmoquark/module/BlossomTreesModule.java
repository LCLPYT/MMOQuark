package work.lclpnet.mmoquark.module;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.MapColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.entity.MMOBoatEntity;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.BlossomLeavesBlock;
import work.lclpnet.mmoquark.block.BlossomSaplingBlock;
import work.lclpnet.mmoquark.util.FeatureUtils;
import work.lclpnet.mmoquark.util.WoodGroupUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class BlossomTreesModule implements IModule {

    public static final List<Block> blossomLeaveBlocks = new ArrayList<>(),
            blossomSaplingBlocks = new ArrayList<>(),
            flowerPotBlocks = new ArrayList<>();
    public static final List<BlockState> leaves = new ArrayList<>();
    public static WoodGroupUtil.WoodGroupHolder blossomWood;

    @Override
    public void register() {
        MMOBoatEntity.enableMMOBoatIntegration();
        blossomWood = WoodGroupUtil.registerWoodGroup("blossom", MapColor.RED, MapColor.BROWN);

        addBlossomTree("blue", MapColor.LIGHT_BLUE);
        addBlossomTree("lavender", MapColor.PINK);
        addBlossomTree("orange", MapColor.TERRACOTTA_ORANGE);
        addBlossomTree("pink", MapColor.PINK);
        addBlossomTree("yellow", MapColor.YELLOW);
        addBlossomTree("red", MapColor.RED);
    }

    private void addBlossomTree(String colorName, MapColor color) {
        BlossomLeavesBlock leaves = new BlossomLeavesBlock(color);
        new MMOBlockRegistrar(leaves)
                .register(MMOQuark.identifier("%s_blossom_leaves", colorName), ItemGroup.DECORATIONS);

        blossomLeaveBlocks.add(leaves);

        final BlockState leaf = leaves.getDefaultState();

        final var config = new TreeFeatureConfig.Builder(
                BlockStateProvider.of(BlossomTreesModule.blossomWood.log),
                new LargeOakTrunkPlacer(3, 11, 0),
                BlockStateProvider.of(leaf),
                // Copy of what TreeConfiguredFeatures.FANCY_OAK uses
                new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                .ignoreVines()
                .build();

        final var registeredFeature = FeatureUtils.register(
                MMOQuark.identifier("%s_blossom_tree", colorName),
                FeatureUtils.configure(Feature.TREE, config)
        );

        final var tree = new BlossomSaplingBlock.BlossomSaplingGenerator(registeredFeature);

        BlossomSaplingBlock sapling = new BlossomSaplingBlock(tree);
        String saplingId = String.format("%s_blossom_sapling", colorName);
        new MMOBlockRegistrar(sapling)
                .register(MMOQuark.identifier(saplingId), ItemGroup.DECORATIONS);

        blossomSaplingBlocks.add(sapling);

        FlowerPotBlock flowerPotBlock = MorePottedPlantsModule.addPottedPlant(sapling, saplingId);
        flowerPotBlocks.add(flowerPotBlock);

        BlossomTreesModule.leaves.add(leaf);
    }
}
