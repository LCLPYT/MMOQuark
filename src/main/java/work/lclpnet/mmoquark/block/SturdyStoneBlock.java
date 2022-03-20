package work.lclpnet.mmoquark.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import work.lclpnet.mmocontent.block.ext.MMOBlock;

public class SturdyStoneBlock extends MMOBlock {

    public SturdyStoneBlock() {
        super(FabricBlockSettings.of(Material.STONE)
                .requiresTool()
                .strength(4F, 10F)
                .sounds(BlockSoundGroup.STONE));
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }
}
