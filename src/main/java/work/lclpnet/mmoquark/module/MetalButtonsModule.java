package work.lclpnet.mmoquark.module;

import net.minecraft.item.ItemGroup;
import work.lclpnet.mmoquark.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.block.MetalButtonBlock;

public class MetalButtonsModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(new MetalButtonBlock(100))
                .register("iron_button", ItemGroup.REDSTONE);

        new MMOBlockRegistrar(new MetalButtonBlock(4))
                .register("gold_button", ItemGroup.REDSTONE);
    }
}
