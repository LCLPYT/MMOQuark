package work.lclpnet.mmoblocks.module;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;

import static net.minecraft.block.AbstractBlock.Settings.copy;

public class MoreBricksModule implements IModule {

    @Override
    public void register() {
        bricks("sandy", Blocks.SANDSTONE);
        bricks("snow", Blocks.SANDSTONE);
        bricks("charred_nether", Blocks.NETHER_BRICKS);
        bricks("blue_nether", Blocks.NETHER_BRICKS);
        bricks("sandstone", Blocks.SANDSTONE);
        bricks("red_sandstone", Blocks.RED_SANDSTONE);
        bricks("soul_sandstone", Blocks.SANDSTONE);
        bricks("twisted_blackstone", Blocks.POLISHED_BLACKSTONE_BRICKS);
        bricks("weeping_blackstone", Blocks.BLACKSTONE);
    }

    private void bricks(String name, Block parent) {
        new MMOBlockRegistrar(copy(parent))
                .withSlab().withStairs().withWall().withVerticalSlab()
                .register(name + "_bricks");
    }
}
