package work.lclpnet.mmoblocks.block.ext;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import work.lclpnet.mmoblocks.asm.mixin.common.AbstractButtonBlockAccessor;

import javax.annotation.Nullable;

public abstract class MMOButtonBlock extends AbstractButtonBlock implements IMMOBlock {

    protected MMOButtonBlock(boolean wooden, Settings settings) {
        super(wooden, settings);
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }

    public abstract int getActiveDuration();

    public boolean isWooden() {
        return ((AbstractButtonBlockAccessor) this).isWooden();
    }

    @Nullable
    public static MMOButtonBlock asMMOButton(Object obj) {
        return obj instanceof MMOButtonBlock ? (MMOButtonBlock) obj : null;
    }
}
