package work.lclpnet.mmoquark.module.content;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AbstractVariantTupleResult {

    protected abstract Set<VariantTuple> getVariantTuples();

    public List<Block> getAllBlocks() {
        List<Block> blocks = new ArrayList<>();

        getVariantTuples().forEach(variantTuple -> {
            if (variantTuple.block != null) blocks.add(variantTuple.block);
            addExtraBlocks(variantTuple.extra, blocks);
        });

        return blocks;
    }

    public List<BlockItem> getAllItems() {
        List<BlockItem> items = new ArrayList<>();

        getVariantTuples().forEach(variantTuple -> {
            if (variantTuple.extra.item != null) items.add(variantTuple.extra.item);
            addExtraItems(variantTuple.extra, items);
        });

        return items;
    }

    private void addExtraBlocks(MMOBlockRegistrar.Result result, List<Block> blocks) {
        addRegisteredBlock(result.slab, blocks);
        addRegisteredBlock(result.verticalSlab, blocks);
        addRegisteredBlock(result.stairs, blocks);
        addRegisteredBlock(result.wall, blocks);
        addRegisteredBlock(result.pane, blocks);
    }

    private void addExtraItems(MMOBlockRegistrar.Result result, List<BlockItem> items) {
        addRegisteredItem(result.slab, items);
        addRegisteredItem(result.verticalSlab, items);
        addRegisteredItem(result.stairs, items);
        addRegisteredItem(result.wall, items);
        addRegisteredItem(result.pane, items);
    }

    private <T extends Block> void addRegisteredBlock(MMOBlockRegistrar.RegisteredBlock<T> item, List<Block> list) {
        if (item != null) list.add(item.block);
    }

    private void addRegisteredItem(MMOBlockRegistrar.RegisteredBlock<?> item, List<BlockItem> list) {
        if (item != null) list.add(item.item);
    }
}
