package work.lclpnet.mmoquark.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.mmoquark.block.ext.MMOBlock;
import work.lclpnet.mmoquark.blockentity.EnderWatcherBlockEntity;

public class EnderWatcherBlock extends MMOBlock implements BlockEntityProvider {

    public static final BooleanProperty WATCHED = BooleanProperty.of("watched");
    public static final IntProperty POWER = Properties.POWER;

    public EnderWatcherBlock() {
        super(Settings.of(Material.METAL, MaterialColor.GREEN)
                .strength(3F, 10F)
                .sounds(BlockSoundGroup.METAL));

        setDefaultState(getDefaultState().with(WATCHED, false).with(POWER, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATCHED, POWER);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(POWER);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new EnderWatcherBlockEntity();
    }
}
