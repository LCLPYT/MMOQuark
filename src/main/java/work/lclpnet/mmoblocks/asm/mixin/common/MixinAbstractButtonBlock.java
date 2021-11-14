package work.lclpnet.mmoblocks.asm.mixin.common;

import net.minecraft.block.AbstractButtonBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import work.lclpnet.mmoblocks.block.ext.MMOButtonBlock;

@Mixin(AbstractButtonBlock.class)
public class MixinAbstractButtonBlock {

    @Inject(
            method = "getPressTicks()I",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getPressTicks(CallbackInfoReturnable<Integer> cir) {
        MMOButtonBlock button = MMOButtonBlock.asMMOButton(this);
        if (button == null) return;

        cir.setReturnValue(button.getActiveDuration());
        cir.cancel();
    }
}
