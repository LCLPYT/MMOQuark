package work.lclpnet.mmoquark.module;

import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.block.ext.MMOInheritedPaneBlock;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.BambooMatBlock;
import work.lclpnet.mmoquark.block.BambooMatCarpetBlock;
import work.lclpnet.mmoquark.block.PaperLanternBlock;

public class OrientalPaletteModule implements IModule {

    @Override
    public void register() {
        PaperLanternBlock parent = new PaperLanternBlock();
        new MMOBlockRegistrar(parent)
                .register(MMOQuark.identifier("paper_lantern"), ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(new PaperLanternBlock())
                .register(MMOQuark.identifier("paper_lantern_sakura"), ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(new MMOInheritedPaneBlock(parent))
                .register(MMOQuark.identifier("paper_wall"), ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(new MMOInheritedPaneBlock(parent))
                .register(MMOQuark.identifier("paper_wall_big"), ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(new MMOInheritedPaneBlock(parent))
                .register(MMOQuark.identifier("paper_wall_sakura"), ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(new BambooMatBlock())
                .register(MMOQuark.identifier("bamboo_mat"), ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(new BambooMatCarpetBlock())
                .register(MMOQuark.identifier("bamboo_mat_carpet"), ItemGroup.DECORATIONS);
    }
}
