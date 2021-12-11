package work.lclpnet.mmoquark.client.module;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ModelIdentifier;
import work.lclpnet.mmocontent.client.entity.MMOClientEntities;
import work.lclpnet.mmocontent.client.render.MMOSpecialModels;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.client.render.entity.GlassItemFrameRenderer;
import work.lclpnet.mmoquark.entity.GlassItemFrameEntity;
import work.lclpnet.mmoquark.module.IClientModule;
import work.lclpnet.mmoquark.module.ItemFramesModule;

public class ItemFramesClientModule implements IClientModule {

    @Override
    public void registerClient() {
        MMOSpecialModels.addSpecialModel(new ModelIdentifier(MMOQuark.identifier("glass_frame"), "inventory"));
        EntityRendererRegistry.INSTANCE.register(ItemFramesModule.glassFrameEntity,
                (manager, context) -> new GlassItemFrameRenderer(manager, MinecraftClient.getInstance().getItemRenderer()));
        MMOClientEntities.registerNonLiving(ItemFramesModule.glassFrameEntity,
                (type, world) -> new GlassItemFrameEntity(ItemFramesModule.glassFrameEntity, world));
    }
}
