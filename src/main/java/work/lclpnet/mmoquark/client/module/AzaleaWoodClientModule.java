package work.lclpnet.mmoquark.client.module;

import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmocontent.client.entity.MMOBoatClientUtility;

import static work.lclpnet.mmocontent.client.render.block.MMORenderLayers.setBlockRenderType;
import static work.lclpnet.mmoquark.module.AzaleaWoodModule.azaleaWood;

public class AzaleaWoodClientModule implements IClientModule {

    @Override
    public void registerClient() {
        MMOBoatClientUtility.enableMMOBoatClientIntegration();

        final RenderLayer cutout = RenderLayer.getCutout();
        setBlockRenderType(azaleaWood.door, cutout);
        setBlockRenderType(azaleaWood.trapdoor, cutout);
    }
}
