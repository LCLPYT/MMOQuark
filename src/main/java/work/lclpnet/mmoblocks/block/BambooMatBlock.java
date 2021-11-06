package work.lclpnet.mmoblocks.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.mmoblocks.block.ext.MMOBlock;

public class BambooMatBlock extends MMOBlock {

    public static final EnumProperty<Direction> FACING = Properties.HOPPER_FACING;

    public BambooMatBlock() {
        super(Settings.of(Material.BAMBOO, MaterialColor.YELLOW)
                .strength(0.5F, 0.5F)
                .sounds(BlockSoundGroup.BAMBOO));

        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction dir = ctx.getPlayerFacing();
        PlayerEntity player = ctx.getPlayer();
        if (player == null) throw new IllegalStateException("Player might not be null");

        if(player.pitch > 70) dir = Direction.DOWN;

        if (dir != Direction.DOWN) {
            Direction opposite = dir.getOpposite();
            BlockPos target = ctx.getBlockPos().offset(opposite);
            BlockState state = ctx.getWorld().getBlockState(target);

            if(state.getBlock() != this || state.get(FACING) != opposite) {
                target = ctx.getBlockPos().offset(dir);
                state = ctx.getWorld().getBlockState(target);

                if(state.getBlock() == this && state.get(FACING) == dir) dir = opposite;
            }
        }

        return getDefaultState().with(FACING, dir);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
