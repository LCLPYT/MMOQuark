package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.VariantLadderBlock;
import work.lclpnet.mmoquark.util.MiscUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VariantLaddersModule implements IModule {

    private static final BlockSoundGroup SOUND_GROUP = new BlockSoundGroup(1.0F, 1.0F, SoundEvents.BLOCK_METAL_BREAK, SoundEvents.BLOCK_LADDER_STEP, SoundEvents.BLOCK_METAL_PLACE, SoundEvents.BLOCK_METAL_HIT, SoundEvents.BLOCK_LADDER_FALL);

    public static final List<Block> variantLadders = new ArrayList<>();
    public static VariantLadderBlock ironLadder;

    @Override
    public void register() {
        Arrays.stream(MiscUtil.OVERWORLD_VARIANT_WOOD_TYPES).forEach(this::addLadder);
        Arrays.stream(MiscUtil.NETHER_WOOD_TYPES).forEach(this::addLadder);
        Arrays.stream(MiscUtil.MOD_WOOD_TYPES).forEach(this::addLadder);

        ironLadder = new VariantLadderBlock(FabricBlockSettings.of(Material.DECORATION)
                .hardness(0.8F)
                .resistance(0.8F)
                .sounds(SOUND_GROUP)
                .nonOpaque());
        new MMOBlockRegistrar(ironLadder)
                .register(MMOQuark.identifier("iron_ladder"), ItemGroup.DECORATIONS);
    }

    private void addLadder(String woodType) {
        VariantLadderBlock ladder = new VariantLadderBlock();
        new MMOBlockRegistrar(ladder)
                .register(MMOQuark.identifier("%s_ladder", woodType), ItemGroup.DECORATIONS);

        variantLadders.add(ladder);
    }
}
