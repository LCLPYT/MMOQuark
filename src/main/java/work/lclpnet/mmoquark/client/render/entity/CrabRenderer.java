package work.lclpnet.mmoquark.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.client.module.CrabsClientModule;
import work.lclpnet.mmoquark.client.render.entity.model.CrabModel;
import work.lclpnet.mmoquark.entity.CrabEntity;

@Environment(EnvType.CLIENT)
public class CrabRenderer extends MobEntityRenderer<CrabEntity, CrabModel> {

    private static final Identifier[] TEXTURES = new Identifier[] {
            MMOQuark.identifier("textures/model/entity/crab/red.png"),
            MMOQuark.identifier("textures/model/entity/crab/blue.png"),
            MMOQuark.identifier("textures/model/entity/crab/green.png")
    };

    public CrabRenderer(EntityRendererFactory.Context context) {
        super(context, new CrabModel(context.getPart(CrabsClientModule.CRAB_LAYER)), 0.4F);
    }

    @Override
    public Identifier getTexture(CrabEntity entity) {
        return TEXTURES[Math.min(TEXTURES.length - 1, entity.getVariant())];
    }
}
