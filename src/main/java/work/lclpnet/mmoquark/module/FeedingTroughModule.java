package work.lclpnet.mmoquark.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.FeedingTroughBlock;
import work.lclpnet.mmoquark.blockentity.FeedingTroughBlockEntity;

public class FeedingTroughModule implements IModule {

    public static BlockEntityType<FeedingTroughBlockEntity> tileEntityType;

    @Override
    public void register() {
        Block feedingTrough = new FeedingTroughBlock(AbstractBlock.Settings.of(Material.WOOD)
                .strength(0.6F, 0.6F)
                .sounds(BlockSoundGroup.WOOD));

        new MMOBlockRegistrar(feedingTrough)
                .register(MMOQuark.identifier("feeding_trough"), ItemGroup.DECORATIONS);

        tileEntityType = Registry.register(Registry.BLOCK_ENTITY_TYPE, MMOQuark.identifier("feeding_trough"),
                BlockEntityType.Builder.create(FeedingTroughBlockEntity::new, feedingTrough).build(null));
    }
}
