package work.lclpnet.mmoquark.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import work.lclpnet.mmocontent.client.entity.MMOClientEntities;
import work.lclpnet.mmoquark.client.render.entity.PickarangRenderer;
import work.lclpnet.mmoquark.entity.PickarangEntity;
import work.lclpnet.mmoquark.module.PickarangModule;

@Environment(EnvType.CLIENT)
public class PickarangClientModule implements IClientModule {

    @Override
    public void registerClient() {
        MMOClientEntities.registerNonLiving(PickarangModule.pickarangType, (type, world) -> new PickarangEntity(PickarangModule.pickarangType, world));
        EntityRendererRegistry.register(PickarangModule.pickarangType, PickarangRenderer::new);
    }
}
