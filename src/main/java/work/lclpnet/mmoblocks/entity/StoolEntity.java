package work.lclpnet.mmoblocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import work.lclpnet.mmoblocks.block.StoolBlock;

import java.util.List;

public class StoolEntity extends Entity {

    public StoolEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        List<Entity> passengers = getPassengerList();
        boolean dead = passengers.isEmpty();

        BlockPos pos = getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (!dead) {
            if(!(state.getBlock() instanceof StoolBlock)) {
                PistonBlockEntity piston = null;
                boolean didOffset = false;

                BlockEntity tile = world.getBlockEntity(pos);
                if (tile instanceof PistonBlockEntity && ((PistonBlockEntity) tile).getPushedBlock().getBlock() instanceof StoolBlock)
                    piston = (PistonBlockEntity) tile;
                else for (Direction d : Direction.values()) {
                    BlockPos offPos = pos.offset(d);
                    tile = world.getBlockEntity(offPos);

                    if (tile instanceof PistonBlockEntity && ((PistonBlockEntity) tile).getPushedBlock().getBlock() instanceof StoolBlock) {
                        piston = (PistonBlockEntity) tile;
                        break;
                    }
                }

                if(piston != null) {
                    Direction dir = piston.getMovementDirection();
                    move(MovementType.PISTON, new Vec3d((float) dir.getOffsetX() * 0.33, (float) dir.getOffsetY() * 0.33, (float) dir.getOffsetZ() * 0.33));

                    didOffset = true;
                }

                dead = !didOffset;
            }
        }

        if(dead && !world.isClient) {
            remove();

            if(state.getBlock() instanceof StoolBlock)
                world.setBlockState(pos, state.with(StoolBlock.SAT_IN, false));
        }
    }

    @Override
    public double getMountedHeightOffset() {
        return -0.3D;
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Direction direction = this.getMovementDirection();
        if (direction.getAxis() != Direction.Axis.Y) {
            int[][] is = Dismounting.getDismountOffsets(direction);
            BlockPos blockPos = this.getBlockPos();
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for (EntityPose entityPose : passenger.getPoses()) {
                Box box = passenger.getBoundingBox(entityPose);

                for (int[] js : is) {
                    mutable.set(blockPos.getX() + js[0], blockPos.getY(), blockPos.getZ() + js[1]);
                    double d = this.world.getDismountHeight(mutable);
                    if (Dismounting.canDismountInBlock(d)) {
                        Vec3d vec3d = Vec3d.ofCenter(mutable, d);
                        if (Dismounting.canPlaceEntityAt(this.world, passenger, box.offset(vec3d))) {
                            passenger.setPose(entityPose);
                            return vec3d;
                        }
                    }
                }
            }

        }
        return super.updatePassengerForDismount(passenger);
    }

    @Override
    protected void initDataTracker() {}

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {}

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {}

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
