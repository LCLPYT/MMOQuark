package work.lclpnet.mmoblocks.util;

import net.minecraft.client.color.block.BlockColors;
import work.lclpnet.mmoblocks.block.IBlockColorProvider;

import java.util.ArrayList;
import java.util.List;

public class MMOBlockColors {

    public static final List<IBlockColorProvider> providers = new ArrayList<>();

    public static void registerProvider(IBlockColorProvider provider) {
        providers.add(provider);
    }

    public static void registerBlockColors(BlockColors colors) {
        providers.forEach(provider -> provider.registerBlockColor(colors));
    }
}
