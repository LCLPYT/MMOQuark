package work.lclpnet.mmoblocks.entity.render;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import work.lclpnet.mmoblocks.entity.StonelingEntity;
import work.lclpnet.mmoblocks.entity.render.layer.StonelingItemLayer;
import work.lclpnet.mmoblocks.entity.render.model.StonelingModel;

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
