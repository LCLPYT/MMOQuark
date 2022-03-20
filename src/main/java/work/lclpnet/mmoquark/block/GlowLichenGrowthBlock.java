package work.lclpnet.mmoquark.block;

import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import work.lclpnet.mmoquark.block.ext.QPlantBlock;
import work.lclpnet.mmoquark.util.MiscUtil;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GlowLichenGrowthBlock extends QPlantBlock implements Fertilizable {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);

    public GlowLichenGrowthBlock() {
        super(Settings.copy(Blocks.GLOW_LICHEN)
                .ticksRandomly()
                .luminance(s -> 8));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        // spreading
        for(int i = 0; i < 10; i++)
            world.addParticle(ParticleTypes.MYCELIUM,
                    pos.getX() + (Math.random() - 0.5) * 5 + 0.5,
                    pos.getY() + (Math.random() - 0.5) * 8 + 0.5,
                    pos.getZ() + (Math.random() - 0.5) * 5 + 0.5,
                    0, 0, 0);

        // focused
        world.addParticle(ParticleTypes.MYCELIUM,
                pos.getX() + (Math.random() - 0.5) * 0.4 + 0.5,
                pos.getY() + (Math.random() - 0.5) * 0.3 + 0.3,
                pos.getZ() + (Math.random() - 0.5) * 0.4 + 0.5,
                0, 0, 0);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlantOnTop(BlockState state, BlockView world, BlockPos pos) {
        return state.isSideSolidFullSquare(world, pos, Direction.UP);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        for (Direction dir : MiscUtil.HORIZONTALS)
            if (canSpread(world, pos.offset(dir)))
                return true;

        return false;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        List<Direction> list = Lists.newArrayList(MiscUtil.HORIZONTALS);
        Collections.shuffle(list, random);
        for (Direction dir : list) {
            BlockPos offPos = pos.offset(dir);
            if (canSpread(world, offPos)) {
                world.setBlockState(offPos, state, 3);
                return;
            }
        }
    }

    private boolean canSpread(BlockView world, BlockPos pos) {
        BlockPos below = pos.down();
        return world.getBlockState(pos).isAir() && canPlantOnTop(world.getBlockState(below), world, below);
    }
}
