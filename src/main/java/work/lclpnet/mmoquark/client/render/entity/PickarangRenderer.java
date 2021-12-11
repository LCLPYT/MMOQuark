package work.lclpnet.mmoquark.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import work.lclpnet.mmoquark.entity.PickarangEntity;

@Environment(EnvType.CLIENT)
public class PickarangRenderer extends EntityRenderer<PickarangEntity> {

    public PickarangRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(PickarangEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.translate(0, 0.2, 0);
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90F));

        MinecraftClient mc = MinecraftClient.getInstance();
        float time = entity.age + (mc.isPaused() ? 0 : tickDelta);
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(time * 20F));

        mc.getItemRenderer().renderItem(entity.getStack(), ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);

        matrices.pop();
    }

    @Override
    public Identifier getTexture(PickarangEntity entity) {
        return null;
    }
}
