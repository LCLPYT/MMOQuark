package work.lclpnet.mmoquark.client.render.entity.model;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import work.lclpnet.mmoquark.entity.CrabEntity;

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

    public CrabModel(ModelPart root) {
        group = root.getChild("group");
        body = group.getChild("body");
        rightClaw = group.getChild("rightClaw");
        leftClaw = group.getChild("leftClaw");
        rightLeg1 = group.getChild("rightLeg1");
        rightLeg2 = group.getChild("rightLeg2");
        rightLeg3 = group.getChild("rightLeg3");
        rightLeg4 = group.getChild("rightLeg4");
        leftLeg1 = group.getChild("leftLeg1");
        leftLeg2 = group.getChild("leftLeg2");
        leftLeg3 = group.getChild("leftLeg3");
        leftLeg4 = group.getChild("leftLeg4");
        rightEye = body.getChild("rightEye");
        leftEye = body.getChild("leftEye");

        leftLegs = ImmutableSet.of(leftLeg1, leftLeg2, leftLeg3, leftLeg4);
        rightLegs = ImmutableSet.of(rightLeg1, rightLeg2, rightLeg3, rightLeg4);
    }

    public static TexturedModelData createBodyLayer() {
        ModelData mesh = new ModelData();
        ModelPartData root = mesh.getRoot();

        ModelPartData group = root.addChild("group", ModelPartBuilder.create(), ModelTransform.NONE);

        ModelPartData body = group.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-4.0F, -2.5F, -3.0F, 8, 5, 6),
                ModelTransform.of(0.0F, 20.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        group.addChild("leftLeg4",
                ModelPartBuilder.create()
                        .mirrored()
                        .uv(0, 19)
                        .cuboid(0.0F, -0.5F, -0.5F, 6, 1, 1),
                ModelTransform.of(3.0F, 20.0F, -1.0F, 0.0F, 0.4363323129985824F, 0.7853981633974483F));

        group.addChild("leftLeg3",
                ModelPartBuilder.create()
                        .mirrored()
                        .uv(0, 19)
                        .cuboid(0.0F, -0.5F, -0.5F, 6, 1, 1),
                ModelTransform.of(3.0F, 20.0F, 0.0F, 0.0F, 0.2181661564992912F, 0.7853981633974483F));

        body.addChild("rightEye",
                ModelPartBuilder.create()
                        .uv(0, 11)
                        .cuboid(-3.0F, -3.5F, -2.85F, 1, 3, 1),
                ModelTransform.of(0.0F, 0.0F, 0.0F, -0.39269908169872414F, 0.0F, 0.0F));

        group.addChild("rightLeg4",
                ModelPartBuilder.create()
                        .uv(0, 19)
                        .cuboid(-6.0F, -0.5F, -0.5F, 6, 1, 1),
                ModelTransform.of(-3.0F, 20.0F, -1.0F, 0.0F, -0.4363323129985824F, -0.7853981633974483F));

        group.addChild("rightClaw",
                ModelPartBuilder.create()
                        .uv(14, 11)
                        .cuboid(-3.0F, -2.5F, -6.0F, 3, 5, 6),
                ModelTransform.of(-3.0F, 20.0F, -4.0F, 0.0F, 0.39269908169872414F, -0.39269908169872414F));

        group.addChild("leftLeg1",
                ModelPartBuilder.create()
                        .mirrored()
                        .uv(0, 19)
                        .cuboid(0.0F, -0.5F, -0.5F, 6, 1, 1),
                ModelTransform.of(3.0F, 20.0F, 2.0F, 0.0F, -0.4363323129985824F, 0.7853981633974483F));

        group.addChild("rightLeg2",
                ModelPartBuilder.create()
                        .mirrored()
                        .uv(0, 19)
                        .cuboid(-6.0F, -0.5F, -0.5F, 6, 1, 1),
                ModelTransform.of(-3.0F, 20.0F, 0.9F, 0.0F, 0.2181661564992912F, -0.7853981633974483F));

        group.addChild("leftClaw",
                ModelPartBuilder.create()
                        .mirrored()
                        .uv(14, 11)
                        .cuboid(0.0F, -2.5F, -6.0F, 3, 5, 6),
                ModelTransform.of(3.0F, 20.0F, -4.0F, 0.0F, -0.39269908169872414F, 0.39269908169872414F));

        group.addChild("rightLeg1",
                ModelPartBuilder.create()
                        .uv(0, 19)
                        .cuboid(-6.0F, -0.5F, -0.5F, 6, 1, 1),
                ModelTransform.of(-3.0F, 20.0F, 2.0F, 0.0F, 0.4363323129985824F, -0.7853981633974483F));

        body.addChild("leftEye",
                ModelPartBuilder.create()
                        .uv(0, 11)
                        .cuboid(2.0F, -3.5F, -2.85F, 1, 3, 1),
                ModelTransform.of(0.0F, 0.0F, 0.0F, -0.39269908169872414F, 0.0F, 0.0F));

        group.addChild("leftLeg2",
                ModelPartBuilder.create()
                        .mirrored()
                        .uv(0, 19)
                        .cuboid(0.0F, -0.5F, -0.5F, 6, 1, 1),
                ModelTransform.of(3.0F, 20.0F, 0.9F, 0.0F, -0.2181661564992912F, 0.7853981633974483F));

        group.addChild("rightLeg3",
                ModelPartBuilder.create()
                        .uv(0, 19)
                        .cuboid(-6.0F, -0.5F, -0.5F, 6, 1, 1),
                ModelTransform.of(-3.0F, 20.0F, 0.0F, 0.0F, -0.2181661564992912F, -0.7853981633974483F));

        return TexturedModelData.of(mesh, 32, 32);
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
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90F));
        matrices.translate(wiggleX, wiggleY, 0);
        group.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        matrices.pop();
    }
}
