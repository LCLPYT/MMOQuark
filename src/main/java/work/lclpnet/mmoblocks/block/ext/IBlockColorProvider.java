package work.lclpnet.mmoblocks.block.ext;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.block.BlockColors;

public interface IBlockColorProvider extends IItemColorProvider {

    @Environment(EnvType.CLIENT)
    void registerBlockColor(BlockColors colors);
}
