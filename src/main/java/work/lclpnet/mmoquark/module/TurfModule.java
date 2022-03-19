package work.lclpnet.mmoquark.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.block.ext.MMOBlock;
import work.lclpnet.mmoquark.MMOQuark;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TurfModule implements IModule {

    public static final List<Block> turfBlocks = new ArrayList<>();
    public static final List<BlockItem> turfItems = new ArrayList<>();

    @Override
    public void register() {
        MMOBlock turfBlock = new MMOBlock(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK));
        MMOBlockRegistrar.Result result = new MMOBlockRegistrar(turfBlock)
                .withSlab().withStairs().withVerticalSlab()
                .register(MMOQuark.identifier("turf"));

        turfBlocks.add(turfBlock);
        turfItems.add(result.item());

        addRegistered(result.slab());
        addRegistered(result.stairs());
        addRegistered(result.verticalSlab());
    }

    protected void addRegistered(@Nullable MMOBlockRegistrar.RegisteredBlock<?> registered) {
        if (registered == null) return;

        turfBlocks.add(registered.block());
        turfItems.add(registered.item());
    }
}
