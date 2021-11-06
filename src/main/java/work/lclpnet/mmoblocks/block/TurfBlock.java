package work.lclpnet.mmoblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import work.lclpnet.mmoblocks.block.ext.*;
import work.lclpnet.mmoblocks.util.Env;

public class TurfBlock extends MMOBlock implements IBlockColorProvider, IBlockOverride {

    public TurfBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void registerBlockColor(BlockColors colors) {
        registerBlockColor(colors, this);
    }

    protected static void registerBlockColor(BlockColors colors, Block b) {
        final BlockState grass = Blocks.GRASS_BLOCK.getDefaultState();
        colors.registerColorProvider((state, world, pos, tintIndex) -> colors.getColor(grass, world, pos, tintIndex), b);
    }

    @Override
    public void registerItemColor(ItemColors colors) {
        registerItemColor(colors, this);
    }

    protected static void registerItemColor(ItemColors colors, ItemConvertible item) {
        final ItemStack grass = new ItemStack(Items.GRASS_BLOCK);
        colors.register((stack, tintIndex) -> colors.getColorMultiplier(grass, tintIndex), item);
    }

    @Override
    public SlabBlock provideSlab(Block baseBlock) {
        return Env.isClient() ? provideSlabClient(baseBlock) : IBlockOverride.super.provideSlab(baseBlock);
    }

    @Environment(EnvType.CLIENT)
    protected SlabBlock provideSlabClient(Block baseBlock) {
        return new ColoredMMOSlabBlock(baseBlock, TurfBlock::registerBlockColor, TurfBlock::registerItemColor);
    }

    @Override
    public StairsBlock provideStairs(Block baseBlock) {
        return Env.isClient() ? provideStairsClient(baseBlock) : IBlockOverride.super.provideStairs(baseBlock);
    }

    @Environment(EnvType.CLIENT)
    protected StairsBlock provideStairsClient(Block baseBlock) {
        return new ColoredMMOStairsBlock(baseBlock, TurfBlock::registerBlockColor, TurfBlock::registerItemColor);
    }

    @Override
    public VerticalSlabBlock provideVerticalSlab(SlabBlock baseBlock) {
        return Env.isClient() ? provideVerticalSlabClient(baseBlock) : IBlockOverride.super.provideVerticalSlab(baseBlock);
    }

    @Environment(EnvType.CLIENT)
    protected VerticalSlabBlock provideVerticalSlabClient(Block baseBlock) {
        return new ColoredVerticalSlabBlock(baseBlock, TurfBlock::registerBlockColor, TurfBlock::registerItemColor);
    }
}
