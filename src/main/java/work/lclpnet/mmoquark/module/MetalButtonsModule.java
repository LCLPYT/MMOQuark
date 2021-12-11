package work.lclpnet.mmoquark.module;

import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.MetalButtonBlock;

public class MetalButtonsModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(new MetalButtonBlock(100))
                .register(MMOQuark.identifier("iron_button"), ItemGroup.REDSTONE);

        new MMOBlockRegistrar(new MetalButtonBlock(4))
                .register(MMOQuark.identifier("gold_button"), ItemGroup.REDSTONE);
    }
}
