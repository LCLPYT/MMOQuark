package work.lclpnet.mmoblocks.block;

import java.awt.Color;
import java.util.stream.IntStream;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.gen.ChunkRandom;

public interface IMyaliteColorProvider extends IBlockColorProvider {

    OctaveSimplexNoiseSampler NOISE = new OctaveSimplexNoiseSampler(new ChunkRandom(4543543), IntStream.rangeClosed(-4, 4));

    @Override
    default void registerBlockColor(BlockColors colors) {
        registerBlockColor(colors, getMyaliteBlock());
    }

    static void registerBlockColor(BlockColors colors, Block block) {
        colors.registerColorProvider((state, world, pos, tintIndex) -> getColor(pos, 0.7F, 0.8F), block);
    }

    Block getMyaliteBlock();

    @Override
    default void registerItemColor(ItemColors colors) {
        registerItemColor(colors, getMyaliteBlock());
    }

    static void registerItemColor(ItemColors colors, ItemConvertible convertibale_block) {
        colors.register( (stack, tintIndex) -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if(mc.player == null)
                return getColor(BlockPos.ORIGIN, 0.7F, 0.8F);

            BlockPos pos = mc.player.getBlockPos();
            HitResult res = mc.crosshairTarget;
            if(res instanceof BlockHitResult)
                pos = ((BlockHitResult) res).getBlockPos();

            return getColor(pos, 0.7F, 0.8F);
        }, convertibale_block);
    }


    static int getColor(BlockPos pos, float s, float b) {
        final double sp = 0.15;
        final double range = 0.3;
        final double shift = 0.05;

        if(pos == null)
            pos = BlockPos.ORIGIN;

        double x = pos.getX() * sp;
        double y = pos.getY() * sp;
        double z = pos.getZ() * sp;

        double xv = x + Math.sin(z) * 2;
        double zv = z + Math.cos(x) * 2;
        double yv = y + Math.sin(y + Math.PI / 4) * 2;

        double noise = NOISE.sample(xv + yv, zv + (yv * 2), false);

        double h = noise * (range / 2) - range + shift;

        return Color.HSBtoRGB((float) h, s, b);
    }

}