package net.eps.lonsbattletowers.entity.custom.golem;

import net.eps.lonsbattletowers.entity.math.GolemMath;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

import java.util.EnumSet;

public class GolemLookAroundGoal extends Goal {
    private final TowerGolemEntity mob;
    private final Random random;
    private final GoalSelector goalSelector;
    private double deltaX;
    private double deltaZ;
    private float newDegree;
    private boolean counterClockwise;
    private boolean shouldLookOutOfBounds;
    private boolean transferableCounterClockwise;
    private int lookTime;

    public GolemLookAroundGoal(TowerGolemEntity mob, GoalSelector goalSelector) {
        this.mob = mob;
        this.random = mob.getRandom();
        this.goalSelector = goalSelector;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    public boolean canStart() {
        if (mob.getIsTurning()) {
            return false;
        } else if (this.goalSelector.getGoals().stream().anyMatch(goal -> goal.getGoal() instanceof GolemTurnAroundGoal)) {
            System.out.println("Found running goal of GolemTurnAround, process is not started");
            return false;
        }
        return this.mob.getRandom().nextFloat() < 0.02F;
    }

    public boolean shouldContinue() {
        return this.lookTime >= 0;
    }

    public void start() {
        this.shouldLookOutOfBounds = this.random.nextFloat() <= 0.7;
        this.transferableCounterClockwise = this.random.nextBoolean();
        this.counterClockwise = this.random.nextBoolean();

        float frontArmDegree;
        float degreeDiff;
        float armDegree;
        if (this.counterClockwise) {
            if (this.shouldLookOutOfBounds) {
                armDegree = mob.getArmByDegree(TowerGolemEntity.BACK_RIGHT_ARM_DEGREE).getPrevDegree();
                if (armDegree < 0) { armDegree += 360; }
                float degree = mob.getArmByDegree(TowerGolemEntity.FRONT_RIGHT_ARM_DEGREE).getPrevDegree();
                if (degree < 0) { degree += 360; }
                if (armDegree < degree) { degree -= 360; }

                this.newDegree = MathHelper.nextBetween(this.random, degree, armDegree - 20);

                this.transferableCounterClockwise = this.counterClockwise;
                frontArmDegree = degree;

            } else {
                armDegree = mob.getArmByDegree(TowerGolemEntity.FRONT_RIGHT_ARM_DEGREE).getPrevDegree();
                if (armDegree < 0) { armDegree += 360; }
                float degree = GolemMath.subtractDegree(armDegree, TowerGolemEntity.FRONT_RIGHT_ARM_DEGREE);
                if (degree < 0) { degree += 360; }
                if (armDegree < degree) { degree -= 360; }

                this.newDegree = MathHelper.nextBetween(this.random, degree + 5, armDegree - 20);

                frontArmDegree = this.transferableCounterClockwise ? mob.getArmByDegree(TowerGolemEntity.FRONT_RIGHT_ARM_DEGREE).getPrevDegree() : mob.getArmByDegree(TowerGolemEntity.FRONT_LEFT_ARM_DEGREE).getPrevDegree();

            }
            //if (frontArmDegree < 0) { frontArmDegree += 360; }
            degreeDiff = frontArmDegree - this.newDegree;

        } else {
            if (this.shouldLookOutOfBounds) {
                armDegree = mob.getArmByDegree(TowerGolemEntity.BACK_LEFT_ARM_DEGREE).getPrevDegree();
                if (armDegree < 0) { armDegree += 360; }
                float degree = mob.getArmByDegree(TowerGolemEntity.FRONT_LEFT_ARM_DEGREE).getPrevDegree();
                if (degree < 0) { degree += 360; }
                if (armDegree > degree) { armDegree -= 360; }

                this.newDegree = MathHelper.nextBetween(this.random, degree, armDegree + 20);

                this.transferableCounterClockwise = this.counterClockwise;
                frontArmDegree = degree;

            } else {
                armDegree = mob.getArmByDegree(TowerGolemEntity.FRONT_LEFT_ARM_DEGREE).getPrevDegree();
                if (armDegree < 0) { armDegree += 360; }
                float degree = armDegree + mob.getArmByDegree(TowerGolemEntity.FRONT_LEFT_ARM_DEGREE).armSpawnedDegree.get(true);
                if (degree < 0) { degree += 360; }
                if (armDegree > degree) { armDegree -= 360; }

                this.newDegree = MathHelper.nextBetween(this.random, degree - 5, armDegree + 20);

                frontArmDegree = this.transferableCounterClockwise ? mob.getArmByDegree(TowerGolemEntity.FRONT_RIGHT_ARM_DEGREE).getPrevDegree() : mob.getArmByDegree(TowerGolemEntity.FRONT_LEFT_ARM_DEGREE).getPrevDegree();

            }
            //if (frontArmDegree < 0) { frontArmDegree += 360; }
            degreeDiff = this.newDegree - frontArmDegree;


        }
        float roundedDegree = this.newDegree % 360;
        if (roundedDegree < 0) { roundedDegree += 360; }

        double d = Math.PI / 2 + Math.toRadians(roundedDegree);
        this.deltaX = Math.cos(d);
        this.deltaZ = Math.sin(d);
        this.lookTime = (int) (10 + Math.min(Math.pow(Math.abs(degreeDiff) / 20, 2), 1) * (40 + this.random.nextInt(50)));
        System.out.println("0| degreeDiff is " + degreeDiff);
        System.out.println("0| formula is " + Math.min(Math.pow(Math.abs(degreeDiff) / 20, 2), 1));
    }

    public boolean shouldRunEveryTick() {
        return true;
    }

    public void tick() {
        --this.lookTime;
        this.mob.getLookControl().lookAt(this.mob.getX() + this.deltaX, this.mob.getEyeY(), this.mob.getZ() + this.deltaZ);
    }

    @Override
    public void stop() {
        this.goalSelector.add(6, new GolemTurnAroundGoal(this.mob, this.transferableCounterClockwise, this.shouldLookOutOfBounds, this.newDegree));
        super.stop();
    }
}
