package work.lclpnet.mmoquark.client;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import work.lclpnet.mcct.transform.ChunkTransformContext;
import work.lclpnet.mcct.transform.ChunkTransformer;
import work.lclpnet.mcct.transform.MCCT;
import work.lclpnet.mcct.transform.impl.StringFindReplaceChunkTransformer;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.stream.Stream;

import static work.lclpnet.mmoquark.MMOQuark.identifier;

/**
 * Class used to manage MCCT Transformers, which can be used to migrate worlds to newer versions.
 * I do realize that there is DFU for this, but the MCCT migration can be used on demand and is very simple.
 * @author LCLP
 */
@Environment(EnvType.CLIENT)
public class QuarkWorldMigration {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void init() {
        final ChunkTransformer.Builder builder = new ChunkTransformer.Builder();

        registerBlockMigrations(builder);

        MCCT.registerTransformer(builder.create());
    }

    private static void registerBlockMigrations(ChunkTransformer.Builder builder) {
        registerStoneMigration(builder, "slate", "shale", null);
        registerStoneMigration(builder, "marble", "calcite", Identifier::new);
    }

    private static void registerStoneMigration(ChunkTransformer.Builder builder, String from, String to, @Nullable Function<String, Identifier> baseMapper) {
        Stream.of(
                "%s",
                "%s_slab",
                "%s_vertical_slab",
                "%s_stairs",
                "%s_wall",
                "polished_%s",
                "polished_%s_slab",
                "polished_%s_vertical_slab",
                "polished_%s_stairs",
                "polished_%s_wall",
                "%s_bricks",
                "%s_bricks_slab",
                "%s_bricks_vertical_slab",
                "%s_bricks_stairs",
                "%s_bricks_wall",
                "chiseled_%s_bricks",
                "%s_pavement",
                "%s_pillar",
                "%s_speleothem"
        ).map(path -> {
            Identifier toId = identifier(path, to);
            if (baseMapper != null && toId.getPath().equals(to))
                toId = baseMapper.apply(to);

            return Pair.of(identifier(path, from), toId);
        }).forEach(replace -> {
            final Identifier fromId = replace.getFirst(), toId = replace.getSecond();
            LOGGER.info("Registering migration {} -> {}", fromId.toString(), toId.toString());
            StringFindReplaceChunkTransformer replacer = new QFindReplaceLiteralTransformer(fromId.toString(), toId.toString());
            builder.addTransformation(replacer);
        });
    }

    public static class QFindReplaceLiteralTransformer extends StringFindReplaceChunkTransformer {

        /**
         * Create a new find and replace transformer.
         * This transformer will find any strings and replaces <code>searchString</code> with <code>replaceValue</code>.
         *
         * @param searchString The string to search; will be replaced with <code>replaceValue</code>.
         * @param replaceValue The string that will be inserted.
         */
        public QFindReplaceLiteralTransformer(String searchString, String replaceValue) {
            super(searchString, replaceValue);
        }

        @Override
        protected void visitString(String string, NbtCompound parent, String key, ChunkTransformContext ctx) {
            if (!string.equals(searchString)) return;

            LOGGER.info("Found {} in {} at x={}, z={}. Replacing with {}", searchString, ctx.region.file().getFileName(), ctx.chunkPos.x, ctx.chunkPos.z, replaceValue);
            parent.putString(key, replaceValue);
            ctx.markDirty();
        }
    }
}
