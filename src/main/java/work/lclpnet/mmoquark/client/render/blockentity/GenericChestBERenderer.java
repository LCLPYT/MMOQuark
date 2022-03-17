package work.lclpnet.mmoquark.client.render.blockentity;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import java.util.Calendar;

public abstract class GenericChestBERenderer<T extends BlockEntity & ChestAnimationProgress> implements BlockEntityRenderer<T> {

    public final ModelPart lid;
    public final ModelPart bottom;
    public final ModelPart lock;
    public final ModelPart doubleLeftLid;
    public final ModelPart doubleLeftBottom;
    public final ModelPart doubleLeftLock;
    public final ModelPart doubleRightLid;
    public final ModelPart doubleRightBottom;
    public final ModelPart doubleRightLock;
    private final boolean christmas;

    public GenericChestBERenderer(BlockEntityRendererFactory.Context context) {
        Calendar calendar = Calendar.getInstance();
        this.christmas = calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26;


        ModelPart part = context.getLayerModelPart(EntityModelLayers.CHEST);
        this.bottom = part.getChild("bottom");
        this.lid = part.getChild("lid");
        this.lock = part.getChild("lock");
        ModelPart dLeft = context.getLayerModelPart(EntityModelLayers.DOUBLE_CHEST_LEFT);
        this.doubleLeftBottom = dLeft.getChild("bottom");
        this.doubleLeftLid = dLeft.getChild("lid");
        this.doubleLeftLock = dLeft.getChild("lock");
        ModelPart dRight = context.getLayerModelPart(EntityModelLayers.DOUBLE_CHEST_RIGHT);
        this.doubleRightBottom = dRight.getChild("bottom");
        this.doubleRightLid = dRight.getChild("lid");
        this.doubleRightLock = dRight.getChild("lock");
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        boolean bl = world != null;
        BlockState blockState = bl ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        ChestType chestType = blockState.contains(ChestBlock.CHEST_TYPE) ? blockState.get(ChestBlock.CHEST_TYPE) : ChestType.SINGLE;
        Block block = blockState.getBlock();
        if (block instanceof AbstractChestBlock<?> abstractChestBlock) {
            boolean bl2 = chestType != ChestType.SINGLE;
            matrices.push();
            float f = blockState.get(ChestBlock.FACING).asRotation();
            matrices.translate(0.5D, 0.5D, 0.5D);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-f));
            matrices.translate(-0.5D, -0.5D, -0.5D);
            DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> propertySource2;
            if (bl) {
                propertySource2 = abstractChestBlock.getBlockEntitySource(blockState, world, entity.getPos(), true);
            } else {
                propertySource2 = DoubleBlockProperties.PropertyRetriever::getFallback;
            }

            float g = propertySource2.apply(ChestBlock.getAnimationProgressRetriever(entity)).get(tickDelta);
            g = 1.0F - g;
            g = 1.0F - g * g * g;
            int i = propertySource2.apply(new LightmapCoordinatesRetriever<>()).applyAsInt(light);
            SpriteIdentifier spriteIdentifier = getMaterialFinal(entity, chestType);
            VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);
            if (bl2) {
                if (chestType == ChestType.LEFT) {
                    this.render(matrices, vertexConsumer, this.doubleLeftLid, this.doubleLeftLock, this.doubleLeftBottom, g, i, overlay);
                } else {
                    this.render(matrices, vertexConsumer, this.doubleRightLid, this.doubleRightLock, this.doubleRightBottom, g, i, overlay);
                }
            } else {
                this.render(matrices, vertexConsumer, this.lid, this.lock, this.bottom, g, i, overlay);
            }

            matrices.pop();
        }
    }

    public final SpriteIdentifier getMaterialFinal(T t, ChestType type) {
        if (christmas) return TexturedRenderLayers.getChestTexture(t, type, true);
        else return getMaterial(t, type);
    }

    public abstract SpriteIdentifier getMaterial(T t, ChestType type);

    public void render(MatrixStack matrices, VertexConsumer vertices, ModelPart lid, ModelPart latch, ModelPart base, float openFactor, int light, int overlay) {
        lid.pitch = -(openFactor * 1.5707964F);
        latch.pitch = lid.pitch;
        lid.render(matrices, vertices, light, overlay);
        latch.render(matrices, vertices, light, overlay);
        base.render(matrices, vertices, light, overlay);
    }
}
