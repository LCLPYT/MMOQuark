package work.lclpnet.mmoblocks.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import work.lclpnet.mmoblocks.client.render.entity.StonelingRenderer;
import work.lclpnet.mmoblocks.module.IClientModule;
import work.lclpnet.mmoblocks.module.StonelingsModule;

@Environment(EnvType.CLIENT)
public class StonelingsClientModule implements IClientModule {

    @Override
    public void registerClient() {
        EntityRendererRegistry.INSTANCE.register(StonelingsModule.stonelingType, (manager, context) -> new StonelingRenderer(manager));
    }
}
