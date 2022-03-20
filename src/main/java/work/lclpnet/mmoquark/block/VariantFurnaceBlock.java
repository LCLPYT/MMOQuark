package work.lclpnet.mmoquark.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import work.lclpnet.mmocontent.block.ext.IMMOBlock;
import work.lclpnet.mmoquark.blockentity.VariantFurnaceBlockEntity;
import work.lclpnet.mmoquark.module.VariantFurnacesModule;

public class VariantFurnaceBlock extends FurnaceBlock implements IMMOBlock {

    public VariantFurnaceBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return new BlockItem(this, settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new VariantFurnaceBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(world, type, VariantFurnacesModule.blockEntityType);
    }

    @Override
    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockentity = world.getBlockEntity(pos);
        if (blockentity instanceof AbstractFurnaceBlockEntity) {
            player.openHandledScreen((NamedScreenHandlerFactory) blockentity);
            player.incrementStat(Stats.INTERACT_WITH_FURNACE);
        }
    }
}
