package work.lclpnet.mmoblocks.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.blockentity.FeedingTroughBlockEntity;
import work.lclpnet.mmoblocks.module.IModule;

public class FeedingTroughModule implements IModule {

    public static BlockEntityType<FeedingTroughBlockEntity> tileEntityType;

    @Override
    public void register() {
        Block feedingTrough = new FeedingTroughBlock(AbstractBlock.Settings.of(Material.WOOD)
                .strength(0.6F, 0.6F)
                .sounds(BlockSoundGroup.WOOD));

        new MMOBlockRegistrar(feedingTrough)
                .register("feeding_trough", ItemGroup.DECORATIONS);

        tileEntityType = Registry.register(Registry.BLOCK_ENTITY_TYPE, MMOBlocks.identifier("feeding_trough"),
                BlockEntityType.Builder.create(FeedingTroughBlockEntity::new, feedingTrough).build(null));
    }
}
