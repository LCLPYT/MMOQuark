package work.lclpnet.mmoquark.block;

import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.mmocontent.block.ext.IMMOBlock;

import net.minecraft.util.math.random.Random;

public class BlossomSaplingBlock extends SaplingBlock implements IMMOBlock {

    public BlossomSaplingBlock(BlossomSaplingGenerator generator) {
        super(generator, Settings.copy(Blocks.OAK_SAPLING));
    }

    @Override
    public @Nullable BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }

    public static class BlossomSaplingGenerator extends SaplingGenerator {

        public final RegistryEntry<? extends ConfiguredFeature<?, ?>> feature;

        public BlossomSaplingGenerator(RegistryEntry<? extends ConfiguredFeature<?, ?>> feature) {
//            config = new TreeFeatureConfig.Builder(
//                    BlockStateProvider.of(BlossomTreesModule.blossomWood.log),
//                    new LargeOakTrunkPlacer(3, 11, 0),
//                    BlockStateProvider.of(leafBlock.getDefaultState()),
//                    new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4), // <- Copy of what Features.FANCY_OAK uses
//                    new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
//                    .ignoreVines()
//                    .build();
            this.feature = feature;
        }

        @Nullable
        @Override
        protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
            return this.feature;
        }
    }
}
