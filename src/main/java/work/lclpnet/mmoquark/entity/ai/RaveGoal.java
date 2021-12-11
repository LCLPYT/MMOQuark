package work.lclpnet.mmoquark.entity.ai;

import net.minecraft.entity.ai.goal.Goal;
import work.lclpnet.mmoquark.entity.CrabEntity;

import java.util.EnumSet;

public class RaveGoal extends Goal {
    private final CrabEntity crab;

    public RaveGoal(CrabEntity crab) {
        this.crab = crab;
        this.setControls(EnumSet.of(Control.MOVE, Control.JUMP));
    }

    @Override
    public boolean canStart() {
        return this.crab.isRaving();
    }

    @Override
    public void start() {
        this.crab.getNavigation().stop();
    }
}
