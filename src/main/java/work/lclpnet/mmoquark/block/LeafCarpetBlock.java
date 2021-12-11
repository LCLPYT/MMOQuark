package work.lclpnet.mmoquark.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import work.lclpnet.mmoquark.block.ext.IBlockColorProvider;
import work.lclpnet.mmoquark.block.ext.MMOBlock;
import work.lclpnet.mmoquark.util.Env;
import work.lclpnet.mmoquark.util.MMORenderLayers;

public class LeafCarpetBlock extends MMOBlock implements IBlockColorProvider {

    private static final VoxelShape SHAPE = createCuboidShape(0, 0, 0, 16, 1, 16);

    private final BlockState baseState;
    private ItemStack baseStack;

    public LeafCarpetBlock(Block baseBlock) {
        super(FabricBlockSettings.of(Material.CARPET)
                .hardness(0F)
                .resistance(0F)
                .sounds(BlockSoundGroup.GRASS)
                .breakByTool(FabricToolTags.HOES)
                .nonOpaque());

        baseState = baseBlock.getDefaultState();

        if (Env.isClient()) registerRenderLayer();
    }

    @Environment(EnvType.CLIENT)
    protected void registerRenderLayer() {
        MMORenderLayers.setBlockRenderType(this, RenderLayer.getCutout());
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

    @Override
    public void registerBlockColor(BlockColors colors) {
        colors.registerColorProvider((state, worldIn, pos, tintIndex) -> colors.getColor(baseState, worldIn, pos, tintIndex), this);
    }

    @Override
    public void registerItemColor(ItemColors colors) {
        if (baseStack == null) baseStack = new ItemStack(baseState.getBlock());
        colors.register((stack, tintIndex) -> colors.getColorMultiplier(baseStack, tintIndex), this);
    }
}
