package work.lclpnet.mmoquark.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.client.render.entity.StonelingRenderer;
import work.lclpnet.mmoquark.client.render.entity.model.StonelingModel;
import work.lclpnet.mmoquark.module.StonelingsModule;

@Environment(EnvType.CLIENT)
public class StonelingsClientModule implements IClientModule {

    public static final EntityModelLayer STONELING_LAYER = new EntityModelLayer(MMOQuark.identifier("stoneling"), "main");

    @Override
    public void registerClient() {
        EntityRendererRegistry.register(StonelingsModule.stonelingType, StonelingRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(STONELING_LAYER, StonelingModel::createBodyLayer);
    }
}
