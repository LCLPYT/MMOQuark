package work.lclpnet.mmoblocks.blockentity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.Block;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.module.WoodExtraModule;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class VariantChestBlockEntityRenderer<T extends ChestBlockEntity> extends GenericChestBERenderer<T> {

    private static final Map<Block, ChestTextureBatch> chestTextures = new HashMap<>();

    public static Block invBlock = null;

    public VariantChestBlockEntityRenderer(BlockEntityRenderDispatcher disp) {
        super(disp);
    }

    @Override
    public SpriteIdentifier getMaterial(T chestBlockEntity, ChestType type) {
        Block block = invBlock;
        if (block == null) block = chestBlockEntity.getCachedState().getBlock();

        ChestTextureBatch batch = chestTextures.get(block);
        if(batch == null) return null;

        switch(type) {
            case LEFT: return batch.left;
            case RIGHT: return batch.right;
            default: return batch.normal;
        }
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        super.render(entity, tickDelta, matrices, vertexConsumers, light, overlay);
    }

    public static void accept(SpriteAtlasTexture atlasTexture, ClientSpriteRegistryCallback.Registry registry, Block chest) {
        Identifier atlas = atlasTexture.getId();

        if(chest instanceof WoodExtraModule.IChestTextureProvider) {
            WoodExtraModule.IChestTextureProvider prov = (WoodExtraModule.IChestTextureProvider) chest;

            String path = prov.getChestTexturePath();
            if(!prov.isTrap()) add(registry, atlas, chest, path, "normal", "left", "right");
            else add(registry, atlas, chest, path, "trap", "trap_left", "trap_right");
        }
    }

    private static void add(ClientSpriteRegistryCallback.Registry registry, Identifier atlas, Block chest, String path, String normal, String left, String right) {
        Identifier resNormal = MMOBlocks.identifier(path + normal);
        Identifier resLeft = MMOBlocks.identifier(path + left);
        Identifier resRight = MMOBlocks.identifier(path + right);

        ChestTextureBatch batch = new ChestTextureBatch(atlas, resNormal, resLeft, resRight);
        chestTextures.put(chest, batch);

        registry.register(resNormal);
        registry.register(resLeft);
        registry.register(resRight);
    }

    private static class ChestTextureBatch {
        public final SpriteIdentifier normal, left, right;

        public ChestTextureBatch(Identifier atlas, Identifier normal, Identifier left, Identifier right) {
            this.normal = new SpriteIdentifier(atlas, normal);
            this.left = new SpriteIdentifier(atlas, left);
            this.right = new SpriteIdentifier(atlas, right);
        }

    }
}
