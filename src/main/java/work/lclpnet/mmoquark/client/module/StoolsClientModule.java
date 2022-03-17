package work.lclpnet.mmoquark.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import work.lclpnet.mmocontent.client.entity.MMOClientEntities;
import work.lclpnet.mmoquark.client.render.entity.StoolRenderer;
import work.lclpnet.mmoquark.entity.StoolEntity;
import work.lclpnet.mmoquark.module.StoolsModule;

@Environment(EnvType.CLIENT)
public class StoolsClientModule implements IClientModule {

    @Override
    public void registerClient() {
        EntityRendererRegistry.register(StoolsModule.stoolEntity, StoolRenderer::new);
        MMOClientEntities.registerNonLiving(StoolsModule.stoolEntity, StoolEntity::new);
    }
}
