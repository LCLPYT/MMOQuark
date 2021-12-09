package work.lclpnet.mmoblocks.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import work.lclpnet.mmoblocks.entity.StonelingEntity;

@Environment(EnvType.CLIENT)
public class StonelingModel extends EntityModel<StonelingEntity> {

    private final ModelPart body;
    private final ModelPart arm_right;
    private final ModelPart arm_left;
    private final ModelPart leg_right;
    private final ModelPart leg_left;

    public StonelingModel() {
        textureWidth = 32;
        textureHeight = 32;

        body = new ModelPart(this);
        body.setPivot(0.0F, 14.0F, 0.0F);

        ModelPart head = new ModelPart(this);
        head.setPivot(0.0F, 0.0F, 0.0F);
        body.addChild(head);

        head.addCuboid(null, -3.0F, -2.0F, -3.0F, 6, 8, 6, 0.0F, 0, 0);
        head.addCuboid(null, -1.0F, -4.0F, -5.0F, 2, 4, 2, 0.0F, 8, 24);
        head.addCuboid(null, -1.0F, 6.0F, -3.0F, 2, 2, 2, 0.0F, 16, 20);
        head.addCuboid(null, -1.0F, -4.0F, 3.0F, 2, 4, 2, 0.0F, 0, 24);
        head.addCuboid(null, -1.0F, -4.0F, -3.0F, 2, 2, 6, 0.0F, 16, 24);
        head.addCuboid(null, -1.0F, -4.0F, -1.0F, 2, 2, 2, 0.0F, 24, 20);
        head.addCuboid(null, -1.0F, 1.0F, -5.0F, 2, 2, 2, 0.0F, 18, 0);
        head.addCuboid(null, -4.0F, -1.0F, -3.0F, 1, 2, 2, 0.0F, 0, 0);
        head.addCuboid(null, 3.0F, -1.0F, -3.0F, 1, 2, 2, 0.0F, 0, 0);

        arm_right = new ModelPart(this);
        arm_right.setPivot(-3.0F, 2.0F, 0.0F);
        setRotationAngle(arm_right, 3.1416F, 0.0F, 0.0F);
        body.addChild(arm_right);
        arm_right.addCuboid(null, -2.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, 0, 14);

        arm_left = new ModelPart(this);
        arm_left.setPivot(3.0F, 2.0F, 0.0F);
        setRotationAngle(arm_left, 3.1416F, 0.0F, 0.0F);
        body.addChild(arm_left);
        arm_left.addCuboid(null, 0.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, 8, 14);

        leg_right = new ModelPart(this);
        leg_right.setPivot(-2.0F, 4.0F, 0.0F);
        body.addChild(leg_right);
        leg_right.addCuboid(null, -1.0F, 2.0F, -1.0F, 2, 4, 2, 0.0F, 16, 14);

        leg_left = new ModelPart(this);
        leg_left.setPivot(1.0F, 4.0F, 0.0F);
        body.addChild(leg_left);
        leg_left.addCuboid(null, 0.0F, 2.0F, -1.0F, 2, 4, 2, 0.0F, 24, 14);
    }

    @Override
    public void setAngles(StonelingEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        leg_right.pitch = MathHelper.cos(limbAngle * 0.6662F) * limbDistance;
        leg_left.pitch = MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI) * limbDistance;

        ItemStack carry = entity.getCarryingItem();
        if (carry.isEmpty() && !entity.hasPassengers()) {
            arm_right.pitch = 0F;
            arm_left.pitch = 0F;
        } else {
            arm_right.pitch = 3.1416F;
            arm_left.pitch = 3.1416F;
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        body.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }
}
