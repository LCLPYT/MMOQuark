package work.lclpnet.mmoblocks.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;

import javax.annotation.Nullable;
import java.util.Objects;

public class MMOBlockRegistrar {

    protected final Block block;
    protected boolean slab = false, stairs = false, wall = false, verticalSlab = false, pane = false;
    protected Item item = null;

    public MMOBlockRegistrar(Block block) {
        this.block = Objects.requireNonNull(block);
    }

    public MMOBlockRegistrar(AbstractBlock.Settings settings) {
        this(new MMOBlock(Objects.requireNonNull(settings)));
    }

    public MMOBlockRegistrar withSlab() {
        this.slab = true;
        return this;
    }

    public MMOBlockRegistrar withStairs() {
        this.stairs = true;
        return this;
    }

    public MMOBlockRegistrar withWall() {
        this.wall = true;
        return this;
    }

    public MMOBlockRegistrar withVerticalSlab() {
        this.verticalSlab = true;
        return this;
    }

    public MMOBlockRegistrar withPane() {
        this.pane = true;
        return this;
    }

    public void register(String name) {
        register(name, ItemGroup.BUILDING_BLOCKS);
    }

    public void register(String name, ItemGroup group) {
        final Identifier blockId = new Identifier(MMOBlocks.MOD_ID, name);
        Registry.register(Registry.BLOCK, blockId, block);

        final FabricItemSettings blockItemSettings = new FabricItemSettings().group(group);
        item = block instanceof IMMOBlock ? ((IMMOBlock) block).provideBlockItem(blockItemSettings) : new BlockItem(block, blockItemSettings);
        Registry.register(Registry.ITEM, blockId, item);

        if (slab || verticalSlab) {
            MMOSlabBlock slabBlock = new MMOSlabBlock(block);
            if (slab) {
                final Identifier slabId = new Identifier(MMOBlocks.MOD_ID, name + "_slab");
                Registry.register(Registry.BLOCK, slabId, slabBlock);
                Registry.register(Registry.ITEM, slabId, new BlockItem(slabBlock, new FabricItemSettings().group(group)));
            }
            if (verticalSlab) {
                final Identifier verticalSlabId = new Identifier(MMOBlocks.MOD_ID, name + "_vertical_slab");
                VerticalSlabBlock verticalSlab = new VerticalSlabBlock(slabBlock);
                Registry.register(Registry.BLOCK, verticalSlabId, verticalSlab);
                Registry.register(Registry.ITEM, verticalSlabId, new BlockItem(verticalSlab, new FabricItemSettings().group(group)));
            }
        }
        if (stairs) {
            final Identifier stairsId = new Identifier(MMOBlocks.MOD_ID, name + "_stairs");
            MMOStairsBlock stairs = new MMOStairsBlock(block);
            Registry.register(Registry.BLOCK, stairsId, stairs);
            Registry.register(Registry.ITEM, stairsId, new BlockItem(stairs, new FabricItemSettings().group(group)));
        }
        if (wall) {
            final Identifier wallId = new Identifier(MMOBlocks.MOD_ID, name + "_wall");
            MMOWallBlock wall = new MMOWallBlock(block);
            Registry.register(Registry.BLOCK, wallId, wall);
            Registry.register(Registry.ITEM, wallId, new BlockItem(wall, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
        }
        if (pane) {
            final Identifier paneId = new Identifier(MMOBlocks.MOD_ID, name + "_pane");
            MMOInheritedPaneBlock pane = new MMOInheritedPaneBlock(block);
            Registry.register(Registry.BLOCK, paneId, pane);
            Registry.register(Registry.ITEM, paneId, new BlockItem(pane, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
        }
    }

    @Nullable
    public Item getItem() {
        return item;
    }
}
