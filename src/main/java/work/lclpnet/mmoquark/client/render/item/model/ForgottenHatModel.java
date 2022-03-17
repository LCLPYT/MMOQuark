package work.lclpnet.mmoquark.client.render.item.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;

@Environment(EnvType.CLIENT)
public class ForgottenHatModel {

    public static TexturedModelData createBodyLayer() {
        return QArmorModel.createLayer(64, 64, root -> {
            ModelPartData head = root.addChild("head", ModelPartBuilder.create(), ModelTransform.NONE);

            head.addChild("piece1",
                    ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(-4.0F, -10.0F, -4.0F, 8, 10, 8, new Dilation(0.6F)),
                    ModelTransform.NONE);

            head.addChild("piece2",
                    ModelPartBuilder.create()
                            .uv(0, 18)
                            .cuboid(-6.0F, -6.0F, -6.0F, 12, 1, 12),
                    ModelTransform.NONE);
        });
    }
}
