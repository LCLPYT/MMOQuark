package work.lclpnet.mmoblocks.blockentity;

import work.lclpnet.mmoblocks.module.WoodExtraModule;

public class VariantTrappedChestBlockEntity extends VariantChestBlockEntity {

    public VariantTrappedChestBlockEntity() {
        super(WoodExtraModule.VARIANT_TRAPPED_CHEST_ENTITY);
    }

    @Override
    protected void onInvOpenOrClose() {
        super.onInvOpenOrClose();
        if (this.world != null) this.world.updateNeighborsAlways(this.pos.down(), this.getCachedState().getBlock());
    }
}
