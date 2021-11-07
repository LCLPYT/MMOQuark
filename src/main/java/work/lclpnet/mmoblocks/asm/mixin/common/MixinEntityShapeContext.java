package work.lclpnet.mmoblocks.asm.mixin.common;

import net.minecraft.block.EntityShapeContext;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import work.lclpnet.mmoblocks.asm.type.IEntityShapeContext;

@Mixin(EntityShapeContext.class)
public class MixinEntityShapeContext implements IEntityShapeContext {

    protected Entity entity;

    @Inject(
            method = "<init>(Lnet/minecraft/entity/Entity;)V",
            at = @At("RETURN")
    )
    public void onConstructEntity(Entity entity, CallbackInfo ci) {
        this.entity = entity;
    }

    @Nullable
    @Override
    public Entity getEntity() {
        return this.entity;
    }
}
