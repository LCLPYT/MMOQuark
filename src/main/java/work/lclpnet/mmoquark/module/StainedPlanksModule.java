package work.lclpnet.mmoquark.module;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.block.ext.MMOBlock;
import work.lclpnet.mmoquark.MMOQuark;

public class StainedPlanksModule implements IModule {

    @Override
    public void register() {
        ImmutableSet.of(DyeColor.WHITE, DyeColor.ORANGE, DyeColor.PINK, DyeColor.GRAY, DyeColor.CYAN, DyeColor.BROWN, DyeColor.BLACK)
                .forEach(dyeColor -> new MMOBlockRegistrar(new MMOBlock(AbstractBlock.Settings.of(Material.WOOD, dyeColor.getMapColor())
                        .strength(2.0F, 3.0F)
                        .sounds(BlockSoundGroup.WOOD)))
                        .withSlab().withStairs().withVerticalSlab()
                        .register(MMOQuark.identifier("%s_stained_planks", dyeColor.getName())));
    }
}
