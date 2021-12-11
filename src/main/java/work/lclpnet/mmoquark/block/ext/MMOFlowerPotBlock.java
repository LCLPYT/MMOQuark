package work.lclpnet.mmoquark.block.ext;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import work.lclpnet.mmoquark.util.Env;
import work.lclpnet.mmoquark.util.MMORenderLayers;

public class MMOFlowerPotBlock extends FlowerPotBlock implements IMMOBlock {

    public MMOFlowerPotBlock(Block content, Settings settings) {
        super(content, settings);

        if (Env.isClient()) registerRenderLayer();
    }

    @Environment(EnvType.CLIENT)
    protected void registerRenderLayer() {
        MMORenderLayers.setBlockRenderType(this, RenderLayer.getCutout());
    }

    @Override
    public BlockItem provideBlockItem(Item.Settings settings) {
        return null;
    }
}
