package work.lclpnet.mmoquark.block;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import work.lclpnet.mmocontent.block.ext.MMOBlock;

public class RopeBlock extends MMOBlock {

    private static final VoxelShape SHAPE = createCuboidShape(6, 0, 6, 10, 16, 10);

    public RopeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(hand != Hand.MAIN_HAND) return ActionResult.PASS;

        ItemStack stack = player.getStackInHand(hand);
        if(stack.getItem() == asItem() && !player.isSneaky()) {
            if (pullDown(world, pos)) {
                if(!player.isCreative())
                    stack.decrement(1);

                world.playSound(null, pos, soundGroup.getPlaceSound(), SoundCategory.BLOCKS, 0.5F, 1F);
                return ActionResult.SUCCESS;
            }
        } else if (stack.getItem() == Items.GLASS_BOTTLE) {
            BlockPos bottomPos = getBottomPos(world, pos);
            BlockState stateAt = world.getBlockState(bottomPos);
            if (stateAt.getMaterial() == Material.WATER) {
                Vec3d playerPos = player.getPos();
                world.playSound(player, playerPos.x, playerPos.y, playerPos.z, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                stack.decrement(1);
                ItemStack bottleStack = PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));

                if (stack.isEmpty())
                    player.setStackInHand(hand, bottleStack);
                else if (!player.inventory.insertStack(bottleStack))
                    player.dropItem(bottleStack, false);


                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        } else {
            if(pullUp(world, pos)) {
                if(!player.isCreative()) {
                    if(!player.giveItemStack(new ItemStack(this)))
                        player.dropItem(new ItemStack(this), false);
                }

                world.playSound(null, pos, soundGroup.getBreakSound(), SoundCategory.BLOCKS, 0.5F, 1F);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    public boolean pullUp(World world, BlockPos pos) {
        BlockPos basePos = pos;

        while(true) {
            pos = pos.down();
            BlockState state = world.getBlockState(pos);
            if(state.getBlock() != this)
                break;
        }

        BlockPos ropePos = pos.up();
        if(ropePos.equals(basePos))
            return false;

        world.setBlockState(ropePos, Blocks.AIR.getDefaultState());

        return true;
    }

    public boolean pullDown(World world, BlockPos pos) {
        boolean can;
        boolean endRope = false;
        boolean wasAirAtEnd = false;

        do {
            pos = pos.down();
            if (!World.isValid(pos))
                return false;

            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if(block == this)
                continue;

            if(endRope) {
                can = wasAirAtEnd || world.isAir(pos) || state.getMaterial().isReplaceable();
                break;
            }

            endRope = true;
            wasAirAtEnd = world.isAir(pos);
        } while(true);

        if(can) {
            BlockPos ropePos = pos.up();

            BlockState ropePosState = world.getBlockState(ropePos);

            if(world.isAir(ropePos) || ropePosState.getMaterial().isReplaceable()) {
                world.setBlockState(ropePos, getDefaultState());
                return true;
            }
        }

        return false;
    }

    private BlockPos getBottomPos(World worldIn, BlockPos pos) {
        Block block = this;
        while (block == this) {
            pos = pos.down();
            BlockState state = worldIn.getBlockState(pos);
            block = state.getBlock();
        }

        return pos;

    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos upPos = pos.up();
        BlockState upState = world.getBlockState(upPos);
        return upState.getBlock() == this || upState.isSideSolidFullSquare(world, upPos, Direction.DOWN);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if(!state.canPlaceAt(world, pos)) {
            world.syncWorldEvent(2001, pos, Block.getRawIdFromState(world.getBlockState(pos)));
            dropStacks(state, world, pos);
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }
}
