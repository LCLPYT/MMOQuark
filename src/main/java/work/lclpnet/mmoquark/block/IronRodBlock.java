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

import java.util.Random;

public class IronRodBlock extends EndRodBlock implements IMMOBlock {

    public static final BooleanProperty CONNECTED = BooleanProperty.of("connected");

    public IronRodBlock() {
        super(Settings.of(Material.METAL, MaterialColor.GRAY)
                .strength(5F, 10F)
                .sounds(BlockSoundGroup.METAL)
                .nonOpaque());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CONNECTED);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {}

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }
}
