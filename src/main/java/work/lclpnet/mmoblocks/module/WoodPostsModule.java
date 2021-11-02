package work.lclpnet.mmoblocks.module;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldView;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.WoodPostBlock;

public class WoodPostsModule implements IModule {

    @Override
    public void register() {
        ImmutableList.of(Blocks.OAK_FENCE, Blocks.SPRUCE_FENCE, Blocks.BIRCH_FENCE,
                        Blocks.JUNGLE_FENCE, Blocks.ACACIA_FENCE, Blocks.DARK_OAK_FENCE)
                .forEach(b -> registerPosts(b, false));
        ImmutableList.of(Blocks.CRIMSON_FENCE, Blocks.WARPED_FENCE)
                .forEach(b -> registerPosts(b, true));
    }

    private void registerPosts(Block b, boolean nether) {
        Identifier key = Registry.BLOCK.getId(b);
        String path = key.getPath();
        if (path.equals("air") && key.getNamespace().equals("minecraft")) return; // default value, if block does not exist in registry

        String postPath = path.replace("_fence", "_post");

        WoodPostBlock post = new WoodPostBlock(b, nether);
        new MMOBlockRegistrar(post)
                .register(postPath);

        post.strippedBlock = new WoodPostBlock(b, nether);
        new MMOBlockRegistrar(post.strippedBlock)
                .register("stripped_".concat(postPath));
    }

    public static boolean canLanternConnect(BlockState state, WorldView worldIn, BlockPos pos, boolean prev) {
        return prev && state.get(LanternBlock.HANGING) && worldIn.getBlockState(pos.up()).getBlock() instanceof WoodPostBlock;
    }
}
