package work.lclpnet.mmoquark.module;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.util.RegistryUtil;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.WoodPostBlock;

import java.util.ArrayList;
import java.util.List;

public class WoodPostsModule implements IModule {

    public static final List<WoodPostBlock> woodPosts = new ArrayList<>();

    @Override
    public void register() {
        ImmutableList.of(Blocks.OAK_FENCE, Blocks.SPRUCE_FENCE, Blocks.BIRCH_FENCE,
                        Blocks.JUNGLE_FENCE, Blocks.ACACIA_FENCE, Blocks.DARK_OAK_FENCE,
                        AzaleaWoodModule.azaleaWood.fence, BlossomTreesModule.blossomWood.fence)
                .forEach(b -> registerPosts(b, false));
        ImmutableList.of(Blocks.CRIMSON_FENCE, Blocks.WARPED_FENCE)
                .forEach(b -> registerPosts(b, true));
    }

    private void registerPosts(Block b, boolean nether) {
        String path = RegistryUtil.getRegistryPath(b);
        if (path == null) return;

        String postPath = path.replace("_fence", "_post");

        WoodPostBlock post = new WoodPostBlock(b, nether);
        new MMOBlockRegistrar(post)
                .register(MMOQuark.identifier(postPath), ItemGroup.DECORATIONS);

        woodPosts.add(post);

        post.strippedBlock = new WoodPostBlock(b, nether);
        new MMOBlockRegistrar(post.strippedBlock)
                .register(MMOQuark.identifier("stripped_".concat(postPath)), ItemGroup.DECORATIONS);
    }

    public static boolean canLanternConnect(BlockState state, WorldView worldIn, BlockPos pos, boolean prev) {
        return prev && state.get(LanternBlock.HANGING) && worldIn.getBlockState(pos.up()).getBlock() instanceof WoodPostBlock;
    }
}
