package work.lclpnet.mmoquark.block;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import work.lclpnet.mmocontent.block.ext.MMOVineBlock;

import java.util.Random;

public class QVineBlock extends MMOVineBlock {

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        scheduledTick(state, world, pos, random);
    }
}
