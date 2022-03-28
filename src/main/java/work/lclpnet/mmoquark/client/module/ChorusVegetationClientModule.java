package work.lclpnet.mmoquark.client.module;

import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmoquark.module.ChorusVegetationModule;

import java.util.stream.Stream;

public class ChorusVegetationClientModule implements IClientModule {

    @Override
    public void registerClient() {
        Stream.of(ChorusVegetationModule.chorusWeeds,
                ChorusVegetationModule.chorusTwist,
                ChorusVegetationModule.chorusWeedsPot,
                ChorusVegetationModule.chorusTwistPot
        ).forEach(block -> MMORenderLayers.setBlockRenderType(block, RenderLayer.getCutout()));
    }
}
