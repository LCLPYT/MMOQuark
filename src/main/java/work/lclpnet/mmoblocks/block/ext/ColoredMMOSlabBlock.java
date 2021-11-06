package work.lclpnet.mmoblocks.block.ext;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.ItemConvertible;

import java.util.function.BiConsumer;

@Environment(EnvType.CLIENT)
public class ColoredMMOSlabBlock extends MMOSlabBlock implements IBlockColorProvider {

    private final BiConsumer<BlockColors, Block> blockColorRegistrar;
    private final BiConsumer<ItemColors, ItemConvertible> itemColorRegistrar;

    public ColoredMMOSlabBlock(Block parent, BiConsumer<BlockColors, Block> blockColorRegistrar, BiConsumer<ItemColors, ItemConvertible> itemColorRegistrar) {
        super(parent);
        this.blockColorRegistrar = blockColorRegistrar;
        this.itemColorRegistrar = itemColorRegistrar;
    }

    @Override
    public void registerBlockColor(BlockColors colors) {
        blockColorRegistrar.accept(colors, this);
    }

    @Override
    public void registerItemColor(ItemColors colors) {
        itemColorRegistrar.accept(colors, this);
    }
}
