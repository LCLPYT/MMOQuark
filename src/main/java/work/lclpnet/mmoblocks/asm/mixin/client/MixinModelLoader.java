package work.lclpnet.mmoblocks.asm.mixin.client;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import work.lclpnet.mmoblocks.util.MMOSpecialModels;

import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class MixinModelLoader {

    @Inject(
            method = "<init>(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/client/color/block/BlockColors;Lnet/minecraft/util/profiler/Profiler;I)V",
            at = @At(
                    value = "CONSTANT",
                    args = "stringValue=minecraft:trident_in_hand#inventory"
            )
    )
    public void registerSpecialModels(ResourceManager resourceManager, BlockColors blockColors, Profiler profiler, int i, CallbackInfo ci) {
        for (Identifier identifier : MMOSpecialModels.getSpecialModels())
            addModelToCache(identifier);
    }

    @Shadow
    public abstract UnbakedModel getOrLoadModel(Identifier identifier);

    @Shadow
    @Final
    private Map<Identifier, UnbakedModel> unbakedModels;
    @Shadow
    @Final
    private Map<Identifier, UnbakedModel> modelsToBake;

    private void addModelToCache(Identifier locationIn) {
        UnbakedModel model = this.getOrLoadModel(locationIn);
        this.unbakedModels.put(locationIn, model);
        this.modelsToBake.put(locationIn, model);
    }
}
