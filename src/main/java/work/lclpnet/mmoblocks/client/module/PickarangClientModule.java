package work.lclpnet.mmoblocks.client.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import work.lclpnet.mmoblocks.entity.MMOClientEntities;
import work.lclpnet.mmoblocks.entity.PickarangEntity;
import work.lclpnet.mmoblocks.entity.render.PickarangRenderer;
import work.lclpnet.mmoblocks.module.IClientModule;
import work.lclpnet.mmoblocks.module.PickarangModule;

@Environment(EnvType.CLIENT)
public class PickarangClientModule implements IClientModule {

    @Override
    public void registerClient() {
        MMOClientEntities.registerNonLiving(PickarangModule.pickarangType, (type, world) -> new PickarangEntity(PickarangModule.pickarangType, world));
        EntityRendererRegistry.INSTANCE.register(PickarangModule.pickarangType, (manager, context) -> new PickarangRenderer(manager));
    }
}
