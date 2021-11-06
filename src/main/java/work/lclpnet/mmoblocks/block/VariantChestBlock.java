package work.lclpnet.mmoblocks.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.BlockView;
import work.lclpnet.mmoblocks.block.ext.IMMOBlock;
import work.lclpnet.mmoblocks.blockentity.MMOItemBlockEntities;
import work.lclpnet.mmoblocks.blockentity.VariantChestBlockEntity;
import work.lclpnet.mmoblocks.blockentity.renderer.VariantChestBlockEntityRenderer;
import work.lclpnet.mmoblocks.item.MMOBlockEntityItem;
import work.lclpnet.mmoblocks.module.WoodExtraModule;
import work.lclpnet.mmoblocks.util.Env;

import java.util.function.Supplier;

public class VariantChestBlock extends ChestBlock implements BlockEntityProvider, WoodExtraModule.IChestTextureProvider, IMMOBlock {

    protected String path;

    public VariantChestBlock(Settings settings, String type, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(settings, supplier);

        this.path = type;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new VariantChestBlockEntity();
    }

    @Override
    public void addStacksForDisplay(ItemGroup group, DefaultedList<ItemStack> list) {
        super.addStacksForDisplay(group, list);
    }

    @Override
    public String getChestTexturePath() {
        return String.format("model/chest/%s/", path);
    }

    @Override
    public boolean isTrap() {
        return false;
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return Env.isClient() ? getClientBlockItem(settings) : new BlockItem(this, settings);
    }

    @Environment(EnvType.CLIENT)
    public BlockItem getClientBlockItem(Item.Settings settings) {
        return new MMOBlockEntityItem(this, settings, () -> MMOItemBlockEntities.variantChest, x -> VariantChestBlockEntityRenderer.invBlock = x);
    }
}
