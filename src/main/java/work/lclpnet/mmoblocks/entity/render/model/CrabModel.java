package work.lclpnet.mmoblocks.entity.render.model;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.MathHelper;
import work.lclpnet.mmoblocks.entity.CrabEntity;

import java.util.Set;

@Environment(EnvType.CLIENT)
public class CrabModel extends EntityModel<CrabEntity> {

    private float wiggleX = 0;
    private float wiggleY = 0;
    private float crabSize = 0;

    public ModelPart group;

    public ModelPart body;
    public ModelPart rightClaw;
    public ModelPart leftClaw;
    public ModelPart rightLeg1;
    public ModelPart rightLeg2;
    public ModelPart rightLeg3;
    public ModelPart rightLeg4;
    public ModelPart leftLeg1;
    public ModelPart leftLeg2;
    public ModelPart leftLeg3;
    public ModelPart leftLeg4;
    public ModelPart rightEye;
    public ModelPart leftEye;

    private final Set<ModelPart> leftLegs;
    private final Set<ModelPart> rightLegs;

    public CrabModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        group = new ModelPart(this);
        group.setPivot(0.0F, 0.0F, 0.0F);

        this.leftLeg4 = new ModelPart(this, 0, 19);
        this.leftLeg4.mirror = true;
        this.leftLeg4.setPivot(3.0F, 20.0F, -1.0F);
        this.leftLeg4.addCuboid(0.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
        this.setRotateAngle(leftLeg4, 0.0F, 0.4363323129985824F, 0.7853981633974483F);
        this.leftLeg3 = new ModelPart(this, 0, 19);
        this.leftLeg3.mirror = true;
        this.leftLeg3.setPivot(3.0F, 20.0F, 0.0F);
        this.leftLeg3.addCuboid(0.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
        this.setRotateAngle(leftLeg3, 0.0F, 0.2181661564992912F, 0.7853981633974483F);
        this.rightEye = new ModelPart(this, 0, 11);
        this.rightEye.setPivot(0.0F, 0.0F, 0.0F);
        this.rightEye.addCuboid(-3.0F, -3.5F, -2.85F, 1, 3, 1, 0.0F);
        this.setRotateAngle(rightEye, -0.39269908169872414F, 0.0F, 0.0F);
        this.rightLeg4 = new ModelPart(this, 0, 19);
        this.rightLeg4.setPivot(-3.0F, 20.0F, -1.0F);
        this.rightLeg4.addCuboid(-6.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
        this.setRotateAngle(rightLeg4, 0.0F, -0.4363323129985824F, -0.7853981633974483F);
        this.rightClaw = new ModelPart(this, 14, 11);
        this.rightClaw.setPivot(-3.0F, 20.0F, -4.0F);
        this.rightClaw.addCuboid(-3.0F, -2.5F, -6.0F, 3, 5, 6, 0.0F);
        this.setRotateAngle(rightClaw, 0.0F, 0.39269908169872414F, -0.39269908169872414F);
        this.leftLeg1 = new ModelPart(this, 0, 19);
        this.leftLeg1.mirror = true;
        this.leftLeg1.setPivot(3.0F, 20.0F, 2.0F);
        this.leftLeg1.addCuboid(0.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
        this.setRotateAngle(leftLeg1, 0.0F, -0.4363323129985824F, 0.7853981633974483F);
        this.rightLeg2 = new ModelPart(this, 0, 19);
        this.rightLeg2.setPivot(-3.0F, 20.0F, 0.9F);
        this.rightLeg2.addCuboid(-6.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
        this.setRotateAngle(rightLeg2, 0.0F, 0.2181661564992912F, -0.7853981633974483F);
        this.leftClaw = new ModelPart(this, 14, 11);
        this.leftClaw.mirror = true;
        this.leftClaw.setPivot(3.0F, 20.0F, -4.0F);
        this.leftClaw.addCuboid(0.0F, -2.5F, -6.0F, 3, 5, 6, 0.0F);
        this.setRotateAngle(leftClaw, 0.0F, -0.39269908169872414F, 0.39269908169872414F);
        this.rightLeg1 = new ModelPart(this, 0, 19);
        this.rightLeg1.setPivot(-3.0F, 20.0F, 2.0F);
        this.rightLeg1.addCuboid(-6.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
        this.setRotateAngle(rightLeg1, 0.0F, 0.4363323129985824F, -0.7853981633974483F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPivot(0.0F, 20.0F, 0.0F);
        this.body.addCuboid(-4.0F, -2.5F, -3.0F, 8, 5, 6, 0.0F);
        this.leftEye = new ModelPart(this, 0, 11);
        this.leftEye.setPivot(0.0F, 0.0F, 0.0F);
        this.leftEye.addCuboid(2.0F, -3.5F, -2.85F, 1, 3, 1, 0.0F);
        this.setRotateAngle(leftEye, -0.39269908169872414F, 0.0F, 0.0F);
        this.leftLeg2 = new ModelPart(this, 0, 19);
        this.leftLeg2.mirror = true;
        this.leftLeg2.setPivot(3.0F, 20.0F, 0.9F);
        this.leftLeg2.addCuboid(0.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
        this.setRotateAngle(leftLeg2, 0.0F, -0.2181661564992912F, 0.7853981633974483F);
        this.rightLeg3 = new ModelPart(this, 0, 19);
        this.rightLeg3.setPivot(-3.0F, 20.0F, 0.0F);
        this.rightLeg3.addCuboid(-6.0F, -0.5F, -0.5F, 6, 1, 1, 0.0F);
        this.setRotateAngle(rightLeg3, 0.0F, -0.2181661564992912F, -0.7853981633974483F);
        this.body.addChild(this.rightEye);
        this.body.addChild(this.leftEye);

        this.group.addChild(body);
        this.group.addChild(rightLeg1);
        this.group.addChild(rightLeg2);
        this.group.addChild(rightLeg3);
        this.group.addChild(rightLeg4);
        this.group.addChild(leftLeg1);
        this.group.addChild(leftLeg2);
        this.group.addChild(leftLeg3);
        this.group.addChild(leftLeg4);
        this.group.addChild(rightClaw);
        this.group.addChild(leftClaw);

        leftLegs = ImmutableSet.of(leftLeg1, leftLeg2, leftLeg3, leftLeg4);
        rightLegs = ImmutableSet.of(rightLeg1, rightLeg2, rightLeg3, rightLeg4);
    }

    public void setRotateAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }
    
    @Override
    public void setAngles(CrabEntity crab, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        rightLeg1.roll = -0.2618F + (-1 + MathHelper.cos(limbAngle * 0.6662F)) * 0.7F * limbDistance;
        rightLeg2.roll = -0.5236F + (-1 + MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI)) * 0.7F * limbDistance;
        rightLeg3.roll = -0.5236F + (-1 + MathHelper.cos(limbAngle * 0.6662F)) * 0.7F * limbDistance;
        rightLeg4.roll = -0.2618F + (-1 + MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI)) * 0.7F * limbDistance;
        leftLeg1.roll = 0.2618F + (1 + MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI)) * 0.7F * limbDistance;
        leftLeg2.roll = 0.5236F + (1 + MathHelper.cos(limbAngle * 0.6662F)) * 0.7F * limbDistance;
        leftLeg3.roll = 0.5236F + (1 + MathHelper.cos(limbAngle * 0.6662F + (float) Math.PI)) * 0.7F * limbDistance;
        leftLeg4.roll = 0.2618F + (1 + MathHelper.cos(limbAngle * 0.6662F)) * 0.7F * limbDistance;

        leftClaw.pitch = 0.0f;
        rightClaw.pitch = 0.0f;
        wiggleX = 0.0f;
        wiggleY = 0.0f;

        crabSize = crab.getSizeModifier();
        if (child)
            crabSize /= 2;

        if(crab.isRaving()) {
            float crabRaveBPM = 125F / 4;
            float freq = (20F / crabRaveBPM);
            float tick = animationProgress * freq;
            float sin = (float) (Math.sin(tick) * 0.5 + 0.5);

            float legRot = (sin * 0.8F) + 0.6F;
            leftLegs.forEach(l -> l.roll = legRot);
            rightLegs.forEach(l -> l.roll = -legRot);

            float maxHeight = -0.05F;
            float horizontalOff = 0.2F;
            wiggleX = (sin - 0.5F) * 2 * maxHeight + maxHeight / 2;

            float slowSin = (float) Math.sin(tick / 2);
            wiggleY = slowSin * horizontalOff;

            float armRot = sin * 0.5F - 1.2F;
            leftClaw.pitch = armRot;
            rightClaw.pitch = armRot;
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.push();
        matrices.translate(0, 1.5 - crabSize * 1.5, 0);
        matrices.scale(crabSize, crabSize, crabSize);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90F));
        matrices.translate(wiggleX, wiggleY, 0);
        group.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        matrices.pop();
    }
}
