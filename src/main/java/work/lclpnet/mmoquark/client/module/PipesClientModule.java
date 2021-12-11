package work.lclpnet.mmoquark.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.client.util.ModelIdentifier;
import work.lclpnet.mmocontent.client.render.MMOSpecialModels;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.client.render.blockentity.PipeBlockEntityRenderer;
import work.lclpnet.mmoquark.module.IClientModule;
import work.lclpnet.mmoquark.module.PipesModule;

@Environment(EnvType.CLIENT)
public class PipesClientModule implements IClientModule {

    @Override
    public void registerClient() {
        MMOSpecialModels.addSpecialModel(new ModelIdentifier(MMOQuark.identifier("pipe_flare"), "inventory"));
        BlockEntityRendererRegistry.INSTANCE.register(PipesModule.blockEntityType, PipeBlockEntityRenderer::new);
    }
}
