package work.lclpnet.mmoblocks.entity.render;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.entity.CrabEntity;
import work.lclpnet.mmoblocks.entity.render.model.CrabModel;

public class CrabRenderer extends MobEntityRenderer<CrabEntity, CrabModel> {

    private static final Identifier[] TEXTURES = new Identifier[] {
            MMOBlocks.identifier("textures/model/entity/crab/red.png"),
            MMOBlocks.identifier("textures/model/entity/crab/blue.png"),
            MMOBlocks.identifier("textures/model/entity/crab/green.png")
    };

    public CrabRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new CrabModel(), 0.4F);
    }

    @Override
    public Identifier getTexture(CrabEntity entity) {
        return TEXTURES[Math.min(TEXTURES.length - 1, entity.getVariant())];
    }
}
