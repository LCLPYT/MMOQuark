package work.lclpnet.mmoblocks.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import work.lclpnet.mmoblocks.block.ext.MMOBlock;

public class PaperLanternBlock extends MMOBlock {

    private static final VoxelShape POST_SHAPE = createCuboidShape(6, 0, 6, 10, 16, 10);
    private static final VoxelShape LANTERN_SHAPE = createCuboidShape(2, 2, 2, 14, 14, 14);
    private static final VoxelShape SHAPE = VoxelShapes.union(POST_SHAPE, LANTERN_SHAPE);

    public PaperLanternBlock() {
        super(FabricBlockSettings.of(Material.WOOD, MaterialColor.WHITE)
                .sounds(BlockSoundGroup.WOOD)
                .breakByTool(FabricToolTags.AXES, 0)
                .luminance(15)
                .strength(1.5F, 1.5F));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
