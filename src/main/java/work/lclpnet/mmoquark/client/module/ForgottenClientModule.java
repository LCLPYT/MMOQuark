package work.lclpnet.mmoquark.client.module;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.client.render.item.model.ForgottenHatModel;

public class ForgottenClientModule implements IClientModule {

    public static final EntityModelLayer HAT_LAYER = new EntityModelLayer(MMOQuark.identifier("forgotten_hat"), "main");

    @Override
    public void registerClient() {
        EntityModelLayerRegistry.registerModelLayer(HAT_LAYER, ForgottenHatModel::createBodyLayer);
    }
}
