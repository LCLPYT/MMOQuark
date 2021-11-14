package work.lclpnet.mmoblocks.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.block.ext.IItemColorProvider;
import work.lclpnet.mmoblocks.util.Env;
import work.lclpnet.mmoblocks.util.MMOBlockColors;

import java.util.function.Function;

public class MMOItemRegistrar {

    protected final Function<FabricItemSettings, Item> itemFactory;

    public MMOItemRegistrar() {
        this(Item::new);
    }

    public MMOItemRegistrar(Function<FabricItemSettings, Item> itemFactory) {
        this.itemFactory = itemFactory;
    }

    public void register(String name) {
        register(name, ItemGroup.BUILDING_BLOCKS);
    }

    public void register(String name, ItemGroup group) {
        final Identifier blockId = MMOBlocks.identifier(name);
        final Item item = itemFactory.apply(new FabricItemSettings().group(group));
        Registry.register(Registry.ITEM, blockId, item);

        if (Env.isClient() && item instanceof IItemColorProvider) registerItemColor((IItemColorProvider) item);
    }

    @Environment(EnvType.CLIENT)
    private void registerItemColor(IItemColorProvider provider) {
        MMOBlockColors.registerItemColorProvider(provider);
    }

    public static void registerSpawnEgg(EntityType<?> type, String entityName, int primaryColor, int secondaryColor) {
        new MMOItemRegistrar(settings -> new SpawnEggItem(type, primaryColor, secondaryColor, settings))
                .register(String.format("%s_spawn_egg", entityName), ItemGroup.MISC);
    }
}
