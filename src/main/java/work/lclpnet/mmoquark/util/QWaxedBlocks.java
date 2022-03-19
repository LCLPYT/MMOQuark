package work.lclpnet.mmoquark.util;

import com.google.common.collect.HashBiMap;
import net.minecraft.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class QWaxedBlocks {

    private static final HashBiMap<Block, Block> cleanToWaxMap = HashBiMap.create();
    private static final Map<Block, Block> axeScrape = new HashMap<>();

    public static void register(Block clean, Block waxed) {
        cleanToWaxMap.put(clean, waxed);
    }

    public static void registerAxeScrape(Block from, Block to) {
        axeScrape.put(from, to);
    }

    @Nullable
    public static Block getWaxedBlock(@Nonnull Block clean) {
        return cleanToWaxMap.get(clean);
    }

    @Nullable
    public static Block getCleanBlock(@Nonnull Block waxed) {
        return cleanToWaxMap.inverse().get(waxed);
    }

    @Nullable
    public static Block getAxeScrapeResult(@Nonnull Block block) {
        return axeScrape.get(block);
    }
}
