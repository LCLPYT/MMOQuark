package work.lclpnet.mmoquark.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import work.lclpnet.mmoquark.module.VariantChestsModule;

public class VariantTrappedChestBlockEntity extends VariantChestBlockEntity {

    public VariantTrappedChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(VariantChestsModule.VARIANT_TRAPPED_CHEST_ENTITY, blockPos, blockState);
    }

    @Override
    protected void onInvOpenOrClose(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        super.onInvOpenOrClose(world, pos, state, oldViewerCount, newViewerCount);

        if (this.world != null)
            this.world.updateNeighborsAlways(this.pos.down(), this.getCachedState().getBlock());
    }
}
