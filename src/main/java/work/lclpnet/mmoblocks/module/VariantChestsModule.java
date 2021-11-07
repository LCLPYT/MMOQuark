package work.lclpnet.mmoblocks.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.block.MMOBlockRegistrar;
import work.lclpnet.mmoblocks.block.VariantChestBlock;
import work.lclpnet.mmoblocks.block.VariantTrappedChestBlock;
import work.lclpnet.mmoblocks.blockentity.VariantChestBlockEntity;
import work.lclpnet.mmoblocks.blockentity.VariantTrappedChestBlockEntity;
import work.lclpnet.mmoblocks.blockentity.renderer.VariantChestBlockEntityRenderer;
import work.lclpnet.mmoblocks.util.MiscUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class VariantChestsModule implements IModule, IClientModule {

    public static final List<Block> VARIANT_CHESTS = new ArrayList<>(),
            VARIANT_TRAPPED_CHESTS = new ArrayList<>();

    public static BlockEntityType<VariantChestBlockEntity> VARIANT_CHEST_ENTITY;
    public static BlockEntityType<VariantTrappedChestBlockEntity> VARIANT_TRAPPED_CHEST_ENTITY;

    @Override
    public void register() {
        addChests(this::addChest);
        addChests(this::addTrappedChest);

        VARIANT_CHEST_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MMOBlocks.identifier("variant_chest"),
                BlockEntityType.Builder.create(VariantChestBlockEntity::new, VARIANT_CHESTS.toArray(new Block[] {})).build(null));
        VARIANT_TRAPPED_CHEST_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MMOBlocks.identifier("variant_trapped_chest"),
                BlockEntityType.Builder.create(VariantTrappedChestBlockEntity::new, VARIANT_TRAPPED_CHESTS.toArray(new Block[] {})).build(null));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void registerClient() {
        BlockEntityRendererRegistry.INSTANCE.register(VARIANT_CHEST_ENTITY, VariantChestBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(VARIANT_TRAPPED_CHEST_ENTITY, VariantChestBlockEntityRenderer::new);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void registerSprites(SpriteAtlasTexture atlasTexture, ClientSpriteRegistryCallback.Registry registry) {
        if (atlasTexture.getId().toString().equals("minecraft:textures/atlas/chest.png")) {
            Consumer<Block> consumer = b -> VariantChestBlockEntityRenderer.accept(atlasTexture, registry, b);
            VARIANT_CHESTS.forEach(consumer);
            VARIANT_TRAPPED_CHESTS.forEach(consumer);
        }
    }

    private void addChests(BiConsumer<String, Block> chestRegistrar) {
        Consumer<String> baseChestConsumer = type -> chestRegistrar.accept(type, Blocks.CHEST);
        Arrays.stream(MiscUtil.OVERWORLD_WOOD_TYPES).forEach(baseChestConsumer);
        Arrays.stream(MiscUtil.NETHER_WOOD_TYPES).forEach(baseChestConsumer);
        chestRegistrar.accept("nether_brick", Blocks.NETHER_BRICKS);
        chestRegistrar.accept("purpur", Blocks.PURPUR_BLOCK);
        chestRegistrar.accept("prismarine", Blocks.PRISMARINE);
        chestRegistrar.accept("mushroom", Blocks.RED_MUSHROOM_BLOCK);
    }

    private void addChest(String blockType, Block parent) {
        VariantChestBlock variantChest = new VariantChestBlock(AbstractBlock.Settings.copy(parent), blockType, () -> VARIANT_CHEST_ENTITY);
        VARIANT_CHESTS.add(variantChest);

        new MMOBlockRegistrar(variantChest).register(blockType + "_chest", ItemGroup.DECORATIONS);
    }

    private void addTrappedChest(String blockType, Block parent) {
        VariantTrappedChestBlock variantTrappedChest = new VariantTrappedChestBlock(AbstractBlock.Settings.copy(parent), blockType, () -> VARIANT_CHEST_ENTITY);
        VARIANT_CHESTS.add(variantTrappedChest);

        new MMOBlockRegistrar(variantTrappedChest).register(blockType + "_trapped_chest", ItemGroup.REDSTONE);
    }

    public interface IChestTextureProvider {
        String getChestTexturePath();
        boolean isTrap();
    }
}
