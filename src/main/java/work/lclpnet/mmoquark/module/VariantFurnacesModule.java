package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.registry.Registry;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.SoulFurnaceBlock;
import work.lclpnet.mmoquark.block.VariantFurnaceBlock;
import work.lclpnet.mmoquark.blockentity.VariantFurnaceBlockEntity;

import java.util.function.ToIntFunction;

public class VariantFurnacesModule implements IModule {

    public static BlockEntityType<VariantFurnaceBlockEntity> blockEntityType;

    public static Block deepslateFurnace, blackstoneFurnace;

    @Override
    public void register() {
        deepslateFurnace = new VariantFurnaceBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE)
                .luminance(furnaceLuminance()));

        new MMOBlockRegistrar(deepslateFurnace)
                .register(MMOQuark.identifier("deepslate_furnace"), ItemGroup.DECORATIONS);

        blackstoneFurnace = new SoulFurnaceBlock(AbstractBlock.Settings.copy(Blocks.BLACKSTONE)
                .luminance(furnaceLuminance()));

        new MMOBlockRegistrar(blackstoneFurnace)
                .register(MMOQuark.identifier("blackstone_furnace"), ItemGroup.DECORATIONS);

        blockEntityType = Registry.register(Registry.BLOCK_ENTITY_TYPE, MMOQuark.identifier("variant_furnace"),
                FabricBlockEntityTypeBuilder.create(VariantFurnaceBlockEntity::new, deepslateFurnace, blackstoneFurnace).build(null));
    }

    private static ToIntFunction<BlockState> furnaceLuminance() {
        return s -> s.get(Properties.LIT) ? 13 : 0;
    }
}
