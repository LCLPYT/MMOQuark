package work.lclpnet.mmoblocks.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.PipeBlock;
import work.lclpnet.mmoblocks.blockentity.PipeBlockEntity;
import work.lclpnet.mmoblocks.blockentity.renderer.PipeBlockEntityRenderer;
import work.lclpnet.mmoblocks.util.MMOSpecialModels;

public class PipesModule implements IModule, IClientModule {

    public static BlockEntityType<PipeBlockEntity> blockEntityType;

    @Override
    public void register() {
        PipeBlock pipeBlock = new PipeBlock();
        new MMOBlockRegistrar(pipeBlock)
                .register("pipe", ItemGroup.DECORATIONS);

        blockEntityType = Registry.register(Registry.BLOCK_ENTITY_TYPE, MMOBlocks.identifier("pipe"),
                BlockEntityType.Builder.create(PipeBlockEntity::new, pipeBlock).build(null));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void registerClient() {
        MMOSpecialModels.addSpecialModel(new ModelIdentifier(MMOBlocks.identifier("pipe_flare"), "inventory"));
        BlockEntityRendererRegistry.INSTANCE.register(blockEntityType, PipeBlockEntityRenderer::new);
    }
}
