package work.lclpnet.mmoquark.module;

import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.IronRodBlock;

public class IronRodModule implements IModule {

    public static IronRodBlock ironRodBlock;

    @Override
    public void register() {
        ironRodBlock = new IronRodBlock();
        new MMOBlockRegistrar(ironRodBlock)
                .register(MMOQuark.identifier("iron_rod"), ItemGroup.DECORATIONS);
    }
}
