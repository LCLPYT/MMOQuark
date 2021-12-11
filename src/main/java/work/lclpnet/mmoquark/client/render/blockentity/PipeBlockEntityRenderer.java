package work.lclpnet.mmoquark.client.render.blockentity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Direction;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.blockentity.PipeBlockEntity;

@Environment(EnvType.CLIENT)
public class PipeBlockEntityRenderer extends BlockEntityRenderer<PipeBlockEntity> {

    private static final ModelIdentifier LOCATION_MODEL = new ModelIdentifier(MMOQuark.identifier("pipe_flare"), "inventory");

    public PipeBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(PipeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(0.5, 0.5, 0.5);

        BlockRenderManager blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
        BakedModelManager modelManager = blockRenderManager.getModels().getModelManager();
        BakedModel model = modelManager.getModel(LOCATION_MODEL);
        for (Direction d : Direction.values())
            renderFlare(entity, blockRenderManager, model, matrices, vertexConsumers, tickDelta, light, overlay, d);

        matrices.pop();
    }

    private void renderFlare(PipeBlockEntity entity, BlockRenderManager blockRenderManager, BakedModel model, MatrixStack matrices, VertexConsumerProvider vertexConsumers, float tickDelta, int light, int overlay, Direction dir) {
        PipeBlockEntity.ConnectionType type = PipeBlockEntity.getConnectionTo(entity.getWorld(), entity.getPos(), dir);
        if (type.isFlared) {
            matrices.push();
            switch(dir.getAxis()) {
                case X:
                    matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-dir.asRotation()));
                    break;
                case Z:
                    matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(dir.asRotation()));
                    break;
                case Y:
                    matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90F));
                    if (dir == Direction.UP) matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180F));
                    break;
            }

            matrices.translate(-0.5, -0.5, type.flareShift);


            blockRenderManager.getModelRenderer().render(matrices.peek(), vertexConsumers.getBuffer(TexturedRenderLayers.getEntityCutout()), null, model, 1.0F, 1.0F, 1.0F, light, OverlayTexture.DEFAULT_UV);
            matrices.pop();
        }
    }
}
