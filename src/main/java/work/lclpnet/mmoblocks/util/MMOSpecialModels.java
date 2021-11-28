package work.lclpnet.mmoblocks.util;

import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;

public class MMOSpecialModels {

    private static final Set<Identifier> specialModels = new HashSet<>();

    /**
     * Indicate to vanilla that it should load and bake the given model, even if no blocks or
     * items use it. This is useful if e.g. you have baked models only for entity renderers.
     * Call during {@link net.fabricmc.api.ClientModInitializer#onInitializeClient()}.
     * @param identifier The model, either {@link net.minecraft.client.util.ModelIdentifier} to point to a blockstate variant,
     *           or plain {@link Identifier} to point directly to a json in the models folder.
     */
    public static void addSpecialModel(Identifier identifier) {
        specialModels.add(identifier);
    }

    public static Set<Identifier> getSpecialModels() {
        return specialModels;
    }
}
