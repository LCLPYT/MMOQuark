package work.lclpnet.mmoquark.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import work.lclpnet.mmocontent.block.ext.MMOVerticalSlabBlock;

import java.util.Optional;
import java.util.Random;

public class WeatheringCopperVerticalSlabBlock extends MMOVerticalSlabBlock implements Oxidizable {

    private final OxidationLevel oxidationLevel;
    public WeatheringCopperVerticalSlabBlock next;

    public WeatheringCopperVerticalSlabBlock(Block parent) {
        super(parent);
        oxidationLevel = ((Oxidizable) parent).getDegradationLevel();
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.tickDegradation(state, world, pos, random);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return getDegradationResult(state).isPresent();
    }

    @Override
    public Optional<BlockState> getDegradationResult(BlockState state) {
        return next == null ? Optional.empty() : Optional.of(next.getStateWithProperties(state));
    }

    @Override
    public OxidationLevel getDegradationLevel() {
        return oxidationLevel;
    }
}
