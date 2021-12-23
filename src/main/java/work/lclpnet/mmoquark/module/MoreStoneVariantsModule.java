package work.lclpnet.mmoquark.module;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.BlockItem;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.block.ext.MMOBlock;
import work.lclpnet.mmocontent.block.ext.MMOPillarBlock;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.module.content.AbstractVariantTupleResult;
import work.lclpnet.mmoquark.module.content.VariantTuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class MoreStoneVariantsModule implements IModule {

    public static final List<Block> myaliteVariationBlocks = new ArrayList<>();
    public static final List<BlockItem> myaliteVariationItems = new ArrayList<>();

    @Override
    public void register() {
        add("granite", MaterialColor.DIRT);
        add("diorite", MaterialColor.QUARTZ);
        add("andesite", MaterialColor.STONE);

        add("marble", MaterialColor.QUARTZ);
        add("limestone", MaterialColor.STONE);
        add("jasper", MaterialColor.RED_TERRACOTTA);
        add("slate", MaterialColor.ICE);
        add("basalt", MaterialColor.BLACK);

        Result myalite = add("myalite", MaterialColor.PURPLE);
        myaliteVariationBlocks.addAll(myalite.getAllBlocks());
        myaliteVariationItems.addAll(myalite.getAllItems());
    }

    private Result add(String name, MaterialColor color) {
        return add(name, color, MMOBlock::new, MMOPillarBlock::new);
    }

    private Result add(String name, MaterialColor color, Function<AbstractBlock.Settings, MMOBlock> constr, Function<AbstractBlock.Settings, MMOPillarBlock> pillarConstr) {
        AbstractBlock.Settings settings = FabricBlockSettings.of(Material.STONE, color)
                .requiresTool()
                .breakByTool(FabricToolTags.PICKAXES)
                .strength(1.5F, 6.0F);

        MMOBlock bricks = constr.apply(settings);
        MMOBlockRegistrar.Result bricksExtra = new MMOBlockRegistrar(bricks)
                .withStairs().withSlab().withVerticalSlab().withWall()
                .register(MMOQuark.identifier(String.format("%s_bricks", name)));

        MMOBlock chiseledBricks = constr.apply(settings);
        MMOBlockRegistrar.Result chiseledBricksExtra = new MMOBlockRegistrar(chiseledBricks)
                .register(MMOQuark.identifier(String.format("chiseled_%s_bricks", name)));

        MMOBlock pavement = constr.apply(settings);
        MMOBlockRegistrar.Result pavementExtra = new MMOBlockRegistrar(pavement)
                .register(MMOQuark.identifier(String.format("%s_pavement", name)));

        MMOPillarBlock pillar = pillarConstr.apply(settings);
        MMOBlockRegistrar.Result pillarExtra = new MMOBlockRegistrar(pillar)
                .register(MMOQuark.identifier(String.format("%s_pillar", name)));

        return new Result(
                new VariantTuple(bricks, bricksExtra),
                new VariantTuple(chiseledBricks, chiseledBricksExtra),
                new VariantTuple(pavement, pavementExtra),
                new VariantTuple(pillar, pillarExtra)
        );
    }

    private static class Result extends AbstractVariantTupleResult {

        public final VariantTuple bricks, chiseledBricks, pavement, pillar;

        private Result(VariantTuple bricks, VariantTuple chiseledBricks, VariantTuple pavement, VariantTuple pillar) {
            this.bricks = bricks;
            this.chiseledBricks = chiseledBricks;
            this.pavement = pavement;
            this.pillar = pillar;
        }

        @Override
        protected Set<VariantTuple> getVariantTuples() {
            return ImmutableSet.of(bricks, chiseledBricks, pavement, pillar);
        }
    }
}
