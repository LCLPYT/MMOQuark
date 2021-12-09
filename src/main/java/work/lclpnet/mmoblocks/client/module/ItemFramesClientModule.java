package work.lclpnet.mmoblocks.client.module;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ModelIdentifier;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.entity.GlassItemFrameEntity;
import work.lclpnet.mmoblocks.entity.MMOClientEntities;
import work.lclpnet.mmoblocks.entity.render.GlassItemFrameRenderer;
import work.lclpnet.mmoblocks.module.IClientModule;
import work.lclpnet.mmoblocks.module.ItemFramesModule;
import work.lclpnet.mmoblocks.util.MMOSpecialModels;

public class ItemFramesClientModule implements IClientModule {

    @Override
    public void registerClient() {
        MMOSpecialModels.addSpecialModel(new ModelIdentifier(MMOBlocks.identifier("glass_frame"), "inventory"));
        EntityRendererRegistry.INSTANCE.register(ItemFramesModule.glassFrameEntity,
                (manager, context) -> new GlassItemFrameRenderer(manager, MinecraftClient.getInstance().getItemRenderer()));
        MMOClientEntities.registerNonLiving(ItemFramesModule.glassFrameEntity,
                (type, world) -> new GlassItemFrameEntity(ItemFramesModule.glassFrameEntity, world));
    }
}
