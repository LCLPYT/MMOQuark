package work.lclpnet.mmoquark.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.client.render.entity.CrabRenderer;
import work.lclpnet.mmoquark.client.render.entity.model.CrabModel;
import work.lclpnet.mmoquark.module.CrabsModule;

@Environment(EnvType.CLIENT)
public class CrabsClientModule implements IClientModule {

    public static final EntityModelLayer CRAB_LAYER = new EntityModelLayer(MMOQuark.identifier("crab"), "main");

    @Override
    public void registerClient() {
        EntityRendererRegistry.register(CrabsModule.crabType, CrabRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(CRAB_LAYER, CrabModel::createBodyLayer);
    }
}
