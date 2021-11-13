package work.lclpnet.mmoblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.Material;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import work.lclpnet.mmoblocks.block.ext.MMOVineBlock;
import work.lclpnet.mmoblocks.util.MiscUtil;

import java.util.Random;

public class RootBlock extends MMOVineBlock implements Fertilizable {

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return true;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient && world.random.nextInt(2) == 0 && isFertilizable(world, pos, state, false))
            grow(world, random, pos, state);
    }

    public static void grow(WorldAccess world, BlockPos pos, BlockState state) {
        BlockPos down = pos.down();

        for (Direction facing : MiscUtil.HORIZONTALS) {
            BooleanProperty prop = getFacingProperty(facing);
            if (state.get(prop)) {
                BlockPos ret = growInFacing(world, down, facing);
                if (ret != null) {
                    BlockState setState = state.getBlock().getDefaultState().with(prop, true);
                    world.setBlockState(ret, setState, 2);
                    return;
                }
                break;
            }
        }

    }

    public static BlockPos growInFacing(WorldAccess world, BlockPos pos, Direction facing) {
        if (!world.isAir(pos))
            return null;

        BlockPos check = pos.offset(facing);
        if (isAcceptableNeighbor(world, check, facing.getOpposite()))
            return pos;

        pos = check;
        if (!world.isAir(check))
            return null;

        check = pos.offset(facing);
        if (isAcceptableNeighbor(world, check, facing.getOpposite()))
            return pos;

        return null;
    }

    public static boolean isAcceptableNeighbor(WorldAccess world, BlockPos pos, Direction side) {
        BlockState blockState = world.getBlockState(pos);
        return Block.isFaceFullSquare(blockState.getCollisionShape(world, pos), side) && blockState.getMaterial() == Material.STONE;
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) world;
            return serverWorld.getBaseLightLevel(pos, 0) < 7;
        }
        return false;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return random.nextFloat() < 0.4;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        grow(world, pos, state);
    }
}
