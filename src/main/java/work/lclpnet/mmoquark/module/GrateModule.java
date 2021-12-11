package work.lclpnet.mmoquark.module;

import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.GrateBlock;

public class GrateModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(new GrateBlock())
                .register(MMOQuark.identifier("grate"), ItemGroup.DECORATIONS);
    }
}
