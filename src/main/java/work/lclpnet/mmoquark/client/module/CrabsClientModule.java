package work.lclpnet.mmoquark.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import work.lclpnet.mmoquark.client.render.entity.CrabRenderer;
import work.lclpnet.mmoquark.module.CrabsModule;

@Environment(EnvType.CLIENT)
public class CrabsClientModule implements IClientModule {

    @Override
    public void registerClient() {
        EntityRendererRegistry.INSTANCE.register(CrabsModule.crabType, (manager, context) -> new CrabRenderer(manager));
    }
}
