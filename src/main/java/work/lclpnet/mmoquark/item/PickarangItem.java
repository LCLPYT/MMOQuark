package work.lclpnet.mmoquark.item;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import work.lclpnet.mmoquark.entity.PickarangEntity;
import work.lclpnet.mmoquark.sound.MMOSounds;

import java.util.HashMap;
import java.util.HashSet;

public class PickarangItem extends Item {

    public final boolean isNetherite;

    public PickarangItem(Settings settings, boolean isNetherite) {
        super(settings);
        this.isNetherite = isNetherite;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(2, attacker, (player) -> player.sendToolBreakStatus(Hand.MAIN_HAND));
        return true;
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0) stack.damage(1, miner, (player) -> player.sendToolBreakStatus(Hand.MAIN_HAND));
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemstack = user.getStackInHand(hand);
        user.setStackInHand(hand, ItemStack.EMPTY);
        int eff = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, itemstack);
        Vec3d pos = user.getPos();
        world.playSound(null, pos.x, pos.y, pos.z, MMOSounds.ENTITY_PICKARANG_THROW, SoundCategory.NEUTRAL, 0.5F + eff * 0.14F, 0.4F / (world.random.nextFloat() * 0.4F + 0.8F));

        if(!world.isClient)  {
            int slot = hand == Hand.OFF_HAND ? user.getInventory().size() - 1 : user.getInventory().selectedSlot;
            PickarangEntity entity = new PickarangEntity(world, user);
            entity.setThrowData(slot, itemstack, isNetherite);
            entity.shoot(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F + eff * 0.325F, 0F);
            world.spawnEntity(entity);
        }

        if (!user.getAbilities().creativeMode) {
            int cooldown = 10 - eff;
            if (cooldown > 0) user.getItemCooldownManager().set(this, cooldown);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return new TypedActionResult<>(ActionResult.SUCCESS, itemstack);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        Multimap<EntityAttribute, EntityAttributeModifier> multimap = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);

        if (slot == EquipmentSlot.MAINHAND) {
            multimap.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 2, EntityAttributeModifier.Operation.ADDITION));
            multimap.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2.8, EntityAttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return 0F;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.getItem() == (isNetherite ? Items.NETHERITE_INGOT : Items.DIAMOND);
    }

    @Override
    public int getEnchantability() {
        return isNetherite ? Items.NETHERITE_PICKAXE.getEnchantability() : Items.DIAMOND_PICKAXE.getEnchantability();
    }
}
