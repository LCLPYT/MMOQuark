package work.lclpnet.mmoquark.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import work.lclpnet.mmoquark.client.render.entity.StonelingRenderer;
import work.lclpnet.mmoquark.module.StonelingsModule;

@Environment(EnvType.CLIENT)
public class StonelingsClientModule implements IClientModule {

    @Override
    public void registerClient() {
        EntityRendererRegistry.INSTANCE.register(StonelingsModule.stonelingType, (manager, context) -> new StonelingRenderer(manager));
    }
}
