package work.lclpnet.mmoquark.asm.mixin.client;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import work.lclpnet.mmoquark.util.MMOBlockColors;

@Mixin(ItemColors.class)
public class MixinItemColors {

    @Inject(method = "create", at = @At("RETURN"))
    private static void create(BlockColors blockMap, CallbackInfoReturnable<ItemColors> info) {
        ItemColors colors = info.getReturnValue();
        MMOBlockColors.registerItemColors(colors);
    }
}
