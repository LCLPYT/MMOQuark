package work.lclpnet.mmoquark.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import work.lclpnet.mmoquark.entity.StonelingEntity;
import work.lclpnet.mmoquark.util.MutableVec3d;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.BooleanSupplier;

public class ActWaryGoal extends WanderAroundFarGoal {

    private final StonelingEntity stoneling;

    private final BooleanSupplier scaredBySuddenMovement;

    private final double range;

    private boolean startled;

    private final Map<PlayerEntity, MutableVec3d> lastPositions = new WeakHashMap<>();
    private final Map<PlayerEntity, MutableVec3d> lastSpeeds = new WeakHashMap<>();

    public ActWaryGoal(StonelingEntity stonelingEntity, double speed, double range, BooleanSupplier scaredBySuddenMovement) {
        super(stonelingEntity, speed, 1F);
        this.stoneling = stonelingEntity;
        this.range = range;
        this.scaredBySuddenMovement = scaredBySuddenMovement;
    }

    private static void updateMotion(MutableVec3d holder, double x, double y, double z) {
        holder.x = x;
        holder.y = y;
        holder.z = z;
    }

    private static void updatePos(MutableVec3d holder, Entity entity) {
        Vec3d pos = entity.getPos();
        holder.x = pos.x;
        holder.y = pos.y;
        holder.z = pos.z;
    }

    private static MutableVec3d initPos(PlayerEntity p) {
        MutableVec3d holder = new MutableVec3d(Vec3d.ZERO);
        updatePos(holder, p);
        return holder;
    }

    public void startle() {
        startled = true;
    }

    public boolean isStartled() {
        return startled;
    }

    @Override
    public void tick() {
        if (stoneling.getNavigation().isIdle() && super.canStart()) start();
    }

    @Override
    public boolean shouldContinue() {
        return canStart();
    }

    @Override
    public void stop() {
        stoneling.getNavigation().stop();
    }

    @Override
    public boolean canStart() {
        if (startled || stoneling.isPlayerMade())
            return false;


        List<PlayerEntity> playersAround = stoneling.world.getEntitiesByClass(PlayerEntity.class, stoneling.getBoundingBox().expand(range),
                (player) -> player != null && !player.abilities.creativeMode && player.squaredDistanceTo(stoneling) < range * range);

        if (playersAround.isEmpty())
            return false;

        for (PlayerEntity player : playersAround) {
            if (player.isSneaky()) {
                if (scaredBySuddenMovement.getAsBoolean()) {
                    MutableVec3d lastSpeed = lastSpeeds.computeIfAbsent(player, p -> new MutableVec3d(Vec3d.ZERO));
                    MutableVec3d lastPos = lastPositions.computeIfAbsent(player, ActWaryGoal::initPos);
                    Vec3d pos = player.getPos();

                    double dX = pos.x - lastPos.x;
                    double dY = pos.y - lastPos.y;
                    double dZ = pos.z - lastPos.z;

                    double xDisplacement = dX - lastSpeed.x;
                    double yDisplacement = dY - lastSpeed.y;
                    double zDisplacement = dZ - lastSpeed.z;

                    updateMotion(lastSpeed, dX, dY, dZ);
                    updatePos(lastPos, player);

                    double displacementSq = xDisplacement * xDisplacement +
                            yDisplacement * yDisplacement +
                            zDisplacement * zDisplacement;

                    if (displacementSq < 0.01) return true;

                    startled = true;
                    return false;
                }
            } else {
                startled = true;
                return false;
            }
        }

        return true;
    }
}
