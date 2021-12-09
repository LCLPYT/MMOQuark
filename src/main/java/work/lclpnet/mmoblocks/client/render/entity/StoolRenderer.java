package work.lclpnet.mmoblocks.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.util.Identifier;
import work.lclpnet.mmoblocks.entity.StoolEntity;

@Environment(EnvType.CLIENT)
public class StoolRenderer extends EntityRenderer<StoolEntity> {

    public StoolRenderer(EntityRenderDispatcher dispatcher) {
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
