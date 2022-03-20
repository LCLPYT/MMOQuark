package work.lclpnet.mmoquark.util;

import net.minecraft.recipe.Ingredient;
import net.minecraft.util.math.Direction;

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

    public static final String[] MOD_WOOD_TYPES = new String[] {
            "azalea",
            "blossom"
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
}
