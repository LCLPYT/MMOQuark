package work.lclpnet.mmoquark.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import work.lclpnet.mmoquark.util.TriFunction;

public class CustomItemFrameItem extends Item {

    private final TriFunction<? extends AbstractDecorationEntity, World, BlockPos, Direction> entityProvider;

    public CustomItemFrameItem(Settings settings, TriFunction<? extends AbstractDecorationEntity, World, BlockPos, Direction> entityProvider) {
        super(settings);
        this.entityProvider = entityProvider;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        Direction facing = context.getSide();
        BlockPos placeLocation = pos.offset(facing);
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();
        if (player != null && !this.canPlace(player, facing, stack, placeLocation)) {
            return ActionResult.FAIL;
        } else {
            World world = context.getWorld();
            AbstractDecorationEntity frame = entityProvider.apply(world, placeLocation, facing);

            CompoundTag tag = stack.getTag();
            if (tag != null) EntityType.loadFromEntityTag(world, player, frame, tag);

            if (frame.canStayAttached()) {
                if (!world.isClient) {
                    frame.onPlace();
                    world.spawnEntity(frame);
                }

                stack.decrement(1);
            }

            return ActionResult.SUCCESS;
        }
    }

    protected boolean canPlace(PlayerEntity player, Direction facing, ItemStack stack, BlockPos pos) {
        return !World.isOutOfBuildLimitVertically(pos) && player.canPlaceOn(pos, facing, stack);
    }
}
