package work.lclpnet.mmoquark.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;

public class CobblestoneBricksModule implements IModule {

    @Override
    public void register() {
        addBricks("cobblestone_bricks", Blocks.MOSSY_COBBLESTONE);
        addBricks("mossy_cobblestone_bricks", Blocks.MOSSY_COBBLESTONE);

        addBricks("blackstone_bricks", Blocks.BLACKSTONE);
        addBricks("dirt_bricks", Blocks.DIRT);
        addBricks("netherrack_bricks", Blocks.NETHERRACK);
        addBricks("vanilla_basalt_bricks", Blocks.BASALT);
    }

    private void addBricks(String name, Block block) {
        new MMOBlockRegistrar(AbstractBlock.Settings.copy(block))
                .withSlab().withWall().withStairs().withVerticalSlab()
                .register(MMOQuark.identifier(name));
    }
}
