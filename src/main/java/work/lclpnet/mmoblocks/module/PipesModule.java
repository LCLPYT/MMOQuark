package work.lclpnet.mmoblocks.module;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.PipeBlock;
import work.lclpnet.mmoblocks.blockentity.PipeBlockEntity;

public class PipesModule implements IModule {

    public static BlockEntityType<PipeBlockEntity> blockEntityType;

    @Override
    public void register() {
        PipeBlock pipeBlock = new PipeBlock();
        new MMOBlockRegistrar(pipeBlock)
                .register("pipe", ItemGroup.DECORATIONS);

        blockEntityType = Registry.register(Registry.BLOCK_ENTITY_TYPE, MMOBlocks.identifier("pipe"),
                BlockEntityType.Builder.create(PipeBlockEntity::new, pipeBlock).build(null));
    }
}
