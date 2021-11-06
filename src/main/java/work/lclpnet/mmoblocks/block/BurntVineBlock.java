package work.lclpnet.mmoblocks.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import work.lclpnet.mmoblocks.block.ext.IBlockColorProvider;
import work.lclpnet.mmoblocks.block.ext.MMOVineBlock;

public class BurntVineBlock extends MMOVineBlock implements IBlockColorProvider {

    @Override
    public void registerBlockColor(BlockColors colors) {
        final BlockState grass = Blocks.VINE.getDefaultState();
        colors.registerColorProvider((state, world, pos, tintIndex) -> colors.getColor(grass, world, pos, tintIndex), this);
    }

    @Override
    public void registerItemColor(ItemColors colors) {
        final ItemStack grass = new ItemStack(Items.VINE);
        colors.register((stack, tintIndex) -> colors.getColorMultiplier(grass, tintIndex), this);
    }
}
