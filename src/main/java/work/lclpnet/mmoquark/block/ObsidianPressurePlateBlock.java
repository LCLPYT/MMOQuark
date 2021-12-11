package work.lclpnet.mmoquark.block;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import work.lclpnet.mmocontent.block.ext.MMOPressurePlateBlock;

import java.util.List;

public class ObsidianPressurePlateBlock extends MMOPressurePlateBlock {

    public static final BooleanProperty POWERED = Properties.POWERED;

    public ObsidianPressurePlateBlock(Settings settings) {
        super(null /* unused */, settings);
        setDefaultState(getDefaultState().with(POWERED, false));
    }

    @Override
    protected void playPressSound(WorldAccess world, BlockPos pos) {
        world.playSound(null, pos, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.5F);
    }

    @Override
    protected void playDepressSound(WorldAccess world, BlockPos pos) {
        world.playSound(null, pos, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.4F);
    }

    @Override
    protected int getRedstoneOutput(World world, BlockPos pos) {
        Box bounds = BOX.offset(pos);
        List<? extends Entity> entities = world.getNonSpectatingEntities(PlayerEntity.class, bounds);

        if (!entities.isEmpty())
            for (Entity entity : entities)
                if (!entity.canAvoidTraps())
                    return 15;

        return 0;
    }
}
