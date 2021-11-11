package work.lclpnet.mmoblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import work.lclpnet.mmoblocks.block.ext.IMMOBlock;
import work.lclpnet.mmoblocks.module.GlowshroomModule;
import work.lclpnet.mmoblocks.util.Env;
import work.lclpnet.mmoblocks.util.MMORenderLayers;

import java.util.Random;

public class GlowshroomPlantBlock extends MushroomPlantBlock implements IMMOBlock {

    public GlowshroomPlantBlock() {
        super(FabricBlockSettings.of(Material.PLANT, MaterialColor.CYAN)
                .noCollision()
                .breakInstantly()
                .sounds(BlockSoundGroup.GRASS)
                .postProcess((s, r, p) -> true)
                .luminance(b -> 14));

        if (Env.isClient()) registerRenderLayer();
    }

    @Environment(EnvType.CLIENT)
    protected void registerRenderLayer() {
        MMORenderLayers.setBlockRenderType(this, RenderLayer.getCutout());
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockpos = pos.down();
        BlockState blockstate = world.getBlockState(blockpos);
        return blockstate.isIn(BlockTags.MUSHROOM_GROW_BLOCK);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(20) != 0 || world.getBlockState(pos.down()).getBlock() != GlowshroomModule.glowcelium) return;

        int i = 5;

        for (BlockPos targetPos : BlockPos.iterate(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
            if (world.getBlockState(targetPos).getBlock() == this) {
                --i;

                if (i <= 0) return;
            }
        }

        BlockPos shiftedPos = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);

        for (int k = 0; k < 4; ++k) {
            if (world.isAir(shiftedPos) && state.canPlaceAt(world, shiftedPos)) pos = shiftedPos;
            shiftedPos = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
        }

        if (world.isAir(shiftedPos) && state.canPlaceAt(world, shiftedPos)) world.setBlockState(shiftedPos, getDefaultState(), 2);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        if(random.nextInt(20) == 0)
            world.addParticle(ParticleTypes.END_ROD, pos.getX() + 0.2 + random.nextDouble() * 0.6, pos.getY() + 0.3, pos.getZ() + 0.2 + random.nextDouble() * 0.6, 0, 0, 0);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return super.canPlantOnTop(floor, world, pos);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return random.nextFloat() < 0.4;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.removeBlock(pos, false);
        if (!GlowshroomBlock.place(world, random, pos))
            world.setBlockState(pos, getDefaultState());
    }
}
