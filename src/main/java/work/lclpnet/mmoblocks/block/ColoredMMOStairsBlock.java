package work.lclpnet.mmoblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.color.block.BlockColors;

import java.util.function.BiConsumer;

@Environment(EnvType.CLIENT)
public class ColoredMMOStairsBlock extends MMOStairsBlock implements IBlockColorProvider {

    private final BiConsumer<BlockColors, Block> registrar;

    protected ColoredMMOStairsBlock(Block parent, BiConsumer<BlockColors, Block> registrar) {
        super(parent);
        this.registrar = registrar;
    }

    @Override
    public void registerBlockColor(BlockColors colors) {
        registrar.accept(colors, this);
    }
}
