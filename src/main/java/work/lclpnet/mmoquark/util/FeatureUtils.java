package work.lclpnet.mmoquark.util;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class FeatureUtils {

    /**
     * Creates a {@link ConfiguredFeature} from a {@link Feature} and a {@link FeatureConfig}.
     * @param feature The feature to configure.
     * @param config The config to configure the feature with.
     * @param <FC> The {@link FeatureConfig} type.
     * @param <F> The {@link Feature} type.
     * @return A {@link ConfiguredFeature}.
     */
    public static <FC extends FeatureConfig, F extends Feature<FC>> ConfiguredFeature<FC, F> configure(
            F feature, FC config
    ) {
        return new ConfiguredFeature<>(feature, config);
    }

    /**
     * Registers a given {@link ConfiguredFeature}.
     * @param identifier The identifier for the configured feature.
     * @param configuredFeature The configured feature.
     * @param <FC> Type of the {@link FeatureConfig}.
     * @param <F> Type of the {@link Feature}.
     * @return A {@link RegistryEntry} with the registered configured feature.
     */
    public static <FC extends FeatureConfig, F extends Feature<FC>> RegistryEntry<ConfiguredFeature<?, ?>> register(
            Identifier identifier, ConfiguredFeature<FC, F> configuredFeature
    ) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_FEATURE, identifier, configuredFeature);
    }
}
