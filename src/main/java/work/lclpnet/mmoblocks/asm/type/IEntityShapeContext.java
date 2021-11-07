package work.lclpnet.mmoblocks.asm.type;

import net.minecraft.entity.Entity;

import javax.annotation.Nullable;

public interface IEntityShapeContext {

    @Nullable
    Entity getEntity();
}
