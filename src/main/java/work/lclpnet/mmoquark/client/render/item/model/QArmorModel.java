package work.lclpnet.mmoquark.client.render.item.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class QArmorModel extends BipedEntityModel<LivingEntity> {

    protected final EquipmentSlot slot;

    public QArmorModel(ModelPart root, EquipmentSlot slot) {
        super(root);
        this.slot = slot;
    }

    public static TexturedModelData createLayer(int textureWidth, int textureHeight, Consumer<ModelPartData> rootConsumer) {
        ModelData mesh = new ModelData();
        ModelPartData root = mesh.getRoot();

        root.addChild("head", ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild("hat", ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild("body", ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild("left_arm", ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild("right_arm", ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild("left_leg", ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.NONE);

        rootConsumer.accept(root);

        return TexturedModelData.of(mesh, textureWidth, textureHeight);
    }

    // [VanillaCopy] ArmorStandArmorModel.setRotationAngles because armor stands are dumb
    // This fixes the armor "breathing" and helmets always facing south on armor stands
    @Override
    public void setAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!(entity instanceof ArmorStandEntity entityIn)) {
            super.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            return;
        }

        this.head.pitch = ((float) Math.PI / 180F) * entityIn.getHeadRotation().getPitch();
        this.head.yaw = ((float) Math.PI / 180F) * entityIn.getHeadRotation().getYaw();
        this.head.roll = ((float) Math.PI / 180F) * entityIn.getHeadRotation().getRoll();
        this.head.setPivot(0.0F, 1.0F, 0.0F);
        this.body.pitch = ((float) Math.PI / 180F) * entityIn.getBodyRotation().getPitch();
        this.body.yaw = ((float) Math.PI / 180F) * entityIn.getBodyRotation().getYaw();
        this.body.roll = ((float) Math.PI / 180F) * entityIn.getBodyRotation().getRoll();
        this.leftArm.pitch = ((float) Math.PI / 180F) * entityIn.getLeftArmRotation().getPitch();
        this.leftArm.yaw = ((float) Math.PI / 180F) * entityIn.getLeftArmRotation().getYaw();
        this.leftArm.roll = ((float) Math.PI / 180F) * entityIn.getLeftArmRotation().getRoll();
        this.rightArm.pitch = ((float) Math.PI / 180F) * entityIn.getRightArmRotation().getPitch();
        this.rightArm.yaw = ((float) Math.PI / 180F) * entityIn.getRightArmRotation().getYaw();
        this.rightArm.roll = ((float) Math.PI / 180F) * entityIn.getRightArmRotation().getRoll();
        this.leftLeg.pitch = ((float) Math.PI / 180F) * entityIn.getLeftLegRotation().getPitch();
        this.leftLeg.yaw = ((float) Math.PI / 180F) * entityIn.getLeftLegRotation().getYaw();
        this.leftLeg.roll = ((float) Math.PI / 180F) * entityIn.getLeftLegRotation().getRoll();
        this.leftLeg.setPivot(1.9F, 11.0F, 0.0F);
        this.rightLeg.pitch = ((float) Math.PI / 180F) * entityIn.getRightLegRotation().getPitch();
        this.rightLeg.yaw = ((float) Math.PI / 180F) * entityIn.getRightLegRotation().getYaw();
        this.rightLeg.roll = ((float) Math.PI / 180F) * entityIn.getRightLegRotation().getRoll();
        this.rightLeg.setPivot(-1.9F, 11.0F, 0.0F);
        this.hat.copyTransform(this.head);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        setPartVisibility(slot);
        super.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    // [VanillaCopy] HumanoidArmorLayer
    private void setPartVisibility(EquipmentSlot slot) {
        setVisible(false);
        switch (slot) {
            case HEAD -> {
                head.visible = true;
                hat.visible = true;
            }
            case CHEST -> {
                body.visible = true;
                rightArm.visible = true;
                leftArm.visible = true;
            }
            case LEGS -> {
                body.visible = true;
                rightLeg.visible = true;
                leftLeg.visible = true;
            }
            case FEET -> {
                rightLeg.visible = true;
                leftLeg.visible = true;
            }
            default -> {}
        }
    }
}
