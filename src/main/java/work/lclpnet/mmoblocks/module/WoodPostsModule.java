package work.lclpnet.mmoblocks.module;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.WoodPostBlock;
import work.lclpnet.mmoblocks.util.MiscUtil;

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
        String path = MiscUtil.getRegistryPath(b);
        if (path == null) return;

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
