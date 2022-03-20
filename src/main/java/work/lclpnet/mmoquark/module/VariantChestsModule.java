package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.util.Env;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.VariantChestBlock;
import work.lclpnet.mmoquark.block.VariantTrappedChestBlock;
import work.lclpnet.mmoquark.blockentity.VariantChestBlockEntity;
import work.lclpnet.mmoquark.blockentity.VariantTrappedChestBlockEntity;
import work.lclpnet.mmoquark.util.MiscUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class VariantChestsModule implements IModule {

    public static final List<VariantChestBlock> VARIANT_CHESTS = new ArrayList<>();
    public static final List<VariantTrappedChestBlock> VARIANT_TRAPPED_CHESTS = new ArrayList<>();

    public static BlockEntityType<VariantChestBlockEntity> VARIANT_CHEST_ENTITY;
    public static BlockEntityType<VariantTrappedChestBlockEntity> VARIANT_TRAPPED_CHEST_ENTITY;

    @Override
    public void register() {
        addChests(this::addChest);
        addChests(this::addTrappedChest);

        VARIANT_CHEST_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MMOQuark.identifier("variant_chest"),
                FabricBlockEntityTypeBuilder.create(VariantChestBlockEntity::new, VARIANT_CHESTS.toArray(new Block[] {})).build(null));
        VARIANT_TRAPPED_CHEST_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, MMOQuark.identifier("variant_trapped_chest"),
                FabricBlockEntityTypeBuilder.create(VariantTrappedChestBlockEntity::new, VARIANT_TRAPPED_CHESTS.toArray(new Block[] {})).build(null));

        if (Env.isClient()) {
            VARIANT_CHESTS.forEach(chest -> chest.displayBlockEntity = new VariantChestBlockEntity(BlockPos.ORIGIN, chest.getDefaultState()));
            VARIANT_TRAPPED_CHESTS.forEach(trappedChest -> trappedChest.displayBlockEntity = new VariantChestBlockEntity(BlockPos.ORIGIN, trappedChest.getDefaultState()));
        }
    }

    private void addChests(BiConsumer<String, Block> chestRegistrar) {
        Consumer<String> baseChestConsumer = type -> chestRegistrar.accept(type, Blocks.CHEST);
        Arrays.stream(MiscUtil.OVERWORLD_WOOD_TYPES).forEach(baseChestConsumer);
        Arrays.stream(MiscUtil.NETHER_WOOD_TYPES).forEach(baseChestConsumer);
        Arrays.stream(MiscUtil.MOD_WOOD_TYPES).forEach(baseChestConsumer);
        chestRegistrar.accept("nether_brick", Blocks.NETHER_BRICKS);
        chestRegistrar.accept("purpur", Blocks.PURPUR_BLOCK);
        chestRegistrar.accept("prismarine", Blocks.PRISMARINE);
        chestRegistrar.accept("mushroom", Blocks.RED_MUSHROOM_BLOCK);
    }

    private void addChest(String blockType, Block parent) {
        VariantChestBlock variantChest = new VariantChestBlock(AbstractBlock.Settings.copy(parent), () -> VARIANT_CHEST_ENTITY);
        VARIANT_CHESTS.add(variantChest);

        new MMOBlockRegistrar(variantChest)
                .register(MMOQuark.identifier("%s_chest", blockType), ItemGroup.DECORATIONS);
    }

    private void addTrappedChest(String blockType, Block parent) {
        VariantTrappedChestBlock variantTrappedChest = new VariantTrappedChestBlock(AbstractBlock.Settings.copy(parent), () -> VARIANT_CHEST_ENTITY);
        VARIANT_TRAPPED_CHESTS.add(variantTrappedChest);

        new MMOBlockRegistrar(variantTrappedChest)
                .register(MMOQuark.identifier("%s_trapped_chest", blockType), ItemGroup.REDSTONE);
    }
}
