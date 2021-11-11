package work.lclpnet.mmoblocks.entity.render;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.util.Identifier;
import work.lclpnet.mmoblocks.entity.StoolEntity;

public class StoolEntityRenderer extends EntityRenderer<StoolEntity> {

    public StoolEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public Identifier getTexture(StoolEntity entity) {
        return null;
    }

    @Override
    public boolean shouldRender(StoolEntity entity, Frustum frustum, double x, double y, double z) {
        return false;
    }
}
