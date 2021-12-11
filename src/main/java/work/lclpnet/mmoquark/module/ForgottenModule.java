package work.lclpnet.mmoquark.module;

import net.minecraft.item.ItemGroup;
import work.lclpnet.mmoquark.item.ForgottenHatItem;
import work.lclpnet.mmoquark.item.MMOItemRegistrar;

public class ForgottenModule implements IModule {

    @Override
    public void register() {
        new MMOItemRegistrar(ForgottenHatItem::new)
                .register("forgotten_hat", ItemGroup.COMBAT);
    }
}
