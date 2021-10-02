package work.lclpnet.mmoblocks.util;

import net.fabricmc.api.EnvType;

import javax.annotation.Nonnull;

public class Env {

    private static EnvType currentEnv = null;

    @Nonnull
    public static EnvType currentEnv() {
        if (currentEnv == null) {
            try {
                Class.forName("net.minecraft.client.gui.screen.TitleScreen");
                currentEnv = EnvType.CLIENT;
            } catch (Exception e) {
                currentEnv = EnvType.SERVER;
            }
        }
        return currentEnv;
    }

    public static boolean isClient() {
        return currentEnv() == EnvType.CLIENT;
    }
}
