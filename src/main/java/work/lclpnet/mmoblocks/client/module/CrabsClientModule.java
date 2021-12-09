package work.lclpnet.mmoblocks.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import work.lclpnet.mmoblocks.entity.render.CrabRenderer;
import work.lclpnet.mmoblocks.module.CrabsModule;
import work.lclpnet.mmoblocks.module.IClientModule;

@Environment(EnvType.CLIENT)
public class CrabsClientModule implements IClientModule {

    @Override
    public void registerClient() {
        EntityRendererRegistry.INSTANCE.register(CrabsModule.crabType, (manager, context) -> new CrabRenderer(manager));
    }
}
