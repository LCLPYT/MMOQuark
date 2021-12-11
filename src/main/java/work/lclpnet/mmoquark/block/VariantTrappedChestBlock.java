package work.lclpnet.mmoquark.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import work.lclpnet.mmocontent.client.item.MMOBlockEntityItem;
import work.lclpnet.mmoquark.client.module.VariantChestsClientModule;
import work.lclpnet.mmoquark.client.render.blockentity.VariantChestBlockEntityRenderer;

import java.util.function.Supplier;

public class VariantTrappedChestBlock extends VariantChestBlock {

    public VariantTrappedChestBlock(Settings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(settings, supplier);
    }

    @Environment(EnvType.CLIENT)
    public BlockItem getClientBlockItem(Item.Settings settings) {
        return new MMOBlockEntityItem(this, settings, () -> VariantChestsClientModule.variantChest, x -> VariantChestBlockEntityRenderer.invBlock = x);
    }

    @Override
    protected Stat<Identifier> getOpenStat() {
        return Stats.CUSTOM.getOrCreateStat(Stats.TRIGGER_TRAPPED_CHEST);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return MathHelper.clamp(ChestBlockEntity.getPlayersLookingInChestCount(world, pos), 0, 15);
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return direction == Direction.UP ? state.getWeakRedstonePower(world, pos, direction) : 0;
    }
}
