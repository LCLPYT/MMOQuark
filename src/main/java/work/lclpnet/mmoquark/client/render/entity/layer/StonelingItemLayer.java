package work.lclpnet.mmoquark.client.render.entity.layer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;
import work.lclpnet.mmoquark.client.render.entity.model.StonelingModel;
import work.lclpnet.mmoquark.entity.StonelingEntity;

@Environment(EnvType.CLIENT)
public class StonelingItemLayer extends FeatureRenderer<StonelingEntity, StonelingModel> {

    public StonelingItemLayer(FeatureRendererContext<StonelingEntity, StonelingModel> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, StonelingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack stack = entity.getCarryingItem();
        if (!stack.isEmpty()) {
            boolean isBlock = stack.getItem() instanceof BlockItem;

            matrices.push();
            matrices.translate(0F, 0.5F, 0F);
            if(!isBlock) {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.getItemAngle() + 180));
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90F));
            } else {
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180F));
            }

            matrices.scale(0.725F, 0.725F, 0.725F);
            MinecraftClient mc = MinecraftClient.getInstance();
            mc.getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
            matrices.pop();
        }
    }
}
