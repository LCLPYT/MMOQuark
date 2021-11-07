package work.lclpnet.mmoblocks.module;

import net.minecraft.item.ItemGroup;
import work.lclpnet.mmoblocks.block.GrateBlock;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;

public class GrateModule implements IModule {

    @Override
    public void register() {
        new MMOBlockRegistrar(new GrateBlock())
                .register("grate", ItemGroup.DECORATIONS);
    }
}
