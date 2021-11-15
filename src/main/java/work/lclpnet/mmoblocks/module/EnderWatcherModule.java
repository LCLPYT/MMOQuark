package work.lclpnet.mmoblocks.module;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.block.EnderWatcherBlock;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.blockentity.EnderWatcherBlockEntity;

public class EnderWatcherModule implements IModule {

    public static BlockEntityType<EnderWatcherBlockEntity> enderWatcherType;

    @Override
    public void register() {
        Block enderWatcher = new EnderWatcherBlock();
        new MMOBlockRegistrar(enderWatcher)
                .register("ender_watcher", ItemGroup.REDSTONE);

        enderWatcherType = Registry.register(Registry.BLOCK_ENTITY_TYPE, MMOBlocks.identifier("ender_watcher"),
                BlockEntityType.Builder.create(EnderWatcherBlockEntity::new, enderWatcher).build(null));
    }
}
