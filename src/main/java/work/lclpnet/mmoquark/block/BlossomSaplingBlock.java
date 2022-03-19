package work.lclpnet.mmoquark.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.mmocontent.block.ext.IMMOBlock;
import work.lclpnet.mmoquark.module.BlossomTreesModule;

import java.util.OptionalInt;
import java.util.Random;

public class BlossomSaplingBlock extends SaplingBlock implements IMMOBlock {

    public BlossomSaplingBlock(BlossomSaplingGenerator generator) {
        super(generator, Settings.copy(Blocks.OAK_SAPLING));
    }

    @Override
    public @Nullable BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }

    public static class BlossomSaplingGenerator extends SaplingGenerator {

        public final TreeFeatureConfig config;
        public final BlockState leaf;

        public BlossomSaplingGenerator(Block leafBlock) {
            config = new TreeFeatureConfig.Builder(
                    BlockStateProvider.of(BlossomTreesModule.blossomWood.log),
                    new LargeOakTrunkPlacer(3, 11, 0),
                    BlockStateProvider.of(leafBlock.getDefaultState()),
                    new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4), // <- Copy of what Features.FANCY_OAK uses
                    new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
                    .ignoreVines()
                    .build();

            leaf = leafBlock.getDefaultState();
        }

        @Nullable
        @Override
        protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bl) {
            return Feature.TREE.configure(config);
        }
    }
}
