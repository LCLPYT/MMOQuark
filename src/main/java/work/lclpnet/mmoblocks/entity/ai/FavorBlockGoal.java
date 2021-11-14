package work.lclpnet.mmoblocks.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.Objects;
import java.util.function.Predicate;

public class FavorBlockGoal extends Goal {

    private final PathAwareEntity creature;
    private final double movementSpeed;
    private final Predicate<BlockState> targetBlock;

    protected int runDelay;
    private int timeoutCounter;
    private int maxStayTicks;

    protected BlockPos destinationBlock = BlockPos.ORIGIN;

    public FavorBlockGoal(PathAwareEntity creature, double speed, Predicate<BlockState> predicate) {
        this.creature = creature;
        this.movementSpeed = speed;
        this.targetBlock = predicate;
        setControls(EnumSet.of(Control.MOVE, Control.JUMP));
    }

    public FavorBlockGoal(PathAwareEntity creature, double speed, Tag<Block> tag) {
        this(creature, speed, (state) -> tag.contains(state.getBlock()));
    }

    public FavorBlockGoal(PathAwareEntity creature, double speed, Block block) {
        this(creature, speed, (state) -> state.getBlock() == block);
    }

    @Override
    public boolean canStart() {
        if (runDelay > 0) {
            --runDelay;
            return false;
        } else {
            runDelay = 200 + creature.getRandom().nextInt(200);
            return searchForDestination();
        }
    }

    @Override
    public boolean shouldContinue() {
        return timeoutCounter >= -maxStayTicks && timeoutCounter <= 1200 && targetBlock.test(creature.world.getBlockState(destinationBlock));
    }

    @Override
    public void start() {
        creature.getNavigation().startMovingTo(destinationBlock.getX() + 0.5, destinationBlock.getY() + 1, destinationBlock.getZ() + 0.5, movementSpeed);
        timeoutCounter = 0;
        maxStayTicks = creature.getRandom().nextInt(creature.getRandom().nextInt(1200) + 1200) + 1200;
    }

    @Override
    public void tick() {
        if (creature.squaredDistanceTo(new Vec3d(destinationBlock.getX(), destinationBlock.getY(), destinationBlock.getZ()).add(0.5, 1.5, 0.5)) > 1.0D) {
            ++timeoutCounter;

            if (timeoutCounter % 40 == 0)
                creature.getNavigation().startMovingTo(destinationBlock.getX() + 0.5D, destinationBlock.getY() + 1, destinationBlock.getZ() + 0.5D, movementSpeed);
        } else {
            --timeoutCounter;
        }
    }

    private boolean searchForDestination() {
        double followRange = Objects.requireNonNull(creature.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE)).getValue();
        Vec3d cpos = creature.getPos();
        double xBase = cpos.x;
        double yBase = cpos.y;
        double zBase = cpos.z;

        BlockPos.Mutable pos = new BlockPos.Mutable();

        for (int yShift = 0;
             yShift <= 1;
             yShift = yShift > 0 ? -yShift : 1 - yShift) {

            for (int seekDist = 0; seekDist < followRange; ++seekDist) {
                for (int xShift = 0;
                     xShift <= seekDist;
                     xShift = xShift > 0 ? -xShift : 1 - xShift) {

                    for (int zShift = xShift < seekDist && xShift > -seekDist ? seekDist : 0;
                         zShift <= seekDist;
                         zShift = zShift > 0 ? -zShift : 1 - zShift) {

                        pos.set(xBase + xShift, yBase + yShift - 1, zBase + zShift);

                        if (creature.isInWalkTargetRange(pos) && targetBlock.test(creature.world.getBlockState(pos))) {
                            destinationBlock = pos;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
