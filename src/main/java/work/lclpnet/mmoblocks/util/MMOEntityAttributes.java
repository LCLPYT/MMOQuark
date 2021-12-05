package work.lclpnet.mmoblocks.util;

import net.fabricmc.fabric.mixin.object.builder.DefaultAttributeRegistryAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;

public class MMOEntityAttributes {

    public static <T extends LivingEntity> void registerDefaultAttributes(EntityType<T> entityType, DefaultAttributeContainer.Builder builder) {
        DefaultAttributeRegistryAccessor.getRegistry().put(entityType, builder.build());
    }
}
