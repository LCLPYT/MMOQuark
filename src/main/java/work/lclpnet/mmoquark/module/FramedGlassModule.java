package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.PaneBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.block.ext.MMOGlassBlock;
import work.lclpnet.mmoquark.MMOQuark;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FramedGlassModule implements IModule {

    public static Block framedGlass;
    public static PaneBlock framedGlassPane;
    public static final List<Block> framedGlassBlocks = new ArrayList<>();
    public static final List<PaneBlock> framedGlassPanes = new ArrayList<>();

    @Override
    public void register() {
        AbstractBlock.Settings props = FabricBlockSettings.of(Material.GLASS)
                .strength(3F, 10F)
                .sounds(BlockSoundGroup.GLASS);

        framedGlass = new MMOGlassBlock(props);
        MMOBlockRegistrar.Result result = new MMOBlockRegistrar(framedGlass)
                .withPane()
                .register(MMOQuark.identifier("framed_glass"));
        
        framedGlassPane = Objects.requireNonNull(result.pane()).block();
        
        for (DyeColor color : DyeColor.values()) {
            Block framedGlass = new MMOGlassBlock(props);
            result = new MMOBlockRegistrar(framedGlass)
                    .withPane()
                    .register(MMOQuark.identifier("%s_framed_glass", color.getName()));

            framedGlassBlocks.add(framedGlass);
            framedGlassPanes.add(Objects.requireNonNull(result.pane()).block());
        }
    }
}
