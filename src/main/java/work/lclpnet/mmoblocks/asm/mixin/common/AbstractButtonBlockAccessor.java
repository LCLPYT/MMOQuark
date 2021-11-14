package work.lclpnet.mmoblocks.asm.mixin.common;

import net.minecraft.block.AbstractButtonBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractButtonBlock.class)
public interface AbstractButtonBlockAccessor {

    @Accessor
    boolean isWooden();
}
