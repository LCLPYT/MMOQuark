package work.lclpnet.mmoquark.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import work.lclpnet.mmoquark.module.VariantChestsModule;

public class VariantChestBlockEntity extends ChestBlockEntity {

    public VariantChestBlockEntity(BlockEntityType<?> type, BlockPos blockPos, BlockState blockState) {
        super(type, blockPos, blockState);
    }

    public VariantChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(VariantChestsModule.VARIANT_CHEST_ENTITY, blockPos, blockState);
    }
}
