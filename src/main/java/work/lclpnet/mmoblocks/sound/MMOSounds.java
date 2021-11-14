package work.lclpnet.mmoblocks.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;

import java.util.ArrayList;
import java.util.List;

public class MMOSounds {

    private static List<SoundEvent> sounds = new ArrayList<>();

    public static final SoundEvent ENTITY_STONELING_MEEP = register("entity.stoneling.meep");
    public static final SoundEvent ENTITY_STONELING_PURR = register("entity.stoneling.purr");
    public static final SoundEvent ENTITY_STONELING_GIVE = register("entity.stoneling.give");
    public static final SoundEvent ENTITY_STONELING_TAKE = register("entity.stoneling.take");
    public static final SoundEvent ENTITY_STONELING_EAT = register("entity.stoneling.eat");
    public static final SoundEvent ENTITY_STONELING_DIE = register("entity.stoneling.die");
    public static final SoundEvent ENTITY_STONELING_CRY = register("entity.stoneling.cry");

    public static final SoundEvent ENTITY_PICKARANG_THROW = register("entity.pickarang.throw");
    public static final SoundEvent ENTITY_PICKARANG_CLANK = register("entity.pickarang.clank");
    public static final SoundEvent ENTITY_PICKARANG_SPARK = register("entity.pickarang.spark");
    public static final SoundEvent ENTITY_PICKARANG_PICKUP = register("entity.pickarang.pickup");

    public static SoundEvent register(String name) {
        Identifier loc = MMOBlocks.identifier(name);
        SoundEvent event = new SoundEvent(loc);
        sounds.add(event);
        return event;
    }

    public static void init() {
        if (sounds == null) throw new IllegalStateException("Sounds are already initialized");
        sounds.forEach(sound -> Registry.register(Registry.SOUND_EVENT, sound.getId(), sound));
        sounds = null;
    }
}
