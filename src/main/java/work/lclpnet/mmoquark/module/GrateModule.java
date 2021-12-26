package work.lclpnet.mmoquark.module;

import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.GrateBlock;

public class GrateModule implements IModule {

    public static GrateBlock grateBlock;

    @Override
    public void register() {
        grateBlock = new GrateBlock();
        new MMOBlockRegistrar(grateBlock)
                .register(MMOQuark.identifier("grate"), ItemGroup.DECORATIONS);
    }
}
