package work.lclpnet.mmoblocks.mixin.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import work.lclpnet.mmoblocks.block.WoodPostBlock;

@Mixin(AxeItem.class)
public class MixinAxeItem {

    @Inject(
            method = "useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    public void onStripe(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, World world, BlockPos blockPos, BlockState blockState) {
        Block b = blockState.getBlock();
        if (!(b instanceof WoodPostBlock)) return;

        Block stripped = ((WoodPostBlock) b).strippedBlock;
        if (stripped == null) return;

        PlayerEntity playerEntity = context.getPlayer();
        world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
        if (!world.isClient) {
            world.setBlockState(blockPos, stripped.getDefaultState().with(PillarBlock.AXIS, blockState.get(PillarBlock.AXIS)), 11);
            if (playerEntity != null) {
                context.getStack().damage(1, (LivingEntity)playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
            }
        }

        cir.setReturnValue(ActionResult.success(world.isClient));
        cir.cancel();
    }
}
