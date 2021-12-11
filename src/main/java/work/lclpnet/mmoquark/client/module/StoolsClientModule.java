package work.lclpnet.mmoquark.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import work.lclpnet.mmoquark.client.MMOClientEntities;
import work.lclpnet.mmoquark.client.render.entity.StoolRenderer;
import work.lclpnet.mmoquark.entity.StoolEntity;
import work.lclpnet.mmoquark.module.IClientModule;
import work.lclpnet.mmoquark.module.StoolsModule;

@Environment(EnvType.CLIENT)
public class StoolsClientModule implements IClientModule {

    @Override
    public void registerClient() {
        EntityRendererRegistry.INSTANCE.register(StoolsModule.stoolEntity, (manager, context) -> new StoolRenderer(manager));
        MMOClientEntities.registerNonLiving(StoolsModule.stoolEntity, StoolEntity::new);
    }
}
