package work.lclpnet.mmoquark.module;

import net.minecraft.block.MapColor;
import work.lclpnet.mmocontent.entity.MMOBoatEntity;
import work.lclpnet.mmoquark.util.WoodGroupUtil;

public class AzaleaWoodModule implements IModule {

    public static WoodGroupUtil.WoodGroupHolder azaleaWood;

    @Override
    public void register() {
        MMOBoatEntity.enableMMOBoatIntegration();
        azaleaWood = WoodGroupUtil.registerWoodGroup("azalea", MapColor.LIME, MapColor.BROWN);
    }
}
