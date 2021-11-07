package work.lclpnet.mmoblocks.module;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import work.lclpnet.mmoblocks.asm.mixin.common.AbstractBlockAccessor;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;

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

        new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.MAGMA_BLOCK)
                .strength(1.5F, 10F)
                .emissiveLighting((s, r, p) -> true))
                .withWall().withSlab().withVerticalSlab().withStairs()
                .register("magma_bricks");
    }

    private void bricks(String name, Block parent) {
        Material parentMaterial = ((AbstractBlockAccessor) parent).getMaterial();

        new MMOBlockRegistrar(FabricBlockSettings.copyOf(parent)
                .strength(2F, 6F)
                .requiresTool()
                .breakByTool(parentMaterial == Material.SNOW_BLOCK ? FabricToolTags.SHOVELS : FabricToolTags.PICKAXES))
                .withSlab().withStairs().withWall().withVerticalSlab()
                .register(name + "_bricks");
    }
}
