package work.lclpnet.mmoblocks.blockentity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class MMOItemBlockEntities {

    public static VariantChestBlockEntity variantChest = null;

    /**
     * Should be called, when all block entity types have been registered.
     */
    public static void init() {
        variantChest = new VariantChestBlockEntity();
    }
}
