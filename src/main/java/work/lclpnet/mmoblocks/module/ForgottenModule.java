package work.lclpnet.mmoblocks.module;

import net.minecraft.item.ItemGroup;
import work.lclpnet.mmoblocks.item.ForgottenHatItem;
import work.lclpnet.mmoblocks.item.MMOItemRegistrar;

public class ForgottenModule implements IModule, IClientModule {

    @Override
    public void register() {
        new MMOItemRegistrar(ForgottenHatItem::new)
                .register("forgotten_hat", ItemGroup.TOOLS);
    }
}
