package work.lclpnet.mmoblocks.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;
import work.lclpnet.mmoblocks.block.*;
import work.lclpnet.mmoblocks.blockentity.VariantChestBlockEntity;
import work.lclpnet.mmoblocks.blockentity.VariantTrappedChestBlockEntity;
import work.lclpnet.mmoblocks.blockentity.renderer.VariantChestBlockEntityRenderer;
import work.lclpnet.mmoblocks.util.MiscUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WoodExtraModule implements IModule, IClientModule {

    public static final List<Block> VARIANT_CHESTS = new ArrayList<>(),
            VARIANT_TRAPPED_CHESTS = new ArrayList<>();

    public static BlockEntityType<VariantChestBlockEntity> VARIANT_CHEST_ENTITY;
    public static BlockEntityType<VariantTrappedChestBlockEntity> VARIANT_TRAPPED_CHEST_ENTITY;

    public static Tag<Block> hedgesTag;

    @Override
    public void register() {
        Arrays.stream(MiscUtil.OVERWORLD_VARIANT_WOOD_TYPES).forEach(this::addVariantStuff);
        Arrays.stream(MiscUtil.NETHER_WOOD_TYPES).forEach(this::addVariantStuff);

        addChests(this::addChest);
        addChests(this::addTrappedChest);

        VARIANT_CHEST_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MMOBlocks.identifier("variant_chest"),
                BlockEntityType.Builder.create(VariantChestBlockEntity::new, WoodExtraModule.VARIANT_CHESTS.toArray(new Block[] {})).build(null));
        VARIANT_TRAPPED_CHEST_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MMOBlocks.identifier("variant_trapped_chest"),
                BlockEntityType.Builder.create(VariantTrappedChestBlockEntity::new, WoodExtraModule.VARIANT_TRAPPED_CHESTS.toArray(new Block[] {})).build(null));

        registerHedges();

        hedgesTag = TagRegistry.block(MMOBlocks.identifier("hedges"));
    }

    private void registerHedges() {
        addHedge(Blocks.OAK_FENCE, Blocks.OAK_LEAVES);
        addHedge(Blocks.BIRCH_FENCE, Blocks.BIRCH_LEAVES);
        addHedge(Blocks.SPRUCE_FENCE, Blocks.SPRUCE_LEAVES);
        addHedge(Blocks.JUNGLE_FENCE, Blocks.JUNGLE_LEAVES);
        addHedge(Blocks.ACACIA_FENCE, Blocks.ACACIA_LEAVES);
        addHedge(Blocks.DARK_OAK_FENCE, Blocks.DARK_OAK_LEAVES);
    }

    private void addHedge(Block fence, Block leaves) {
        String path;
        if (leaves instanceof BlossomLeavesBlock) {
            Identifier key = Registry.BLOCK.getId(leaves);
            String leavesPath = key.getPath();
            if (leavesPath.equals("air") && key.getNamespace().equals("minecraft")) return; // default value, if block does not exist in registry

            path = leavesPath.replaceAll("_blossom_leaves", "_blossom_hedge");
        } else {
            Identifier key = Registry.BLOCK.getId(fence);
            String fencePath = key.getPath();
            if (fencePath.equals("air") && key.getNamespace().equals("minecraft")) return; // default value, if block does not exist in registry

            path = fencePath.replaceAll("_fence", "_hedge");
        }

        new MMOBlockRegistrar(new HedgeBlock(fence, leaves)).register(path, ItemGroup.DECORATIONS);
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
            WoodExtraModule.VARIANT_CHESTS.forEach(consumer);
            WoodExtraModule.VARIANT_TRAPPED_CHESTS.forEach(consumer);
        }
    }

    private void addVariantStuff(String woodType) {
        new MMOBlockRegistrar(AbstractBlock.Settings.copy(Blocks.BOOKSHELF))
                .register(woodType + "_bookshelf", ItemGroup.DECORATIONS);
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
