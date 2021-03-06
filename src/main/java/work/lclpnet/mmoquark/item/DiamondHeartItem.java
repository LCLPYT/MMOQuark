package work.lclpnet.mmoquark.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import work.lclpnet.mmoquark.entity.StonelingEntity;
import work.lclpnet.mmoquark.entity.StonelingVariant;
import work.lclpnet.mmoquark.module.StonelingsModule;

public class DiamondHeartItem extends Item {

    public DiamondHeartItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Hand hand = context.getHand();
        Direction facing = context.getSide();

        if (player != null) {
            BlockState stateAt = world.getBlockState(pos);
            ItemStack stack = player.getStackInHand(hand);

            if (player.canPlaceOn(pos, facing, stack) && stateAt.getHardness(world, pos) != -1) {
                StonelingVariant variant = null;
                for (StonelingVariant possibleVariant : StonelingVariant.values()) {
                    if (possibleVariant.getBlocks().contains(stateAt.getBlock()))
                        variant = possibleVariant;
                }

                if (variant != null) {
                    if (!world.isClient && world instanceof ServerWorldAccess) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                        world.syncWorldEvent(2001, pos, Block.getRawIdFromState(stateAt));

                        StonelingEntity stoneling = new StonelingEntity(StonelingsModule.stonelingType, world);
                        stoneling.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                        stoneling.setPlayerMade(true);
                        stoneling.setYaw(player.getYaw() + 180F);
                        stoneling.initialize((ServerWorldAccess) world, world.getLocalDifficulty(pos), SpawnReason.STRUCTURE, variant, null);
                        world.spawnEntity(stoneling);

                        if (player instanceof ServerPlayerEntity)
                            Criteria.SUMMONED_ENTITY.trigger((ServerPlayerEntity) player, stoneling);

                        if (!player.getAbilities().creativeMode)
                            stack.decrement(1);
                    }

                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
