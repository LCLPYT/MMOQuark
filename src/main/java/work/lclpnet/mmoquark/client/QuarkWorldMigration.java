package work.lclpnet.mmoquark.client;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import work.lclpnet.mcct.transform.ChunkTransformer;
import work.lclpnet.mcct.transform.MCCT;
import work.lclpnet.mcct.transform.impl.StringFindReplaceChunkTransformer;

import static work.lclpnet.mmoquark.MMOQuark.identifier;

/**
 * Class used to manage MCCT Transformers, which can be used to migrate worlds to newer versions.
 * I do realize that there is DFU for this, but the MCCT migration can be used on demand and is very simple.
 * @author LCLP
 */
@Environment(EnvType.CLIENT)
public class QuarkWorldMigration {

    public static void init() {
        final ChunkTransformer.Builder builder = new ChunkTransformer.Builder();

        ImmutableMap.of(
                // identifier_from, identifier_to; subsequent pairs are expected
                identifier("chiseled_slate_bricks"), identifier("chiseled_shale_bricks")
                // replacements end
        ).forEach((find, replace) -> {
            // every replacer will search for the corresponding find string in the world NBT
            StringFindReplaceChunkTransformer replacer = new StringFindReplaceChunkTransformer(find.toString(), replace.toString());
            builder.addTransformation(replacer);
        });

        MCCT.registerTransformer(builder.create());
    }
}
