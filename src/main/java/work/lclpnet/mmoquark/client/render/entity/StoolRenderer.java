package work.lclpnet.mmoquark.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import work.lclpnet.mmoquark.entity.StoolEntity;

@Environment(EnvType.CLIENT)
public class StoolRenderer extends EntityRenderer<StoolEntity> {

    public StoolRenderer(EntityRendererFactory.Context context) {
        super(context);
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
