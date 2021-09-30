package work.lclpnet.mmoblocks.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmoblocks.MMOBlocks;

import java.util.Objects;

public class MMOBlockRegistrar {

    protected final AbstractBlock.Settings settings;
    protected boolean slab = false, stairs = false, wall = false;
    protected ItemGroup group = ItemGroup.BUILDING_BLOCKS;

    public MMOBlockRegistrar(AbstractBlock.Settings settings) {
        this.settings = Objects.requireNonNull(settings);
    }

    public MMOBlockRegistrar setItemGroup(ItemGroup group) {
        this.group = Objects.requireNonNull(group);
        return this;
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

    public void register(String name) {
        final Identifier blockId = new Identifier(MMOBlocks.MOD_ID, name);
        MMOBlock block = new MMOBlock(settings);
        Registry.register(Registry.BLOCK, blockId, block);
        Registry.register(Registry.ITEM, blockId, new BlockItem(block, new FabricItemSettings().group(group)));

        if (slab) {
            final Identifier slabId = new Identifier(MMOBlocks.MOD_ID, name + "_slab");
            MMOSlabBlock slab = new MMOSlabBlock(block);
            Registry.register(Registry.BLOCK, slabId, slab);
            Registry.register(Registry.ITEM, slabId, new BlockItem(slab, new FabricItemSettings().group(group)));
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
            Registry.register(Registry.ITEM, wallId, new BlockItem(wall, new FabricItemSettings().group(group)));
        }
//        if (verticalSlab) {
//
//        }
    }
}
