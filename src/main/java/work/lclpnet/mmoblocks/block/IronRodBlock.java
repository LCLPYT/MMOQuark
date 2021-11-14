package work.lclpnet.mmoblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import work.lclpnet.mmoblocks.util.Env;
import work.lclpnet.mmoblocks.util.MMORenderLayers;

import java.util.Random;

public class IronRodBlock extends EndRodBlock {

    public static final BooleanProperty CONNECTED = BooleanProperty.of("connected");

    public IronRodBlock() {
        super(Settings.of(Material.METAL, MaterialColor.GRAY)
                .strength(5F, 10F)
                .sounds(BlockSoundGroup.METAL)
                .nonOpaque());

        if (Env.isClient()) registerRenderLayer();
    }

    @Environment(EnvType.CLIENT)
    protected void registerRenderLayer() {
        MMORenderLayers.setBlockRenderType(this, RenderLayer.getCutout());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(CONNECTED);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {}
}
