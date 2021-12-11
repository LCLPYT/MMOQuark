package work.lclpnet.mmoquark.asm.mixin.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import work.lclpnet.mmoquark.client.MMOBlockEntityItem;

@Mixin(BuiltinModelItemRenderer.class)
public class MixinBuiltinModelItemRenderer {

    @Inject(
            method = "render(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    public void onRender(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        Item item = stack.getItem();
        if (!(item instanceof MMOBlockEntityItem)) return;

        MMOBlockEntityItem mmoItem = (MMOBlockEntityItem) item;

        mmoItem.beforeRender();
        BlockEntityRenderDispatcher.INSTANCE.renderEntity(mmoItem.getEntity(), matrices, vertexConsumers, light, overlay);
        mmoItem.afterRender();

        ci.cancel();
    }
}
