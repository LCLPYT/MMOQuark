package work.lclpnet.mmoquark.asm.mixin.common;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import work.lclpnet.mmoquark.block.WoodPostBlock;
import work.lclpnet.mmoquark.util.QWaxedBlocks;

@Mixin(AxeItem.class)
public class MixinAxeItem {

    @Inject(
            method = "useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/AxeItem;getStrippedState(Lnet/minecraft/block/BlockState;)Ljava/util/Optional;"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    public void onStripe(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, World world,
                         BlockPos blockPos, PlayerEntity playerEntity, BlockState blockState) {
        final Block b = blockState.getBlock();
        if (!(b instanceof WoodPostBlock)) return;

        final Block stripped = ((WoodPostBlock) b).strippedBlock;
        if (stripped == null) return;

        world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

        quark$axeUse(context, cir, world, blockPos, playerEntity, stripped.getDefaultState().with(PillarBlock.AXIS, blockState.get(PillarBlock.AXIS)));
    }

    @Unique
    private void quark$axeUse(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, World world,
                              BlockPos blockPos, PlayerEntity playerEntity, BlockState toState) {
        final ItemStack itemStack = context.getStack();
        if (playerEntity instanceof ServerPlayerEntity)
            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) playerEntity, blockPos, itemStack);

        world.setBlockState(blockPos, toState, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);

        if (playerEntity != null)
            itemStack.damage(1, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));

        cir.setReturnValue(ActionResult.success(world.isClient));
    }

    @Inject(
            method = "useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Oxidizable;getDecreasedOxidationState(Lnet/minecraft/block/BlockState;)Ljava/util/Optional;"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    public void onScape(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, World world,
                         BlockPos blockPos, PlayerEntity playerEntity, BlockState blockState) {
        final Block block = blockState.getBlock();
        final Block scraped = QWaxedBlocks.getAxeScrapeResult(block);
        if (scraped == null) return;

        world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0f, 1.0f);
        world.syncWorldEvent(playerEntity, WorldEvents.BLOCK_SCRAPED, blockPos, 0);

        quark$axeUse(context, cir, world, blockPos, playerEntity, scraped.getStateWithProperties(blockState));
    }

    @Inject(
            method = "useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/item/HoneycombItem;WAXED_TO_UNWAXED_BLOCKS:Ljava/util/function/Supplier;",
                    opcode = Opcodes.GETSTATIC
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    public void onWaxScape(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, World world,
                        BlockPos blockPos, PlayerEntity playerEntity, BlockState blockState) {
        final Block waxed = blockState.getBlock();
        final Block clean = QWaxedBlocks.getCleanBlock(waxed);
        if (clean == null) return;

        world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0f, 1.0f);
        world.syncWorldEvent(playerEntity, WorldEvents.WAX_REMOVED, blockPos, 0);

        quark$axeUse(context, cir, world, blockPos, playerEntity, clean.getStateWithProperties(blockState));
    }
}
