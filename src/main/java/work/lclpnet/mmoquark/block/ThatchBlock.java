package work.lclpnet.mmoquark.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import work.lclpnet.mmocontent.block.ext.MMOBlock;

public class ThatchBlock extends MMOBlock {

    public ThatchBlock() {
        super(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.YELLOW)
                .strength(0.5F, 0.5F)
                .sounds(BlockSoundGroup.GRASS));
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float distance) {
        entity.handleFallDamage(distance, 0.5F, DamageSource.FALL);
    }
}
