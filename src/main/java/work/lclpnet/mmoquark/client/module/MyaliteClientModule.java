package work.lclpnet.mmoquark.client.module;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.gen.ChunkRandom;
import work.lclpnet.mmoquark.module.MoreStoneVariantsModule;
import work.lclpnet.mmoquark.module.NewStoneTypesModule;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MyaliteClientModule implements IClientModule {

    @Override
    public void registerClient() {
        /* Register block colors */

        List<Block> blocks = new ArrayList<>(MoreStoneVariantsModule.myaliteVariationBlocks);
        blocks.addAll(NewStoneTypesModule.myaliteBlocks);

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> getColor(pos, 0.7F, 0.8F), blocks.toArray(new Block[0]));

        /* register item colors */

        List<BlockItem> items = new ArrayList<>(MoreStoneVariantsModule.myaliteVariationItems);
        items.addAll(NewStoneTypesModule.myaliteItems);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player == null) return getColor(BlockPos.ORIGIN, 0.7F, 0.8F);

            BlockPos pos = mc.player.getBlockPos();
            HitResult res = mc.crosshairTarget;
            if (res instanceof BlockHitResult)
                pos = ((BlockHitResult) res).getBlockPos();

            return getColor(pos, 0.7F, 0.8F);
        }, items.toArray(new BlockItem[0]));
    }

    public static final OctaveSimplexNoiseSampler NOISE = new OctaveSimplexNoiseSampler(new ChunkRandom(4543543), IntStream.rangeClosed(-4, 4));

    public static int getColor(BlockPos pos, float s, float b) {
        final double sp = 0.15;
        final double range = 0.3;
        final double shift = 0.05;

        if (pos == null) pos = BlockPos.ORIGIN;

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
