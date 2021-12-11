package work.lclpnet.mmoquark.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.client.render.item.ICustomArmorModel;
import work.lclpnet.mmoquark.client.render.item.model.ForgottenHatModel;

import java.util.UUID;

public class ForgottenHatItem extends ArmorItem implements ICustomArmorModel {

    @Environment(EnvType.CLIENT)
    private static final Identifier TEXTURE = MMOQuark.identifier("textures/misc/forgotten_hat_worn.png");
    @Environment(EnvType.CLIENT)
    private ForgottenHatModel model;

    private Multimap<EntityAttribute, EntityAttributeModifier> attributes;

    public ForgottenHatItem(Settings settings) {
        super(ArmorMaterials.LEATHER, EquipmentSlot.HEAD, settings
                .maxCount(1)
                .maxDamage(0)
                .rarity(Rarity.RARE));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (attributes == null) {
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
            UUID uuid = UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150");
            builder.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uuid, "Armor modifier", 1, EntityAttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributes.GENERIC_LUCK, new EntityAttributeModifier(uuid, "Armor luck modifier", 1, EntityAttributeModifier.Operation.ADDITION));

            attributes = builder.build();
        }

        return slot == this.slot ? attributes : super.getAttributeModifiers(slot);
    }

    @Environment(EnvType.CLIENT)
    @Override
    @SuppressWarnings("unchecked")
    public <A extends BipedEntityModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default) {
        if (model == null) model = new ForgottenHatModel();

        return (A) model;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Identifier getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return TEXTURE;
    }
}
