package work.lclpnet.mmoquark.module;

import net.minecraft.item.ItemGroup;
import work.lclpnet.mmoquark.block.GrateBlock;
import work.lclpnet.mmoquark.block.MMOBlockRegistrar;

public class GrateModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(new GrateBlock())
                .register("grate", ItemGroup.DECORATIONS);
    }
}
