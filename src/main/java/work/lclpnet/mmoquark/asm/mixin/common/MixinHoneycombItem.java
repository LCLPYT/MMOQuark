package work.lclpnet.mmoquark.asm.mixin.common;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoneycombItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import work.lclpnet.mmoquark.util.QWaxedBlocks;

@Mixin(HoneycombItem.class)
public class MixinHoneycombItem {

    @Inject(
            method = "useOnBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/HoneycombItem;getWaxedState(Lnet/minecraft/block/BlockState;)Ljava/util/Optional;"
            ),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void onWaxCustom(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, World world, BlockPos blockPos, BlockState blockState) {
        Block waxed = QWaxedBlocks.getWaxedBlock(blockState.getBlock());
        if (waxed == null) return;

        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();
        if (playerEntity instanceof ServerPlayerEntity)
            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) playerEntity, blockPos, itemStack);

        itemStack.decrement(1);
        world.setBlockState(blockPos, waxed.getStateWithProperties(blockState), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
        world.syncWorldEvent(playerEntity, WorldEvents.BLOCK_WAXED, blockPos, 0);

        cir.setReturnValue(ActionResult.success(world.isClient));
    }
}
