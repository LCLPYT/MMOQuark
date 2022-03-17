package work.lclpnet.mmoquark.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import work.lclpnet.mmoquark.entity.StonelingEntity;

@Environment(EnvType.CLIENT)
public class StonelingModel extends EntityModel<StonelingEntity> {

    private final ModelPart body;
    private final ModelPart arm_right;
    private final ModelPart arm_left;
    private final ModelPart leg_right;
    private final ModelPart leg_left;

    public StonelingModel(ModelPart root) {
        body = root.getChild("body");
        arm_right = root.getChild("arm_right");
        arm_left = root.getChild("arm_left");
        leg_right = root.getChild("leg_right");
        leg_left = root.getChild("leg_left");
    }

    // Made with Blockbench 4.1.5
    public static TexturedModelData createBodyLayer() {
        ModelData mesh = new ModelData();
        ModelPartData root = mesh.getRoot();

        root.addChild("body", ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4.0F, -9.0F, -3.0F, 8.0F, 9.0F, 7.0F, new Dilation(0.0F))
                        .uv(36, 13).cuboid(-4.0F, -2.0F, -4.0F, 8.0F, 2.0F, 1.0F, new Dilation(0.0F))
                        .uv(44, 7).cuboid(-4.0F, -9.0F, -5.0F, 8.0F, 4.0F, 2.0F, new Dilation(0.0F))
                        .uv(23, 0).cuboid(-2.0F, -12.0F, -1.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F))
                        .uv(30, 7).cuboid(-2.0F, -9.0F, -6.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
                        .uv(25, 24).cuboid(-2.0F, -12.0F, -5.0F, 4.0F, 6.0F, 3.0F, new Dilation(0.0F))
                        .uv(36, 17).cuboid(-2.0F, -11.0F, 3.0F, 4.0F, 5.0F, 3.0F, new Dilation(0.0F))
                        .uv(0, 27).cuboid(-2.0F, -2.0F, -4.0F, 4.0F, 5.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 21.0F, 0.0F));

//        body.addChild("lychen", ModelPartBuilder.create()
//                        .uv(10, 12).cuboid(0.0F, -4.0F, -2.0F, 0.0F, 4.0F, 4.0F, new Dilation(0.0F))
//                        .uv(10, 16).cuboid(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 0.0F, new Dilation(0.0F)),
//                ModelTransform.of(3.0F, -9.0F, 3.0F, 0.0F, 0.7854F, 0.0F));
//
//        body.addChild("dripstone", ModelPartBuilder.create()
//                        .uv(14, 16).cuboid(0.0F, -5.0F, -3.0F, 0.0F, 5.0F, 6.0F, new Dilation(0.0F))
//                        .uv(14, 22).cuboid(-3.0F, -5.0F, 0.0F, 6.0F, 5.0F, 0.0F, new Dilation(0.0F)),
//                ModelTransform.of(0.0F, -9.0F, 1.0F, 0.0F, 0.7854F, 0.0F));

        root.addChild("leg_left", ModelPartBuilder.create()
                        .uv(27, 13).cuboid(-1.5F, 1.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)),
                ModelTransform.pivot(2.25F, 19.0F, 0.5F));

        root.addChild("leg_right", ModelPartBuilder.create()
                        .uv(27, 13).mirrored().cuboid(-1.5F, 1.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.pivot(-2.25F, 19.0F, 0.5F));

        root.addChild("arm_right", ModelPartBuilder.create()
                        .uv(0, 16).cuboid(-3.0F, 0.0F, -2.0F, 3.0F, 7.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-4.0F, 15.0F, 0.5F));

        root.addChild("arm_left", ModelPartBuilder.create()
                        .uv(0, 16).mirrored().cuboid(0.0F, 0.0F, -2.0F, 3.0F, 7.0F, 4.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.pivot(4.0F, 15.0F, 0.5F));

        return TexturedModelData.of(mesh, 64, 64);
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
        arm_right.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        arm_left.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        leg_right.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        leg_left.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
