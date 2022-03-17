package work.lclpnet.mmoquark.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import work.lclpnet.mmoquark.entity.StonelingEntity;
import work.lclpnet.mmoquark.sound.MMOSounds;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class RunAndPoofGoal<T extends Entity> extends Goal {

    private final Predicate<Entity> canBeSeenSelector;
    protected StonelingEntity entity;
    private final double farSpeed;
    private final double nearSpeed;
    protected T closestLivingEntity;
    private final float avoidDistance;
    private Path path;
    private final EntityNavigation navigation;
    private final Class<T> classToAvoid;
    private final Predicate<T> avoidTargetSelector;

    public RunAndPoofGoal(StonelingEntity entity, Class<T> classToAvoid, float avoidDistance, double farSpeed, double nearSpeed) {
        this(entity, classToAvoid, t -> true, avoidDistance, farSpeed, nearSpeed);
    }

    public RunAndPoofGoal(StonelingEntity entity, Class<T> classToAvoid, Predicate<T> avoidTargetSelector, float avoidDistance, double farSpeed, double nearSpeed) {
        this.canBeSeenSelector = target -> target != null && target.isAlive() && entity.getVisibilityCache().canSee(target) && !entity.isTeammate(target);
        this.entity = entity;
        this.classToAvoid = classToAvoid;
        this.avoidTargetSelector = avoidTargetSelector;
        this.avoidDistance = avoidDistance;
        this.farSpeed = farSpeed;
        this.nearSpeed = nearSpeed;
        this.navigation = entity.getNavigation();
        setControls(EnumSet.of(Control.MOVE, Control.JUMP));
    }

    @Override
    public boolean canStart() {
        if (entity.isPlayerMade() || !entity.isStartled()) return false;

        List<T> entities = this.entity.world.getEntitiesByClass(this.classToAvoid, this.entity.getBoundingBox().expand(this.avoidDistance, 3.0D, this.avoidDistance),
                entity -> EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(entity) && this.canBeSeenSelector.test(entity) && this.avoidTargetSelector.test(entity));

        if (entities.isEmpty()) return false;
        else {
            this.closestLivingEntity = entities.get(0);
            Vec3d target = NoPenaltyTargeting.findFrom(this.entity, 16, 7, this.closestLivingEntity.getPos());

            if (target != null && this.closestLivingEntity.squaredDistanceTo(target.x, target.y, target.z) < this.closestLivingEntity.squaredDistanceTo(this.entity)) {
                return false;
            } else {
                if (target != null) this.path = this.navigation.findPathTo(target.x, target.y, target.z, 0);
                return target == null || this.path != null;
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        if (this.path == null || this.navigation.isIdle()) {
            return false;
        }

        BlockPos.Mutable pos = new BlockPos.Mutable();
        Vec3d entityPos = entity.getPos();

        for (int i = 0; i < 8; ++i) {
            int j = MathHelper.floor(entityPos.x + (i % 2 - 0.5F) * 0.1F + entity.getStandingEyeHeight());
            int k = MathHelper.floor(entityPos.y + ((i >> 1) % 2 - 0.5F) * entity.getWidth() * 0.8F);
            int l = MathHelper.floor(entityPos.z + ((i >> 2) % 2 - 0.5F) * entity.getWidth() * 0.8F);

            if (pos.getX() != k || pos.getY() != j || pos.getZ() != l) {
                pos.set(k, j, l);

                if (entity.world.getBlockState(pos).getMaterial().blocksMovement()) return false;
            }
        }

        return true;
    }

    @Override
    public void start() {
        Vec3d epos = entity.getPos();

        if (this.path != null) this.navigation.startMovingAlong(this.path, this.farSpeed);
        entity.world.playSound(null, epos.x, epos.y, epos.z, MMOSounds.ENTITY_STONELING_MEEP, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }

    @Override
    public void stop() {
        this.closestLivingEntity = null;

        World world = entity.world;

        if (world instanceof ServerWorld ws) {
            Vec3d epos = entity.getPos();

            ws.spawnParticles(ParticleTypes.CLOUD, epos.x, epos.y, epos.z, 40, 0.5, 0.5, 0.5, 0.1);
            ws.spawnParticles(ParticleTypes.EXPLOSION, epos.x, epos.y, epos.z, 20, 0.5, 0.5, 0.5, 0);
        }
        for (Entity passenger : entity.getPassengersDeep())
            if (!(passenger instanceof PlayerEntity))
                passenger.discard();

        entity.discard();
    }

    @Override
    public void tick() {
        if (this.entity.squaredDistanceTo(this.closestLivingEntity) < 49.0D) this.entity.getNavigation().setSpeed(this.nearSpeed);
        else this.entity.getNavigation().setSpeed(this.farSpeed);
    }
}
