package work.lclpnet.mmoblocks.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import work.lclpnet.mmoblocks.entity.MMOClientEntities;
import work.lclpnet.mmoblocks.entity.StoolEntity;
import work.lclpnet.mmoblocks.entity.render.StoolRenderer;
import work.lclpnet.mmoblocks.module.IClientModule;
import work.lclpnet.mmoblocks.module.StoolsModule;

@Environment(EnvType.CLIENT)
public class StoolsClientModule implements IClientModule {

    @Override
    public void registerClient() {
        EntityRendererRegistry.INSTANCE.register(StoolsModule.stoolEntity, (manager, context) -> new StoolRenderer(manager));
        MMOClientEntities.registerNonLiving(StoolsModule.stoolEntity, StoolEntity::new);
    }
}
