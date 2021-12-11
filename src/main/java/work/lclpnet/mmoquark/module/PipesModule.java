package work.lclpnet.mmoquark.module;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.PipeBlock;
import work.lclpnet.mmoquark.blockentity.PipeBlockEntity;

public class PipesModule implements IModule {

    public static BlockEntityType<PipeBlockEntity> blockEntityType;

    @Override
    public void register() {
        PipeBlock pipeBlock = new PipeBlock();
        new MMOBlockRegistrar(pipeBlock)
                .register(MMOQuark.identifier("pipe"), ItemGroup.DECORATIONS);

        blockEntityType = Registry.register(Registry.BLOCK_ENTITY_TYPE, MMOQuark.identifier("pipe"),
                BlockEntityType.Builder.create(PipeBlockEntity::new, pipeBlock).build(null));
    }
}
