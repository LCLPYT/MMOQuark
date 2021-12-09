package work.lclpnet.mmoblocks.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class MMOBlockEntityItem extends BlockItem {

    private final Supplier<BlockEntity> entity;
    @Nullable
    private final Consumer<Block> store;

    public MMOBlockEntityItem(Block block, Settings settings, Supplier<BlockEntity> entity, @Nullable Consumer<Block> store) {
        super(block, settings);
        this.entity = entity;
        this.store = store;
    }

    public BlockEntity getEntity() {
        return entity.get();
    }

    public void beforeRender() {
        if (store != null) store.accept(getBlock());
    }

    public void afterRender() {
        if (store != null) store.accept(null);
    }
}
