package work.lclpnet.mmoblocks.item.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.EquipmentSlot;

@Environment(EnvType.CLIENT)
public class ForgottenHatModel extends BaseArmorModel {

    public ForgottenHatModel() {
        super(EquipmentSlot.HEAD);
        ModelPart base = new ModelPart(this);

        textureHeight = 64;
        textureWidth = 64;

        ModelPart piece1 = new ModelPart(this, 0, 0);
        piece1.addCuboid(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.6F);

        ModelPart piece2 = new ModelPart(this, 0, 18);
        piece2.addCuboid(-6.0F, -6.0F, -6.0F, 12, 1, 12, 0.0F);

        base.addChild(piece1);
        base.addChild(piece2);

        head = base;
        helmet = base;
    }
}
