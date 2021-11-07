package work.lclpnet.mmoblocks.asm.mixin.client;

import net.minecraft.client.color.block.BlockColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import work.lclpnet.mmoblocks.util.MMOBlockColors;

@Mixin(BlockColors.class)
public class MixinBlockColors {

    @Inject(method = "create", at = @At("RETURN"))
    private static void create(CallbackInfoReturnable<BlockColors> info) {
        BlockColors colors = info.getReturnValue();
        MMOBlockColors.registerBlockColors(colors);
    }
}
