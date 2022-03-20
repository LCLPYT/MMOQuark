package work.lclpnet.mmoquark.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import work.lclpnet.mmocontent.block.ext.MMOBlock;

public class LeafCarpetBlock extends MMOBlock {

    private static final VoxelShape SHAPE = createCuboidShape(0, 0, 0, 16, 1, 16);

    public final BlockState baseState;
    private ItemStack baseStack;

    public LeafCarpetBlock(Block baseBlock) {
        super(FabricBlockSettings.of(Material.CARPET)
                .hardness(0F)
                .resistance(0F)
                .sounds(BlockSoundGroup.GRASS)
                .nonOpaque());

        baseState = baseBlock.getDefaultState();
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return true;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.collidable ? state.getOutlineShape(world, pos) : VoxelShapes.empty();
    }

    public ItemStack getBaseStack() {
        return baseStack == null ? baseStack = new ItemStack(baseState.getBlock()) : baseStack;
    }
}
