package work.lclpnet.mmoquark.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import work.lclpnet.mmocontent.block.ext.IMMOBlock;
import work.lclpnet.mmocontent.client.item.MMOBlockEntityItem;
import work.lclpnet.mmocontent.util.Env;
import work.lclpnet.mmoquark.blockentity.VariantChestBlockEntity;
import work.lclpnet.mmoquark.client.render.blockentity.VariantChestBlockEntityRenderer;

import java.util.function.Supplier;

public class VariantChestBlock extends ChestBlock implements BlockEntityProvider, IMMOBlock {

    @Environment(EnvType.CLIENT)
    protected VariantChestBlockEntity displayBlockEntity = null;

    public VariantChestBlock(Settings settings, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(settings, supplier);

        if (Env.isClient())
            this.displayBlockEntity = new VariantChestBlockEntity(BlockPos.ORIGIN, getDefaultState());
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new VariantChestBlockEntity(pos, state);
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> list) {
        super.appendStacks(group, list);
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return Env.isClient() ? getClientBlockItem(settings) : new BlockItem(this, settings);
    }

    @Environment(EnvType.CLIENT)
    public BlockItem getClientBlockItem(Item.Settings settings) {
        return new MMOBlockEntityItem(this, settings, () -> displayBlockEntity, x -> VariantChestBlockEntityRenderer.invBlock = x);
    }

    public static class Item extends BlockItem {

        public Item(Block block, Settings settings) {
            super(block, settings);
        }


    }
}
