package work.lclpnet.mmoquark.client.module;

import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmoquark.module.CaveCrystalsModule;

import java.util.stream.Stream;

public class CaveCrystalClientModule implements IClientModule {

    @Override
    public void registerClient() {
        Stream.concat(CaveCrystalsModule.crystalBlocks.stream(), CaveCrystalsModule.crystalBlockPanes.stream())
                .forEach(block -> MMORenderLayers.setBlockRenderType(block, RenderLayer.getTranslucent()));

        CaveCrystalsModule.crystalClusterBlocks
                .forEach(block -> MMORenderLayers.setBlockRenderType(block, RenderLayer.getCutout()));
    }
}
