package work.lclpnet.mmoquark.module;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import work.lclpnet.mmocontent.block.MMOBlockRegistrar;
import work.lclpnet.mmoquark.MMOQuark;
import work.lclpnet.mmoquark.block.ext.QVineBlock;

import java.util.Map;

public class BurntVinesModule implements IModule {

    public static Block burnt_vine;
    public static BlockItem burnt_vine_item;

    @Override
    public void register() {
        MMOBlockRegistrar.Result result = new MMOBlockRegistrar(burnt_vine = new QVineBlock())
                .register(MMOQuark.identifier("burnt_vine"), ItemGroup.DECORATIONS);

        burnt_vine_item = result.item;

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() != Items.FLINT_AND_STEEL && stack.getItem() != Items.FIRE_CHARGE) return ActionResult.PASS;

            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() != Blocks.VINE) return ActionResult.PASS;

            BlockState newState = burnt_vine.getDefaultState();
            Map<Direction, BooleanProperty> map = VineBlock.FACING_PROPERTIES;
            for (Direction d : map.keySet()) {
                BooleanProperty prop = map.get(d);
                newState = newState.with(prop, state.get(prop));
            }

            world.setBlockState(pos, newState);

            BlockPos testPos = pos.down();
            BlockState testState = world.getBlockState(testPos);
            while (testState.getBlock() == Blocks.VINE) {
                world.removeBlock(testPos, false);
                testPos = testPos.down();
                testState = world.getBlockState(testPos);
            }

            world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 0.5F, 1F);
            if (world instanceof ServerWorld) {
                ServerWorld sworld = (ServerWorld) world;
                sworld.spawnParticles(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5, 0.25, 0.25, 0.25, 0.01);
                sworld.spawnParticles(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 20, 0.25, 0.25, 0.25, 0.01);
            }
            return ActionResult.SUCCESS;
        });
    }
}
