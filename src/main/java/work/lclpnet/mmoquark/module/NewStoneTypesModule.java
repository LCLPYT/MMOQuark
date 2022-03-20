package work.lclpnet.mmoquark.module;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.block.ext.MMOBlock;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.module.content.AbstractVariantTupleResult;
import work.lclpnet.mmoquark.module.content.VariantTuple;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class NewStoneTypesModule implements IModule {

    public static Block limestoneBlock, jasperBlock, shaleBlock, basaltBlock, myaliteBlock;
    public static final List<Block> myaliteBlocks = new ArrayList<>();
    public static final List<BlockItem> myaliteItems = new ArrayList<>();

    public static Map<Block, Block> polishedBlocks = Maps.newHashMap();

    @Override
    public void register() {
        makeStone("calcite", MapColor.TERRACOTTA_WHITE, MMOBlock::new, Blocks.CALCITE);
        makeStone("dripstone", MapColor.TERRACOTTA_BROWN, MMOBlock::new, Blocks.DRIPSTONE_BLOCK);
        makeStone("tuff", MapColor.TERRACOTTA_GRAY, MMOBlock::new, Blocks.TUFF);

        limestoneBlock = makeStone("limestone", MapColor.STONE_GRAY).normal.block();
        jasperBlock = makeStone("jasper", MapColor.TERRACOTTA_RED).normal.block();
        shaleBlock = makeStone("shale", MapColor.PALE_PURPLE).normal.block();
        basaltBlock = makeStone("basalt", MapColor.BLACK).normal.block();

        Result myalite = makeStone("myalite", MapColor.PURPLE);
        myaliteBlock = myalite.normal.block();
        myaliteBlocks.addAll(myalite.getAllBlocks());
        myaliteItems.addAll(myalite.getAllItems());
    }

    private Result makeStone(String name, MapColor color) {
        return makeStone(name, color, MMOBlock::new, null);
    }

    private Result makeStone(String name, MapColor color, Function<AbstractBlock.Settings, Block> constr, @Nullable Block useNormal) {
        AbstractBlock.Settings settings = FabricBlockSettings.of(Material.STONE, color)
                .requiresTool()
                .strength(1.5F, 6.0F);

        Block normal = useNormal != null ? useNormal : constr.apply(settings);
        Block polished = constr.apply(settings);
        polishedBlocks.put(normal, polished);

        MMOBlockRegistrar.Result normalRes = new MMOBlockRegistrar(normal)
                .withSlab().withVerticalSlab().withWall().withStairs()
                .register(MMOQuark.identifier(name), ItemGroup.BUILDING_BLOCKS, Functions.identity(), useNormal == null);

        MMOBlockRegistrar.Result polishedRes = new MMOBlockRegistrar(polished)
                .withSlab().withVerticalSlab().withStairs()
                .register(MMOQuark.identifier("polished_%s", name));

        return new Result(
                new VariantTuple(normal, normalRes),
                new VariantTuple(polished, polishedRes)
        );
    }

    private static class Result extends AbstractVariantTupleResult {

        public final VariantTuple normal, polished;

        private Result(VariantTuple normal, VariantTuple polished) {
            this.normal = normal;
            this.polished = polished;
        }

        @Override
        protected Set<VariantTuple> getVariantTuples() {
            return ImmutableSet.of(normal, polished);
        }
    }
}
