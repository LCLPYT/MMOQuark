package work.lclpnet.mmoquark.block;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import work.lclpnet.mmocontent.block.ext.IMMOBlock;

import net.minecraft.util.math.random.Random;

public class IronRodBlock extends RodBlock implements IMMOBlock {

    public static final BooleanProperty CONNECTED = BooleanProperty.of("connected");

    public IronRodBlock() {
        super(Settings.of(Material.METAL, MapColor.GRAY)
                .strength(5F, 10F)
                .sounds(BlockSoundGroup.METAL)
                .nonOpaque());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, CONNECTED);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {}

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }
}
