package work.lclpnet.mmoquark.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import work.lclpnet.mmocontent.block.ext.IMMOBlock;

import java.util.Random;

public class BlossomLeavesBlock extends LeavesBlock implements IMMOBlock {

    public BlossomLeavesBlock(MapColor color) {
        super(FabricBlockSettings.of(Material.LEAVES, color)
                .strength(0.2F, 0.2F)
                .ticksRandomly()
                .sounds(BlockSoundGroup.GRASS)
                .nonOpaque()
                .allowsSpawning((s, r, p, t) -> false)
                .suffocates((s, r, p) -> false)
                .blockVision((s, r, p) -> false));
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if(world.isAir(pos.down()) && random.nextInt(5) == 0) {
            double windStrength = 5 + Math.cos((double) world.getTime() / 2000) * 2;
            double windX = Math.cos((double) world.getTime() / 1200) * windStrength;
            double windZ = Math.sin((double) world.getTime() / 1000) * windStrength;

            world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, state), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, windX, -1.0, windZ);
        }
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }
}
