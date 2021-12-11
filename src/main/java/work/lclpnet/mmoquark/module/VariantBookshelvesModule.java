package work.lclpnet.mmoquark.module;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.util.MiscUtil;

import java.util.Arrays;

public class VariantBookshelvesModule implements IModule {

    @Override
    public void register() {
        Arrays.stream(MiscUtil.OVERWORLD_VARIANT_WOOD_TYPES).forEach(this::addVariantStuff);
        Arrays.stream(MiscUtil.NETHER_WOOD_TYPES).forEach(this::addVariantStuff);
    }

    private void addVariantStuff(String woodType) {
        new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.BOOKSHELF))
                .register(MMOQuark.identifier(String.format("%s_bookshelf", woodType)), ItemGroup.DECORATIONS);
    }
}
