package work.lclpnet.mmoblocks.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;

public class MiscUtil {

    public static final String[] OVERWORLD_VARIANT_WOOD_TYPES = new String[] {
            "spruce",
            "birch",
            "jungle",
            "acacia",
            "dark_oak"
    };

    public static final String[] OVERWORLD_WOOD_TYPES = new String[] {
            "oak",
            "spruce",
            "birch",
            "jungle",
            "acacia",
            "dark_oak"
    };

    public static final String[] NETHER_WOOD_TYPES = new String[] {
            "crimson",
            "warped"
    };

    public static final Direction[] HORIZONTALS = new Direction[] {
            Direction.NORTH,
            Direction.SOUTH,
            Direction.WEST,
            Direction.EAST
    };

    public static Ingredient mergeIngredients(Collection<Ingredient> parts) {
        return Ingredient.ofEntries(parts.stream().flatMap(i -> Arrays.stream(i.entries)));
    }

    @Nullable
    public static String getRegistryPath(Block block) {
        Identifier key = Registry.BLOCK.getId(block);
        String path = key.getPath();

        // check if block is in registry
        if (!block.equals(Blocks.AIR) && path.equals("air") && key.getNamespace().equals("minecraft")) return null;
        else return path;
    }
}
