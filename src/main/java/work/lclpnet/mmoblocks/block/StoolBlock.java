package work.lclpnet.mmoblocks.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.mmoblocks.block.ext.MMOBlock;
import work.lclpnet.mmoblocks.entity.StoolEntity;
import work.lclpnet.mmoblocks.module.StoolsModule;

import java.util.Random;

public class StoolBlock extends MMOBlock implements Waterloggable {

    private static final VoxelShape SHAPE_TOP = createCuboidShape(0F, 1F, 0F, 16F, 9F, 16F);
    private static final VoxelShape SHAPE_LEG = createCuboidShape(0F, 0F, 0F, 4F, 1F, 4F);

    private static final VoxelShape SHAPE_TOP_BIG = createCuboidShape(0F, 8F, 0F, 16F, 16F, 16F);
    private static final VoxelShape SHAPE_LEG_BIG = createCuboidShape(0F, 0F, 0F, 4F, 8F, 4F);

    private static final VoxelShape SHAPE = VoxelShapes.union(SHAPE_TOP, SHAPE_LEG,
            SHAPE_LEG.offset(0.75F, 0F, 0F),
            SHAPE_LEG.offset(0.75F, 0F, 0.75F),
            SHAPE_LEG.offset(0F, 0F, 0.75F));

    private static final VoxelShape SHAPE_BIG = VoxelShapes.union(SHAPE_TOP_BIG, SHAPE_LEG_BIG,
            SHAPE_LEG_BIG.offset(0.75F, 0F, 0F),
            SHAPE_LEG_BIG.offset(0.75F, 0F, 0.75F),
            SHAPE_LEG_BIG.offset(0F, 0F, 0.75F));

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty BIG = BooleanProperty.of("big");
    public static final BooleanProperty SAT_IN = BooleanProperty.of("sat_in");

    public StoolBlock(DyeColor color) {
        super(Settings.of(Material.WOOL, color.getMaterialColor())
                .sounds(BlockSoundGroup.WOOD)
                .strength(0.2F, 0.2F)
                .nonOpaque());

        setDefaultState(getDefaultState()
                .with(WATERLOGGED, false)
                .with(BIG, false)
                .with(SAT_IN, false)
        );
    }

    public void blockClicked(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (!state.get(BIG)) {
            world.setBlockState(pos, state.with(BIG, true));
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        fixState(world, pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(SAT_IN) || !world.getBlockState(pos.up()).isAir() || player.getVehicle() != null)
            return super.onUse(state, world, pos, player, hand, hit);

        if (!world.isClient) {
            StoolEntity entity = new StoolEntity(StoolsModule.stoolEntity, world);
            entity.updatePosition(pos.getX() + 0.5, pos.getY() + 0.6, pos.getZ() + 0.5);

            world.spawnEntity(entity);
            player.startRiding(entity);

            world.setBlockState(pos, state.with(SAT_IN, true));
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
        super.onLandedUpon(world, pos, entity, distance * 0.5F);
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        if (entity.bypassesLandingEffects()) super.onEntityLand(world, entity);
        else this.bounceEntity(entity);
    }

    private void bounceEntity(Entity entity) {
        Vec3d vector3d = entity.getVelocity();
        if (vector3d.y < 0.0D) {
            double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setVelocity(vector3d.x, -vector3d.y * (double)0.66F * d0, vector3d.z);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(BIG) ? SHAPE_BIG : SHAPE;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getStateFor(ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        fixState(world, pos, state);
    }

    private void fixState(World worldIn, BlockPos pos, BlockState state) {
        BlockState target = getStateFor(worldIn, pos);
        if(!target.equals(state))
            worldIn.setBlockState(pos, target);
    }

    private BlockState getStateFor(World world, BlockPos pos) {
        return getDefaultState()
                .with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER)
                .with(BIG, world.getBlockState(pos.up()).getOutlineShape(world, pos.up()).getMin(Direction.Axis.Y) == 0)
                .with(SAT_IN, world.getEntitiesByClass(StoolEntity.class, new Box(pos, pos.up()).expand(0.4), e -> e.getBlockPos().equals(pos)).size() > 0);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(SAT_IN) ? 15 : 0;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, BIG, SAT_IN);
    }
}
