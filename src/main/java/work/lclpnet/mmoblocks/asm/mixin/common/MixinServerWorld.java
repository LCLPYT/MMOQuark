package work.lclpnet.mmoblocks.asm.mixin.common;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import work.lclpnet.mmoblocks.entity.CrabEntity;

@Mixin(ServerWorld.class)
public class MixinServerWorld {

    @Inject(
            method = "syncWorldEvent(Lnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/util/math/BlockPos;I)V",
            at = @At("HEAD")
    )
    public void rave(PlayerEntity player, int eventId, BlockPos pos, int data, CallbackInfo ci) {
        if (eventId == 1010) CrabEntity.rave((ServerWorld) (Object) this, pos, data != 0);
    }
}
