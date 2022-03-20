package work.lclpnet.mmoquark.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import work.lclpnet.mmocontent.block.ext.MMOBlock;

import java.util.Random;

public class GlowceliumBlock extends MMOBlock {

    public GlowceliumBlock() {
        super(FabricBlockSettings.of(Material.SOLID_ORGANIC)
                .ticksRandomly()
                .strength(0.5F, 0.5F)
                .luminance(b -> 7)
                .sounds(BlockSoundGroup.GRASS));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.isClient) return;

        if (!canExist(state, world, pos)) world.setBlockState(pos, Blocks.DIRT.getDefaultState());
        else for (int i = 0; i < 4; ++i) {
            BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
            if(world.getBlockState(blockpos).getBlock() == Blocks.DIRT && canGrowTo(state, world, blockpos))
                world.setBlockState(blockpos, getDefaultState());
        }
    }

    private static boolean canExist(BlockState state, WorldAccess world, BlockPos pos) {
        BlockPos blockpos = pos.up();
        BlockState blockstate = world.getBlockState(blockpos);
        int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, blockstate, blockpos, Direction.UP, blockstate.getOpacity(world, blockpos));
        return i < world.getMaxLightLevel();
    }

    private static boolean canGrowTo(BlockState state, WorldAccess world, BlockPos pos) {
        BlockPos blockpos = pos.up();
        return canExist(state, world, pos) && !world.getFluidState(blockpos).isIn(FluidTags.WATER);
    }
}
