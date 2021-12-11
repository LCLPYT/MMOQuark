package work.lclpnet.mmoquark.blockentity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import work.lclpnet.mmoquark.module.VariantChestsModule;

public class VariantChestBlockEntity extends ChestBlockEntity {

    public VariantChestBlockEntity(BlockEntityType<?> type) {
        super(type);
    }

    public VariantChestBlockEntity() {
        super(VariantChestsModule.VARIANT_CHEST_ENTITY);
    }
}
