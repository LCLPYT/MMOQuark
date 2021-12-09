package work.lclpnet.mmoblocks.client.render.entity;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BannerItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.map.MapState;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.asm.mixin.client.EntityRenderDispatcherAccessor;
import work.lclpnet.mmoblocks.entity.GlassItemFrameEntity;

import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class GlassItemFrameRenderer extends EntityRenderer<GlassItemFrameEntity> {

    private static final ModelIdentifier LOCATION_MODEL = new ModelIdentifier(MMOBlocks.identifier("glass_frame"), "inventory");
    private static final List<Direction> SIGN_DIRECTIONS = Arrays.asList(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);

    private static BannerBlockEntity banner = new BannerBlockEntity();
    private final ModelPart bannerModel;

    private final ItemRenderer itemRenderer;
    private final ItemFrameEntityRenderer defaultRenderer;

    public GlassItemFrameRenderer(EntityRenderDispatcher dispatcher, ItemRenderer itemRenderer) {
        super(dispatcher);
        bannerModel = BannerBlockEntityRenderer.createBanner();
        this.itemRenderer = itemRenderer;
        this.defaultRenderer = (ItemFrameEntityRenderer) ((EntityRenderDispatcherAccessor) dispatcher).getRenderers().get(EntityType.ITEM_FRAME);
    }

    @Override
    public Identifier getTexture(GlassItemFrameEntity entity) {
//        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
        return null;
    }

    @Override
    public void render(GlassItemFrameEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);

        matrices.push();
        Direction direction = entity.getHorizontalFacing();
        Vec3d vec3d = this.getPositionOffset(entity, tickDelta);
        matrices.translate(-vec3d.getX(), -vec3d.getY(), -vec3d.getZ());
        matrices.translate((double) direction.getOffsetX() * 0.46875D, (double) direction.getOffsetY() * 0.46875D, (double) direction.getOffsetZ() * 0.46875D);
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(entity.pitch));
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - entity.yaw));
        BlockRenderManager blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
        BakedModelManager modelManager = blockRenderManager.getModels().getModelManager();

        ItemStack itemstack = entity.getHeldItemStack();

        if (entity.getDataTracker().get(GlassItemFrameEntity.IS_SHINY)) light = 0xF000F0;

        if (itemstack.isEmpty()) {
            matrices.push();
            matrices.translate(-0.5D, -0.5D, -0.5D);
            blockRenderManager.getModelRenderer().render(matrices.peek(), vertexConsumers.getBuffer(TexturedRenderLayers.getEntityCutout()), null, modelManager.getModel(LOCATION_MODEL), 1.0F, 1.0F, 1.0F, light, OverlayTexture.DEFAULT_UV);
            matrices.pop();
        } else {
            renderItemStack(entity, matrices, vertexConsumers, light, itemstack);
        }

        matrices.pop();
    }

    @Override
    public Vec3d getPositionOffset(GlassItemFrameEntity entity, float tickDelta) {
        return new Vec3d(
                (float) entity.getHorizontalFacing().getOffsetX() * 0.3F,
                -0.25D,
                (float) entity.getHorizontalFacing().getOffsetZ() * 0.3F
        );
    }

    @Override
    protected boolean hasLabel(GlassItemFrameEntity entity) {
        if (MinecraftClient.isHudEnabled() && !entity.getHeldItemStack().isEmpty() && entity.getHeldItemStack().hasCustomName() && this.dispatcher.targetedEntity == entity) {
            double d0 = this.dispatcher.getSquaredDistanceToCamera(entity);
            float f = entity.isSneaky() ? 32.0F : 64.0F;
            return d0 < (double)(f * f);
        } else {
            return false;
        }
    }

    @Override
    protected void renderLabelIfPresent(GlassItemFrameEntity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.renderLabelIfPresent(entity, entity.getHeldItemStack().getName(), matrices, vertexConsumers, light);
    }

    protected void renderItemStack(GlassItemFrameEntity itemFrame, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack) {
        if (!stack.isEmpty()) {
            matrices.push();
            MapState mapState = FilledMapItem.getMapState(stack, itemFrame.world);

            sign: if(itemFrame.isOnSign()) {
                BlockPos back = itemFrame.getBehindPos();
                BlockState state = itemFrame.world.getBlockState(back);

                Direction ourDirection = itemFrame.getHorizontalFacing().getOpposite();

                int signRotation = state.get(SignBlock.ROTATION);
                Direction signDirection = SIGN_DIRECTIONS.get(signRotation / 4);
                if(signRotation % 4 == 0 ? (signDirection != ourDirection) : (signDirection.getOpposite() == ourDirection))
                    break sign;

                int ourRotation = SIGN_DIRECTIONS.indexOf(ourDirection) * 4;
                int rotation = signRotation - ourRotation;
                float angle = -rotation * 22.5F;

                matrices.translate(0, 0.35, 0.8);
                matrices.scale(0.4F, 0.4F, 0.4F);
                matrices.translate(0, 0, 0.5);
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(angle));
                matrices.translate(0, 0, -0.5);
                matrices.translate(0, 0, -0.085);
            }

            int rotation = mapState != null ? itemFrame.getRotation() % 4 * 2 : itemFrame.getRotation();
            matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float) rotation * 360.0F / 8.0F));

            if (mapState != null) {
                matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
                matrices.scale(0.0078125F, 0.0078125F, 0.0078125F);
                matrices.translate(-64.0F, -64.0F, 62.5F); // <- Use 62.5 instead of 64 to prevent z-fighting
                MinecraftClient.getInstance().gameRenderer.getMapRenderer().draw(matrices, vertexConsumers, mapState, true, light);
            } else {
                float s = 1.5F;
                if (stack.getItem() instanceof BannerItem) {
                    banner.readFrom(stack, ((BannerItem) stack.getItem()).getColor());
                    List<Pair<BannerPattern, DyeColor>> patterns = banner.getPatterns();

                    matrices.push();
                    matrices.translate(0.0001F, -0.5001F, 0.55F);
                    matrices.scale(0.799999F, 0.399999F, 0.5F);
                    BannerBlockEntityRenderer.method_29999(matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, bannerModel, ModelLoader.BANNER_BASE, true, patterns);
                    matrices.pop();
                } else {
                    if (stack.getItem() instanceof ShieldItem) {
                        s = 4F;
                        matrices.translate(-0.25F, 0F, 0.5F);
                    } else {
                        matrices.translate(0F, 0F, 0.475F);
                    }
                    matrices.scale(s, s, s);
                    matrices.scale(0.5F, 0.5F, 0.5F);
                    this.itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);
                }
            }

            matrices.pop();
        }
    }
}
