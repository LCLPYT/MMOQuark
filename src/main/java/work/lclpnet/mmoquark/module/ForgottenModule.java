package work.lclpnet.mmoquark.module;

import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.item.MMOItemRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.item.ForgottenHatItem;

public class ForgottenModule implements IModule {

    @Override
    public void register() {
        new MMOItemRegistrar(ForgottenHatItem::new)
                .register(MMOQuark.identifier("forgotten_hat"), ItemGroup.COMBAT);
    }
}
