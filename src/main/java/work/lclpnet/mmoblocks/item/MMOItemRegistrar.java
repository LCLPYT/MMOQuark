package work.lclpnet.mmoblocks.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;

import java.util.function.Function;

public class MMOItemRegistrar {

    protected final Function<FabricItemSettings, Item> itemFactory;

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
    }
}
