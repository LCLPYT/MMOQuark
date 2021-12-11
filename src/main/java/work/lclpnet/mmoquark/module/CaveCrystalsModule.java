package work.lclpnet.mmoquark.module;

import net.minecraft.block.MaterialColor;
import work.lclpnet.mmoquark.block.CaveCrystalBlock;
import work.lclpnet.mmoquark.block.CaveCrystalClusterBlock;
import work.lclpnet.mmoquark.block.MMOBlockRegistrar;

public class CaveCrystalsModule implements IModule {

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
        new MMOBlockRegistrar(crystal)
                .withPane()
                .register(crystalName);

        new MMOBlockRegistrar(new CaveCrystalClusterBlock(crystal))
                .register(crystalName + "_cluster");
    }
}
