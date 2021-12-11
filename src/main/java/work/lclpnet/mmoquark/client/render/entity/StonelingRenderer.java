package work.lclpnet.mmoquark.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import work.lclpnet.mmoquark.client.render.entity.layer.StonelingItemLayer;
import work.lclpnet.mmoquark.client.render.entity.model.StonelingModel;
import work.lclpnet.mmoquark.entity.StonelingEntity;

@Environment(EnvType.CLIENT)
public class StonelingRenderer extends MobEntityRenderer<StonelingEntity, StonelingModel> {

    public StonelingRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new StonelingModel(), 0.3F);
        addFeature(new StonelingItemLayer(this));
    }

    @Override
    public Identifier getTexture(StonelingEntity entity) {
        return entity.getVariant().getTexture();
    }
}
