package net.eps.lonsbattletowers.entity.custom.golem;

import net.eps.lonsbattletowers.entity.custom.golem.goals.TowerGolemStepGoal;
import net.eps.lonsbattletowers.entity.math.GolemMath;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.EnumSet;
import java.util.Optional;

public class GolemTurnAroundGoal extends Goal {
    private final TowerGolemEntity mob;
    private final Random random;
    private float newDegree;
    private float nextDegree;
    private float viewDegree;
    private double deltaX;
    private double deltaZ;
    private boolean shouldArmTurn;
    private final boolean outOfBounds;
    private final boolean clockwise;
    private int lookTime;

    @Deprecated
    public GolemTurnAroundGoal(TowerGolemEntity mob) {
        this.mob = mob;
        this.random = mob.getRandom();
        this.viewDegree = mob.headYaw < 0 ? mob.headYaw + 360 : mob.headYaw;
        this.clockwise = this.random.nextBoolean();
        this.outOfBounds = this.random.nextBoolean();
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    public GolemTurnAroundGoal(TowerGolemEntity mob, boolean clockwise, boolean outOfBounds, float viewDegree) {
        this.mob = mob;
        this.random = mob.getRandom();
        this.clockwise = clockwise;
        this.outOfBounds = outOfBounds;
        this.viewDegree = viewDegree;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    public boolean canStart() {
        if (mob.getIsTurning()) {
            return false;
        }
        //return this.mob.getRandom().nextFloat() < 0.02F;
        return true;
    }

    public boolean shouldContinue() {
        return this.lookTime >= 0;
    }

    public void start() {
        this.shouldArmTurn = false;

        if (this.clockwise) {
            if (this.outOfBounds) {
                float armDegree = mob.getArmByDegree(TowerGolemEntity.FRONT_RIGHT_ARM_DEGREE).getPrevDegree();
                //float degree = GolemMath.subtractDegree(armDegree, TowerGolemEntity.FRONT_RIGHT_ARM_DEGREE);
                System.out.println("1| cClockwise is " + clockwise + ", and shouldLookOutOfBounds is " + outOfBounds);
                System.out.println("1| armDegree - " + armDegree + ", viewDegree is " + this.viewDegree);
                if (viewDegree < armDegree) { this.viewDegree += 360;
                System.out.println("1| viewDegree changed - " + this.viewDegree);}
                System.out.println("1| ");
                //this.newDegree = MathHelper.nextBetween(this.random, GolemMath.addDegree(degree, 5), armDegree);
                this.newDegree = MathHelper.nextBetween(this.random, this.viewDegree - 5, armDegree - 15);
                System.out.println("1| newDegree is " + newDegree);
                System.out.println("1| ");
                System.out.println("1| FloorMod of newDegree " + Math.floorMod((int) newDegree, 360));
                this.shouldArmTurn = true;
                this.nextDegree = TowerGolemEntity.FRONT_RIGHT_ARM_DEGREE;
            } else {
                float armDegree = mob.getArmByDegree(TowerGolemEntity.FRONT_RIGHT_ARM_DEGREE).getPrevDegree();
                System.out.println("1| cClockwise is " + clockwise + ", and shouldLookOutOfBounds is " + outOfBounds);
                System.out.println("1| armDegree - " + armDegree);
                System.out.println("1| ");
                this.newDegree = MathHelper.nextBetween(this.random, armDegree - 15, armDegree);
                System.out.println("1| newDegree is " + newDegree);
                System.out.println("1| ");
                System.out.println("1| FloorMod of newDegree " + Math.floorMod((int) newDegree, 360));
                this.shouldArmTurn = true;
                this.nextDegree = TowerGolemEntity.FRONT_RIGHT_ARM_DEGREE;
            }

        } else {
            if (this.outOfBounds) {
                float armDegree = mob.getArmByDegree(TowerGolemEntity.FRONT_LEFT_ARM_DEGREE).getPrevDegree();
                //float degree = armDegree + mob.armPartMap.get(TowerGolemEntity.FRONT_LEFT_ARM_DEGREE).armSpawnedDegree.get(true);
                System.out.println("1| cClockwise is " + clockwise + ", and shouldLookOutOfBounds is " + outOfBounds);
                System.out.println("1| armDegree - " + armDegree + ", viewDegree is " + this.viewDegree);
                if (viewDegree > armDegree) { armDegree += 360;
                    System.out.println("1| armDegree changed - " + armDegree);}
                System.out.println("1| ");
                //this.newDegree = MathHelper.nextBetween(this.random, degree - 5, armDegree);
                this.newDegree = MathHelper.nextBetween(this.random, this.viewDegree + 5, armDegree + 15);
                System.out.println("1| newDegree is " + newDegree);
                System.out.println("1| ");
                System.out.println("1| FloorMod of newDegree " + Math.floorMod((int) newDegree, 360));
                this.shouldArmTurn = true;
                this.nextDegree = TowerGolemEntity.FRONT_LEFT_ARM_DEGREE;

                //this.newDegree %= 360;
            } else {
                float armDegree = mob.getArmByDegree(TowerGolemEntity.FRONT_LEFT_ARM_DEGREE).getPrevDegree();
                System.out.println("1| cClockwise is " + clockwise + ", and shouldLookOutOfBounds is " + outOfBounds);
                System.out.println("1| armDegree - " + armDegree);
                System.out.println("1| ");
                this.newDegree = MathHelper.nextBetween(this.random, armDegree + 15, armDegree);
                System.out.println("1| newDegree is " + newDegree);
                System.out.println("1| ");
                System.out.println("1| FloorMod of newDegree " + Math.floorMod((int) newDegree, 360));
                this.shouldArmTurn = true;
                this.nextDegree = TowerGolemEntity.FRONT_LEFT_ARM_DEGREE;

                //this.newDegree %= 360;
            }

        }
        System.out.println(" ");

        double d = Math.PI / 2 + Math.toRadians(this.newDegree);
        this.deltaX = Math.cos(d);
        this.deltaZ = Math.sin(d);
        this.lookTime = 20 + this.mob.getRandom().nextInt(20);
    }

    public boolean shouldRunEveryTick() {
        return true;
    }

    public void tick() {
        --this.lookTime;
        this.mob.getLookControl().lookAt(this.mob.getX() + this.deltaX, this.mob.getEyeY(), this.mob.getZ() + this.deltaZ);

        if (this.shouldArmTurn) {
            TowerGolemArmPart armPart = mob.getArmByDegree(this.nextDegree);

            /*
            if (armPart.getTurnStop() && !mob.armTurnContinues && !mob.isArmNowTurning) {
                TowerGolemEntity.armStepStop(armPart, mob);
            }

             */

            boolean bl = /* ВРЕМЕННО this.stepCooldown == 0 && */ !mob.getIsTurning();
            //boolean bl2 = mob.getIsTurning() && mob.armTurnContinues && mob.armDegreeNext == armPart.armSpawnedDegree.get(false) && !mob.isArmNowTurning;

            if (bl) {
                this.moveArm(armPart);

                this.shouldArmTurn = false;
            }
        }
    }

    @Override
    public void stop() {
        this.mob.clearGoals(goal -> goal instanceof GolemTurnAroundGoal);
        super.stop();
    }

    protected void moveArm(TowerGolemArmPart armPart) {
        mob.setIsTurning(true);

        TowerGolemArmPart nextPart;
        float armDegree;
        int t = 0;
        int n = 0;
        if (this.clockwise) {
            nextPart = mob.frontArmParts.get(mob.frontArmParts.indexOf(armPart) + 1 > mob.frontArmParts.size() - 1 ? 0 : mob.frontArmParts.indexOf(armPart) + 1);
            if (nextPart.getIsTurned()) {
                throw new RuntimeException("Next golem arm in frontArmParts is turned, clockwise is " + this.clockwise + ", but is called!");
            }
            armDegree = GolemMath.addDegree(this.newDegree, armPart.armSpawnedDegree.get(false));
            if (armDegree < armPart.getPrevDegree()) {
                n = 360;
            }
        } else {
            nextPart = mob.frontArmParts.get(mob.frontArmParts.indexOf(armPart) - 1 < 0 ? mob.frontArmParts.size() - 1 : mob.frontArmParts.indexOf(armPart) - 1);
            if (nextPart.getIsTurned()) {
                throw new RuntimeException("Next golem arm in frontArmParts is turned, clockwise is " + this.clockwise + ", but is called!");
            }
            armDegree = GolemMath.subtractDegree(this.newDegree, armPart.armSpawnedDegree.get(true));
            if (armDegree > armPart.getPrevDegree()) {
                t = 360;
            }
        }
        //armPart.armFullDegree += (armDegree + n) - (armPart.getPrevDegree() + t);

        BlockPos newPos = TowerGolemEntity.raycastPos(mob, GolemMath.posFromAngle(mob.getPos(), GolemMath.addDegree(armDegree, 90), false).add(0, mob.getY(), 0));


        armPart.armGoalSelector.add(1, new TowerGolemStepGoal(
                mob,
                armPart,
                nextPart,
                newPos.toCenterPos(),
                armPart.getBlockPos(),
                n,
                t,
                armDegree,
                //this.newDegree,
                this.clockwise));


        mob.armsNowMoving.set(1, Optional.of(armPart));
        //mob.armNowTurning = armPart;
        //armPart.setPrevDegree(armDegree);

        Vec3d posOffset = new Vec3d(newPos.getX() - mob.getPos().x, newPos.getY() - mob.getPos().y, newPos.getZ() - mob.getPos().z);
        armPart.armPosOffset = Optional.of(BlockPos.ofFloored(posOffset));
        System.out.println("armPosOffset у руки " + armPart.armSpawnedDegree.get(false) + " записана : " + armPart.armPosOffset);
    }
}
