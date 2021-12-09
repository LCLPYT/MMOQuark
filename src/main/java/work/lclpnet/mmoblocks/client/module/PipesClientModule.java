package work.lclpnet.mmoblocks.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.client.util.ModelIdentifier;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.client.render.blockentity.PipeBlockEntityRenderer;
import work.lclpnet.mmoblocks.module.IClientModule;
import work.lclpnet.mmoblocks.module.PipesModule;
import work.lclpnet.mmoblocks.util.MMOSpecialModels;

@Environment(EnvType.CLIENT)
public class PipesClientModule implements IClientModule {

    @Override
    public void registerClient() {
        MMOSpecialModels.addSpecialModel(new ModelIdentifier(MMOBlocks.identifier("pipe_flare"), "inventory"));
        BlockEntityRendererRegistry.INSTANCE.register(PipesModule.blockEntityType, PipeBlockEntityRenderer::new);
    }
}
