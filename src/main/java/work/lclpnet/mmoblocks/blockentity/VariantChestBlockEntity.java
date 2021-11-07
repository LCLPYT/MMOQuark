package work.lclpnet.mmoblocks.blockentity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import work.lclpnet.mmoblocks.module.VariantChestsModule;

public class VariantChestBlockEntity extends ChestBlockEntity {

    public VariantChestBlockEntity(BlockEntityType<?> type) {
        super(type);
    }

    public VariantChestBlockEntity() {
        super(VariantChestsModule.VARIANT_CHEST_ENTITY);
    }
}
