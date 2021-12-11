package work.lclpnet.mmoquark.asm.mixin.common;

import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import work.lclpnet.mmoquark.module.WoodPostsModule;

@Mixin(LanternBlock.class)
public class MixinLanternBlock {

    @Inject(
            method = "canPlaceAt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z",
            at = @At("RETURN"),
            cancellable = true
    )
    public void onCanPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(WoodPostsModule.canLanternConnect(state, world, pos, cir.getReturnValue()));
    }
}
