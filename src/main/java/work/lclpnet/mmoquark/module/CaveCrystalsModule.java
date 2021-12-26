package work.lclpnet.mmoquark.module;

import net.minecraft.block.Block;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.CaveCrystalBlock;
import work.lclpnet.mmoquark.block.CaveCrystalClusterBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CaveCrystalsModule implements IModule {

    public static final List<Block> crystalBlocks = new ArrayList<>(),
            crystalBlockPanes = new ArrayList<>(),
            crystalClusterBlocks = new ArrayList<>();

    @Override
    public void register() {
        crystal("red", 0xff0000, MaterialColor.RED);
        crystal("orange", 0xff8000, MaterialColor.ORANGE);
        crystal("yellow", 0xffff00, MaterialColor.YELLOW);
        crystal("green", 0x00ff00, MaterialColor.GREEN);
        crystal("blue", 0x00ffff, MaterialColor.LIGHT_BLUE);
        crystal("indigo", 0x0000ff, MaterialColor.BLUE);
        crystal("violet", 0xff00ff, MaterialColor.MAGENTA);
        crystal("white", 0xffffff, MaterialColor.WHITE);
        crystal("black", 0x000000, MaterialColor.BLACK);
    }

    private void crystal(String name, int color, MaterialColor material) {
        CaveCrystalBlock crystal = new CaveCrystalBlock(material, color);
        String crystalName = name + "_crystal";
        MMOBlockRegistrar.Result result = new MMOBlockRegistrar(crystal)
                .withPane()
                .register(MMOQuark.identifier(crystalName));

        crystalBlocks.add(crystal);
        crystalBlockPanes.add(Objects.requireNonNull(result.pane).block);

        CaveCrystalClusterBlock crystalClusterBlock = new CaveCrystalClusterBlock(crystal);
        new MMOBlockRegistrar(crystalClusterBlock)
                .register(MMOQuark.identifier(crystalName + "_cluster"), ItemGroup.DECORATIONS);

        crystalClusterBlocks.add(crystalClusterBlock);
    }
}
