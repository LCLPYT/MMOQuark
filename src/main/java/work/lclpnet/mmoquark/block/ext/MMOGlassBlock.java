package work.lclpnet.mmoquark.block.ext;

public class MMOGlassBlock extends MMOBlock {

    public MMOGlassBlock(Settings settings) {
        super(settings.nonOpaque()
                .allowsSpawning((state, world, pos, entityType) -> false)
                .solidBlock((state, world, pos) -> false)
                .suffocates((state, world, pos) -> false)
                .blockVision((state, world, pos) -> false));
    }
}
