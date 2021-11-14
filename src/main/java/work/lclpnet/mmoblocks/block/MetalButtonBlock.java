package work.lclpnet.mmoblocks.block;

import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import work.lclpnet.mmoblocks.block.ext.MMOButtonBlock;

public class MetalButtonBlock extends MMOButtonBlock {

    private final int speed;

    public MetalButtonBlock(int speed) {
        super(false, Settings.of(Material.SUPPORTED)
                .noCollision()
                .strength(0.5F, 0.5F)
                .sounds(BlockSoundGroup.METAL));
        this.speed = speed;
    }

    @Override
    public int getActiveDuration() {
        return speed;
    }

    @Override
    protected SoundEvent getClickSound(boolean powered) {
        return powered ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
    }
}
