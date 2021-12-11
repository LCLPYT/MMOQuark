package work.lclpnet.mmoquark.block;

import it.unimi.dsi.fastutil.floats.Float2ObjectArrayMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.mmocontent.block.ext.MMOBlock;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmocontent.util.Env;
import work.lclpnet.mmoquark.asm.type.IEntityShapeContext;

public class GrateBlock extends MMOBlock implements Waterloggable {

    private static final VoxelShape TRUE_SHAPE = createCuboidShape(0, 15, 0, 16, 16, 16);
    private static final Float2ObjectArrayMap<VoxelShape> WALK_BLOCK_CACHE = new Float2ObjectArrayMap<>();

    public static BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public GrateBlock() {
        super(Settings.of(Material.METAL)
                .strength(5F, 10F)
                .sounds(BlockSoundGroup.METAL)
                .nonOpaque());

        setDefaultState(getDefaultState().with(WATERLOGGED, false));

        if (Env.isClient()) registerRenderLayer();
    }

    @Environment(EnvType.CLIENT)
    protected void registerRenderLayer() {
        MMORenderLayers.setBlockRenderType(this, RenderLayer.getCutout());
    }

    private static VoxelShape createNewBox(double stepHeight) {
        return createCuboidShape(0, 15, 0, 16, 17 + 16 * stepHeight, 16);
    }

    @Override
    public boolean hasDynamicBounds() {
        return true;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return TRUE_SHAPE;
    }

    private static VoxelShape getCachedShape(float stepHeight) {
        return WALK_BLOCK_CACHE.computeIfAbsent(stepHeight, GrateBlock::createNewBox);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Entity entity = context instanceof IEntityShapeContext ? ((IEntityShapeContext) context).getEntity() : null;

        if (entity != null) {
            if (entity instanceof ItemEntity || entity instanceof ExperienceOrbEntity) return VoxelShapes.empty();

            boolean animal = entity instanceof AnimalEntity;
            boolean leashed = animal && ((AnimalEntity) entity).getLovingPlayer() != null;
            boolean onGrate = world.getBlockState(entity.getBlockPos().add(0, -1, 0)).getBlock() instanceof GrateBlock;

            if (animal && !leashed && !onGrate) return getCachedShape(entity.stepHeight);
            else return TRUE_SHAPE;
        }

        return TRUE_SHAPE;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }
}
