package work.lclpnet.mmoquark.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import work.lclpnet.mmoquark.module.VariantFurnacesModule;

public class VariantFurnaceBlockEntity extends AbstractFurnaceBlockEntity {

    public VariantFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(VariantFurnacesModule.blockEntityType, pos, state, RecipeType.SMELTING);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.furnace");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}
