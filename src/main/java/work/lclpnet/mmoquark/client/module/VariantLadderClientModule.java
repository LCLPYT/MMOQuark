package work.lclpnet.mmoquark.client.module;

import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import work.lclpnet.mmocontent.client.render.block.MMORenderLayers;
import work.lclpnet.mmoquark.module.VariantLaddersModule;

import java.util.ArrayList;
import java.util.List;

public class VariantLadderClientModule implements IClientModule {

    @Override
    public void registerClient() {
        List<Block> ladders = new ArrayList<>(VariantLaddersModule.variantLadders);
        ladders.add(VariantLaddersModule.ironLadder);

        ladders.forEach(ladder -> MMORenderLayers.setBlockRenderType(ladder, RenderLayer.getCutout()));
    }
}
