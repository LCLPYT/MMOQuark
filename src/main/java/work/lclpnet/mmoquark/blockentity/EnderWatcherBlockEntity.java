package work.lclpnet.mmoquark.blockentity;

import net.minecraft.block.entity.BlockEntity;
import work.lclpnet.mmoquark.module.EnderWatcherModule;

// Note: Tickable is currently disabled, because the logic performance is horrible
public class EnderWatcherBlockEntity extends BlockEntity /*implements Tickable*/ {

    public EnderWatcherBlockEntity() {
        super(EnderWatcherModule.enderWatcherType);
    }

    /*@Override // do something for performance before enable, e.g. angle check or something like that
    public void tick() {
        if (world == null) return;

        BlockState state = getCachedState();
        boolean wasLooking = state.get(EnderWatcherBlock.WATCHED);
        int currWatch = state.get(EnderWatcherBlock.POWER);
        int range = 80;

        int newWatch = 0;
        List<PlayerEntity> players = world.getNonSpectatingEntities(PlayerEntity.class, new Box(pos.add(-range, -range, -range), pos.add(range, range, range)));

        boolean looking = false;
        for (PlayerEntity player : players) {
            ItemStack helm = player.getEquippedStack(EquipmentSlot.HEAD);
            if (!helm.isEmpty() && helm.getItem() == Items.PUMPKIN) continue;

            HitResult result = RayCastHandler.rayTrace(player, world, player, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, 64);
            if(result instanceof BlockHitResult && ((BlockHitResult) result).getBlockPos().equals(pos)) {
                looking = true;

                Vec3d vec = result.getPos();
                Direction dir = ((BlockHitResult) result).getSide();
                double x = Math.abs(vec.x - pos.getX() - 0.5) * (1 - Math.abs(dir.getOffsetX()));
                double y = Math.abs(vec.y - pos.getY() - 0.5) * (1 - Math.abs(dir.getOffsetY()));
                double z = Math.abs(vec.z - pos.getZ() - 0.5) * (1 - Math.abs(dir.getOffsetZ()));

                // 0.7071067811865476 being the hypotenuse of an isosceles triangle with cathetus of length 0.5
                double fract = 1 - (Math.sqrt(x*x + y*y + z*z) / 0.7071067811865476);
                newWatch = Math.max(newWatch, (int) Math.ceil(fract * 15));
            }
        }

        if (!world.isClient && (looking != wasLooking || currWatch != newWatch))
            world.setBlockState(pos, world.getBlockState(pos).with(EnderWatcherBlock.WATCHED, looking).with(EnderWatcherBlock.POWER, newWatch), 1 | 2);

        if (looking) {
            double x = pos.getX() - 0.1 + Math.random() * 1.2;
            double y = pos.getY() - 0.1 + Math.random() * 1.2;
            double z = pos.getZ() - 0.1 + Math.random() * 1.2;

            world.addParticle(new DustParticleEffect(1.0F, 0.0F, 0.0F, 1.0F), x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }*/
}
