package work.lclpnet.mmoblocks.asm.mixin.common;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SoundEvent.class)
public interface SoundEventAccessor {

    @Accessor
    Identifier getId();
}
