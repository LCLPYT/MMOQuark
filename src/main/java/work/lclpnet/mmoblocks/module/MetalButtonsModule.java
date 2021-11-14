package work.lclpnet.mmoblocks.module;

import net.minecraft.item.ItemGroup;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.MetalButtonBlock;

public class MetalButtonsModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(new MetalButtonBlock(100))
                .register("iron_button", ItemGroup.REDSTONE);

        new MMOBlockRegistrar(new MetalButtonBlock(4))
                .register("gold_button", ItemGroup.REDSTONE);
    }
}
