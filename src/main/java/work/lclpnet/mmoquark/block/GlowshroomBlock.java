package work.lclpnet.mmoquark.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import work.lclpnet.mmoquark.asm.mixin.common.AbstractBlockAccessor;
import work.lclpnet.mmoquark.block.ext.IMMOBlock;
import work.lclpnet.mmoquark.module.GlowshroomModule;
import work.lclpnet.mmoquark.util.Env;
import work.lclpnet.mmoquark.util.MMORenderLayers;

import java.util.Random;

public class GlowshroomBlock extends MushroomBlock implements IMMOBlock {

    public GlowshroomBlock() {
        super(AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM_BLOCK)
                .luminance(b -> 14)
                .ticksRandomly()
                .nonOpaque());

        if (Env.isClient()) registerRenderLayer();
    }

    @Environment(EnvType.CLIENT)
    protected void registerRenderLayer() {
        MMORenderLayers.setBlockRenderType(this, RenderLayer.getTranslucent());
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);

        if (random.nextInt(10) == 0)
            world.addParticle(ParticleTypes.END_ROD, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), 0, 0, 0);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.getBlock() == this || super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public static boolean place(World world, Random rand, BlockPos pos) {
        int i = rand.nextInt(3) + 4;
        if (rand.nextInt(12) == 0) {
            i *= 2;
        }

        int j = pos.getY();
        if (j >= 1 && j + i + 1 < 256) {
            Block block = world.getBlockState(pos.down()).getBlock();
            if (block != GlowshroomModule.glowcelium) {
                return false;
            } else {
                BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

                for (int k = 0; k <= i; ++k) {
                    int l = 0;
                    if (k < i && k >= i - 3) {
                        l = 2;
                    } else if (k == i) {
                        l = 1;
                    }

                    for (int i1 = -l; i1 <= l; ++i1) {
                        for (int j1 = -l; j1 <= l; ++j1) {
                            BlockState blockstate = world.getBlockState(blockpos$mutableblockpos.set(pos).move(i1, k, j1));

                            boolean air = ((AbstractBlockAccessor) blockstate.getBlock()).getMaterial() == Material.AIR;
                            if (!air && !blockstate.isIn(BlockTags.LEAVES)) return false;
                        }
                    }
                }

                BlockState blockstate1 = GlowshroomModule.glowshroom_block.getDefaultState().with(MushroomBlock.DOWN, false);

                for (int l1 = i - 3; l1 <= i; ++l1) {
                    int i2 = l1 < i ? 2 : 1;

                    for (int l2 = -i2; l2 <= i2; ++l2) {
                        for (int k1 = -i2; k1 <= i2; ++k1) {
                            boolean flag = l2 == -i2;
                            boolean flag1 = l2 == i2;
                            boolean flag2 = k1 == -i2;
                            boolean flag3 = k1 == i2;
                            boolean flag4 = flag || flag1;
                            boolean flag5 = flag2 || flag3;
                            if (l1 >= i || flag4 != flag5) {
                                blockpos$mutableblockpos.set(pos).move(l2, l1, k1);
                                BlockState state = world.getBlockState(blockpos$mutableblockpos);
                                boolean air = ((AbstractBlockAccessor) state.getBlock()).getMaterial() == Material.AIR;
                                if (air || state.isIn(BlockTags.LEAVES)) {
                                    world.setBlockState(blockpos$mutableblockpos, blockstate1
                                            .with(MushroomBlock.UP, l1 >= i - 1)
                                            .with(MushroomBlock.WEST, l2 < 0)
                                            .with(MushroomBlock.EAST, l2 > 0)
                                            .with(MushroomBlock.NORTH, k1 < 0)
                                            .with(MushroomBlock.SOUTH, k1 > 0), 2);
                                }
                            }
                        }
                    }
                }

                BlockState blockstate2 = GlowshroomModule.glowshroom_stem.getDefaultState()
                        .with(MushroomBlock.UP, Boolean.FALSE)
                        .with(MushroomBlock.DOWN, Boolean.FALSE);

                for (int j2 = 0; j2 < i; ++j2) {
                    blockpos$mutableblockpos.set(pos).move(Direction.UP, j2);
                    BlockState state = world.getBlockState(blockpos$mutableblockpos);
                    boolean air = ((AbstractBlockAccessor) state.getBlock()).getMaterial() == Material.AIR;
                    if (air || state.isIn(BlockTags.LEAVES)) { // can be replaced by leaves
                        world.setBlockState(blockpos$mutableblockpos, blockstate2, 3);
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }
}
