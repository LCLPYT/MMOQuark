package work.lclpnet.mmoquark.util;

import net.minecraft.block.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.SignType;
import net.minecraft.util.math.Direction;
import work.lclpnet.mmocontent.block.MMOAdditionalSigns;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmocontent.block.ext.MMOBlock;
import work.lclpnet.mmocontent.block.ext.MMOPillarBlock;
import work.lclpnet.mmocontent.block.ext.MMOSignBlock;
import work.lclpnet.mmocontent.block.ext.MMOWallSignBlock;
import work.lclpnet.mmocontent.entity.MMOBoatType;
import work.lclpnet.mmocontent.item.MMOBoatItem;
import work.lclpnet.mmocontent.item.MMOItemRegistrar;
import work.lclpnet.mmoquark.MMOQuark;

import java.util.Objects;

import static work.lclpnet.mmocontent.util.MMOUtil.registerStrippedBlock;
import static work.lclpnet.mmoquark.MMOQuark.identifier;

public class WoodGroupUtil {

    public static WoodGroupHolder registerWoodGroup(String name, MapColor woodTopColor, MapColor woodSideColor) {
        MMOPillarBlock log = createLogBlock(woodTopColor, woodSideColor);
        new MMOBlockRegistrar(log).register(identifier("%s_log", name));

        MMOPillarBlock wood = new MMOPillarBlock(AbstractBlock.Settings.of(Material.WOOD, woodTopColor)
                .strength(2.0F)
                .sounds(BlockSoundGroup.WOOD));
        new MMOBlockRegistrar(wood).register(identifier("%s_wood", name));

        MMOPillarBlock strippedLog = createLogBlock(woodTopColor, woodTopColor);
        new MMOBlockRegistrar(strippedLog).register(identifier("stripped_%s_log", name));

        MMOPillarBlock strippedWood = new MMOPillarBlock(AbstractBlock.Settings.of(Material.WOOD, woodTopColor)
                .strength(2.0F)
                .sounds(BlockSoundGroup.WOOD));
        new MMOBlockRegistrar(strippedWood).register(identifier("stripped_%s_wood", name));

        registerStrippedBlock(log, strippedLog);
        registerStrippedBlock(wood, strippedWood);

        MMOBlock planks = new MMOBlock(AbstractBlock.Settings.of(Material.WOOD, woodTopColor)
                .strength(2.0F, 3.0F)
                .sounds(BlockSoundGroup.WOOD));
        MMOBlockRegistrar.Result result = new MMOBlockRegistrar(planks)
                .withStairs().withSlab().withVerticalSlab()
                .withFence(ItemGroup.DECORATIONS)
                .withFenceGate(ItemGroup.REDSTONE)
                .withDoor(ItemGroup.REDSTONE)
                .withTrapdoor(ItemGroup.REDSTONE)
                .withPressurePlate(ItemGroup.REDSTONE, PressurePlateBlock.ActivationRule.EVERYTHING)
                .withButton(ItemGroup.REDSTONE, true)
                .register(identifier(name), ItemGroup.BUILDING_BLOCKS, basePath -> basePath.concat("_planks"));

        SignType signType = MMOAdditionalSigns.registerSignType(name, MMOQuark::identifier);

        MMOSignBlock sign = new MMOSignBlock(AbstractBlock.Settings.of(Material.WOOD)
                .noCollision()
                .strength(1.0F)
                .sounds(BlockSoundGroup.WOOD), signType);
        new MMOBlockRegistrar(sign)
                .register(identifier("%s_sign", name));

        MMOWallSignBlock wallSign = new MMOWallSignBlock(AbstractBlock.Settings.of(Material.WOOD)
                .noCollision()
                .strength(1.0F)
                .sounds(BlockSoundGroup.WOOD)
                .dropsLike(sign), signType);
        new MMOBlockRegistrar(wallSign)
                .register(identifier("%s_wall_sign", name));

        MMOAdditionalSigns.registerAdditionalSign(sign, wallSign);

        new MMOItemRegistrar(settings -> new SignItem(settings.maxCount(16), sign, wallSign))
                .register(identifier("%s_sign", name), ItemGroup.DECORATIONS);

        MMOBoatType boatType = MMOBoatType.register(identifier(name), planks);

        final MMOBoatItem boatItem = (MMOBoatItem) new MMOItemRegistrar(settings -> new MMOBoatItem(boatType, settings.maxCount(1)))
                .register(identifier("%s_boat", name), ItemGroup.TRANSPORTATION);

        boatType.boatItem = boatItem;

        return new WoodGroupHolder(log, wood, strippedLog, strippedWood, planks, result, boatType, boatItem);
    }

    private static MMOPillarBlock createLogBlock(MapColor topMaterialColor, MapColor sideMaterialColor) {
        return new MMOPillarBlock(AbstractBlock.Settings.of(Material.WOOD,
                        (blockState) -> blockState.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMaterialColor : sideMaterialColor)
                .strength(2.0F)
                .sounds(BlockSoundGroup.WOOD));
    }

    public static class WoodGroupHolder {
        public final MMOPillarBlock log, wood, strippedLog, strippedWood;
        public final MMOBlock planks;
        public final StairsBlock stairs;
        public final SlabBlock slab;
        public final FenceBlock fence;
        public final FenceGateBlock fenceGate;
        public final DoorBlock door;
        public final TrapdoorBlock trapdoor;
        public final PressurePlateBlock pressurePlate;
        public final AbstractButtonBlock button;
        public final MMOBoatType boatType;
        public final MMOBoatItem boatItem;

        public WoodGroupHolder(MMOPillarBlock log, MMOPillarBlock wood,
                               MMOPillarBlock strippedLog, MMOPillarBlock strippedWood, MMOBlock planks,
                               MMOBlockRegistrar.Result result, MMOBoatType boatType, MMOBoatItem boatItem) {
            this.log = log;
            this.wood = wood;
            this.strippedLog = strippedLog;
            this.strippedWood = strippedWood;
            this.planks = planks;
            this.stairs = Objects.requireNonNull(result.stairs()).block();
            this.slab = Objects.requireNonNull(result.slab()).block();
            this.fence = Objects.requireNonNull(result.fence()).block();
            this.fenceGate = Objects.requireNonNull(result.fenceGate()).block();
            this.door = Objects.requireNonNull(result.door()).block();
            this.trapdoor = Objects.requireNonNull(result.trapdoor()).block();
            this.pressurePlate = Objects.requireNonNull(result.pressurePlate()).block();
            this.button = Objects.requireNonNull(result.button()).block();
            this.boatType = boatType;
            this.boatItem = boatItem;
        }
    }
}
