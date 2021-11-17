package work.lclpnet.mmoblocks.asm.mixin.common;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {

    @Accessor
    int getLastAttackedTicks();

    @Accessor
    void setLastAttackedTicks(int lastAttackedTicks);
}
