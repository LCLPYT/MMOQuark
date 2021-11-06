package work.lclpnet.mmoblocks.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import work.lclpnet.mmoblocks.block.ext.MMOBlock;

public class ThatchBlock extends MMOBlock {

    public ThatchBlock() {
        super(FabricBlockSettings.of(Material.SOLID_ORGANIC, MaterialColor.YELLOW)
                .breakByTool(FabricToolTags.HOES)
                .strength(0.5F, 0.5F)
                .sounds(BlockSoundGroup.GRASS));
    }

    @Override
    public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
        entity.handleFallDamage(distance, 0.5F);
    }
}
