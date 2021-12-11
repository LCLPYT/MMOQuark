package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import work.lclpnet.mmoquark.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.block.VariantLadderBlock;
import work.lclpnet.mmoquark.util.MiscUtil;

import java.util.Arrays;

public class VariantLaddersModule implements IModule {

    private static final BlockSoundGroup SOUND_GROUP = new BlockSoundGroup(1.0F, 1.0F, SoundEvents.BLOCK_METAL_BREAK, SoundEvents.BLOCK_LADDER_STEP, SoundEvents.BLOCK_METAL_PLACE, SoundEvents.BLOCK_METAL_HIT, SoundEvents.BLOCK_LADDER_FALL);

    @Override
    public void register() {
        Arrays.stream(MiscUtil.OVERWORLD_VARIANT_WOOD_TYPES).forEach(this::addLadder);
        Arrays.stream(MiscUtil.NETHER_WOOD_TYPES).forEach(this::addLadder);

        new MMOBlockRegistrar(new VariantLadderBlock(FabricBlockSettings.of(Material.SUPPORTED)
                .hardness(0.8F)
                .resistance(0.8F)
                .sounds(SOUND_GROUP)
                .nonOpaque())
        ).register("iron_ladder", ItemGroup.DECORATIONS);
    }

    private void addLadder(String woodType) {
        new MMOBlockRegistrar(new VariantLadderBlock())
                .register(woodType + "_ladder", ItemGroup.DECORATIONS);
    }
}
