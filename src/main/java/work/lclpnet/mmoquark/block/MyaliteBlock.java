package work.lclpnet.mmoquark.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import work.lclpnet.mmocontent.block.ext.*;
import work.lclpnet.mmocontent.util.Env;

public class MyaliteBlock extends MMOBlock implements IMyaliteColorProvider, IBlockOverride {


    public MyaliteBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Block getMyaliteBlock() {
        return this;
    }

    @Override
    public SlabBlock provideSlab(Block baseBlock) {
        return Env.isClient() ? provideSlabClient(baseBlock) : IBlockOverride.super.provideSlab(baseBlock);
    }

    @Environment(EnvType.CLIENT)
    protected SlabBlock provideSlabClient(Block baseBlock) {
        return new ColoredMMOSlabBlock(baseBlock, IMyaliteColorProvider::registerBlockColor, IMyaliteColorProvider::registerItemColor);
    }

    @Override
    public StairsBlock provideStairs(Block baseBlock) {
        return Env.isClient() ? provideStairsClient(baseBlock) : IBlockOverride.super.provideStairs(baseBlock);
    }

    @Environment(EnvType.CLIENT)
    protected StairsBlock provideStairsClient(Block baseBlock) {
        return new ColoredMMOStairsBlock(baseBlock, IMyaliteColorProvider::registerBlockColor, IMyaliteColorProvider::registerItemColor);
    }

    @Override
    public WallBlock provideWall(Block baseBlock) {
        return Env.isClient() ? provideWallClient(baseBlock) : IBlockOverride.super.provideWall(baseBlock);
    }

    @Environment(EnvType.CLIENT)
    protected WallBlock provideWallClient(Block baseBlock) {
        return new ColoredMMOWallBlock(baseBlock, IMyaliteColorProvider::registerBlockColor, IMyaliteColorProvider::registerItemColor);
    }

    @Override
    public MMOVerticalSlabBlock provideVerticalSlab(SlabBlock baseBlock) {
        return Env.isClient() ? provideVerticalSlabClient(baseBlock) : IBlockOverride.super.provideVerticalSlab(baseBlock);
    }

    @Environment(EnvType.CLIENT)
    protected MMOVerticalSlabBlock provideVerticalSlabClient(Block baseBlock) {
        return new ColoredVerticalSlabBlock(baseBlock, IMyaliteColorProvider::registerBlockColor, IMyaliteColorProvider::registerItemColor);
    }
}
