package work.lclpnet.mmoblocks.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public interface ICustomArmorModel {

    @Environment(EnvType.CLIENT)
    <A extends BipedEntityModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default);

    @Environment(EnvType.CLIENT)
    Identifier getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type);
}
