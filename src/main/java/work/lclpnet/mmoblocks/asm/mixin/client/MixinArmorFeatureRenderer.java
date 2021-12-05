package work.lclpnet.mmoblocks.asm.mixin.client;

import com.google.common.collect.Maps;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import work.lclpnet.mmoblocks.item.ICustomArmorModel;

import javax.annotation.Nullable;
import java.util.Map;

@Mixin(ArmorFeatureRenderer.class)
public abstract class MixinArmorFeatureRenderer {

    @Inject(
            method = "renderArmor(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/model/BipedEntityModel;setAttributes(Lnet/minecraft/client/render/entity/model/BipedEntityModel;)V"
            ),
            cancellable = true
    )
    private void useCustomArmorModel(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity livingEntity, EquipmentSlot equipmentSlot, int i, BipedEntityModel<LivingEntity> bipedEntityModel, CallbackInfo ci) {
        ItemStack itemStack = livingEntity.getEquippedStack(equipmentSlot);
        ArmorItem armorItem = (ArmorItem) itemStack.getItem(); // is always ArmorItem at this point
        if (!(armorItem instanceof ICustomArmorModel)) return;

        bipedEntityModel = ((ICustomArmorModel) armorItem).getArmorModel(livingEntity, itemStack, equipmentSlot, bipedEntityModel);
        if (bipedEntityModel == null) return;

        ci.cancel();

        // copied from ArmorFeatureRenderer::renderArmor
        @SuppressWarnings("unchecked")
        BipedEntityModel<LivingEntity> model = ((FeatureRenderer<LivingEntity, BipedEntityModel<LivingEntity>>) (Object) this).getContextModel();
        model.setAttributes(bipedEntityModel);

        this.setVisible(bipedEntityModel, equipmentSlot);
        boolean bl = this.usesSecondLayer(equipmentSlot);
        boolean bl2 = itemStack.hasGlint();
        if (armorItem instanceof DyeableArmorItem) {
            int j = ((DyeableArmorItem)armorItem).getColor(itemStack);
            float f = (float)(j >> 16 & 255) / 255.0F;
            float g = (float)(j >> 8 & 255) / 255.0F;
            float h = (float)(j & 255) / 255.0F;
            this.renderArmorPartsCustom(matrices, vertexConsumers, i, bl2, bipedEntityModel, f, g, h, this.getArmorResource(livingEntity, itemStack, equipmentSlot, null));
            this.renderArmorPartsCustom(matrices, vertexConsumers, i, bl2, bipedEntityModel, 1.0F, 1.0F, 1.0F, this.getArmorResource(livingEntity, itemStack, equipmentSlot, "overlay"));
        } else {
            this.renderArmorPartsCustom(matrices, vertexConsumers, i, bl2, bipedEntityModel, 1.0F, 1.0F, 1.0F, this.getArmorResource(livingEntity, itemStack, equipmentSlot, null));
        }
    }

    @Shadow
    protected abstract void setVisible(BipedEntityModel<?> bipedModel, EquipmentSlot slot);

    @Shadow
    protected abstract boolean usesSecondLayer(EquipmentSlot slot);

    protected void renderArmorPartsCustom(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, boolean bl, BipedEntityModel<LivingEntity> bipedEntityModel, float f, float g, float h, Identifier armorResource) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(armorResource), false, bl);
        bipedEntityModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, f, g, h, 1.0F);
    }

    private static final Map<String, Identifier> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();

    public Identifier getArmorResource(Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type) {
        Item item = stack.getItem();
        if (item instanceof ICustomArmorModel) return ((ICustomArmorModel) item).getArmorTexture(stack, entity, slot, type);

        // Fallback, if somehow the item does not implement ICustomArmorModel
        // Forge port below
        String texture = ((ArmorItem) item).getMaterial().getName();
        String domain = "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }

        String s = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (usesSecondLayer(slot) ? 2 : 1), type == null ? "" : String.format("_%s", type));
        return ARMOR_TEXTURE_RES_MAP.computeIfAbsent(s, Identifier::new);
    }
}
