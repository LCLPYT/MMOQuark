package work.lclpnet.mmoblocks.module;

import net.minecraft.item.ItemGroup;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.PaperLanternBlock;
import work.lclpnet.mmoblocks.block.ext.MMOInheritedPaneBlock;

public class PaperDecorModule implements IModule {

    @Override
    public void register() {
        PaperLanternBlock parent = new PaperLanternBlock();
        new MMOBlockRegistrar(parent)
                .register("paper_lantern", ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(new PaperLanternBlock())
                .register("paper_lantern_sakura", ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(new MMOInheritedPaneBlock(parent))
                .register("paper_wall", ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(new MMOInheritedPaneBlock(parent))
                .register("paper_wall_big", ItemGroup.DECORATIONS);

        new MMOBlockRegistrar(new MMOInheritedPaneBlock(parent))
                .register("paper_wall_sakura", ItemGroup.DECORATIONS);
    }
}
