package net.eps.lonsbattletowers.entity.custom.golem.goals;

import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemArmPart;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemEntity;
import net.eps.lonsbattletowers.entity.math.GolemMath;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
//import org.joml.Math;
import java.lang.Math;

import java.util.Optional;

public class TowerGolemStepGoal extends Goal {
    private final TowerGolemEntity owner;
    private final TowerGolemArmPart part;
    private final TowerGolemArmPart nextPart;
    private final BlockPos prevPos;

    public double armsStepTime;
    public float maxTime;
    public float timeAddend;
    public int nextChainsAmount;
    public double distance;
    public double backArmsCooldownTime;

    public float partDegree;
    public int delta1;
    public int delta2;

    public boolean clockwiseConstant;
    public int stepState;
    /**     1 - arm turning;
     *      2 - front arm moving;
     *      3 - back arm moving;
     *      4 - arm attacking;
     */
    public Optional<Vec3d> projection;
    public boolean foundObstacle;

    public TowerGolemStepGoal(TowerGolemEntity owner, TowerGolemArmPart part, TowerGolemArmPart nextPart, Vec3d pos, BlockPos prevPos, int delta1, int delta2, float partDegree, /*float mainDegree,*/ boolean clockwise) {
        this(owner, part, nextPart, pos, prevPos, owner.getPos(), delta1, delta2, partDegree, /*mainDegree,*/ clockwise, clockwise, 1, 1, 0.14f, 4);
    }

    public TowerGolemStepGoal(TowerGolemEntity owner, TowerGolemArmPart part, TowerGolemArmPart nextPart, Vec3d pos, BlockPos prevPos, Vec3d centerPos, int delta1, int delta2, float partDegree, /*float mainDegree,*/ boolean clockwise, boolean clockwiseConstant, int stepState, int nextChainsAmount) {
        this(owner, part, nextPart, pos, prevPos, centerPos, delta1, delta2, partDegree, /*mainDegree,*/ clockwise, clockwiseConstant, stepState, 1, 0.14f, nextChainsAmount);
    }

    public TowerGolemStepGoal(TowerGolemEntity owner, TowerGolemArmPart part, TowerGolemArmPart nextPart,
                              Vec3d pos, BlockPos prevPos, Vec3d centerPos, int delta1, int delta2,
                              float partDegree, /*float mainDegree,*/
                              boolean clockwise, boolean clockwiseConstant, int stepState,
                              float maxTime, float timeAddend, int nextChainsAmount) {
        this.owner = owner;
        this.part = part;
        this.nextPart = nextPart;

        this.part.setStepFinishPos(pos);
        this.prevPos = prevPos;
        this.delta1 = delta1;
        this.delta2 = delta2;

        this.partDegree = partDegree;

        this.part.setClockwise(clockwise);
        this.clockwiseConstant = clockwiseConstant;

        this.stepState = stepState;

        this.backArmsCooldownTime = 0;
        this.armsStepTime = 0;
        this.maxTime = 1.1f;
        this.timeAddend = timeAddend;

        this.nextChainsAmount = nextChainsAmount;

        this.projection = Optional.empty();
        this.part.setPartsCenterPos(centerPos);
        this.foundObstacle = false;
    }

    @Override
    public boolean canStart() {
        if (this.owner == null) {
            return false;
        }
        if (this.part == null) {
            return false;
        }
        if (this.part.getIsTurned()) {
            return false;
        }
        if (this.part.getStepFinishPos() == null) {
            return false;
        }
        if (this.prevPos == null) {
            return false;
        }
        if (!this.owner.getIsTurning()) {
            return false;
        }
        if (this.nextChainsAmount < 1) {
            return false;
        }

        return true;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void start() {
        if (this.stepState == 3 && this.owner.getBackArmsCooldownTime() > 0) {
            this.backArmsCooldownTime = this.owner.getBackArmsCooldownTime();
        }
        this.part.setNoGravity(true);

        Vec3d startPos = Vec3d.ofBottomCenter(this.prevPos).add(-0.5, 0, -0.5);
        Vec3d endPos = this.part.getStepFinishPos();
        double distanceToCenter = Math.sqrt(Math.pow(startPos.x - this.part.getPartsCenterPos().x, 2) + Math.pow(startPos.z - this.part.getPartsCenterPos().z, 2));

        this.distance = GolemMath.getDistance(startPos, endPos, this.part.getPartsCenterPos(), this.partDegree, distanceToCenter);
        if (distanceToCenter >= 5/* && true*/) {
            this.projection = Optional.of(GolemMath.posFromAngle(this.part.getPartsCenterPos(), GolemMath.addDegree(this.partDegree, 90), false));
        }

        this.armsStepTime = 0;
    }

    @Override
    public boolean shouldContinue() {
        boolean bl = this.owner.armsNowMoving.get(this.stepState).isPresent() &&
                this.owner.getIsTurning() &&
                this.owner.isAlive() && this.part.isAlive();

        return bl && this.armsStepTime < 1;
    }

    @Override
    public void tick() {
        if (this.backArmsCooldownTime > 0) {
            System.out.println("(ArmPart " + this.part.armSpawnedDegree.get(false) + " is on tick moving) clockwise is " + this.part.getClockwise());
            this.backArmsCooldownTime = this.owner.getBackArmsCooldownTime();
            return;
        }

        if (this.armsStepTime < this.maxTime && this.owner.armsNowMoving.get(this.stepState).isPresent()) {
            Vec3d newPos = GolemMath.calculateArmPosition(
                    Vec3d.ofBottomCenter(this.prevPos).add(-0.5, 0, -0.5),
                    this.part.getStepFinishPos(),
                    this.part.getPartsCenterPos(),
                    Math.min(this.armsStepTime, 1.0f),
                    !this.part.getClockwise(),
                    this.distance,
                    this.projection,
                    this.owner.getWorld());


            this.part.setPosition(newPos);

            this.armsStepTime = Math.min(this.armsStepTime + this.timeAddend * this.owner.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED), this.maxTime);
        }
    }

    @Override
    public void stop() {
        this.part.setNoGravity(false);

        if (this.foundObstacle) {
            float newArmDegree = (float) ((Math.toDegrees(Math.atan2(this.part.getPos().x - this.owner.getPos().x, this.part.getPos().z - this.owner.getPos().z)) % 360) * -1);

            this.part.armFullDegree += (newArmDegree + delta1) - (this.part.getPrevDegree() + delta2);
            this.part.setPrevDegree(newArmDegree);
        } else {
            this.part.armFullDegree += (this.partDegree + delta1) - (this.part.getPrevDegree() + delta2);
            this.part.setPrevDegree(this.partDegree);
        }
        this.part.setIsTurned(true);



        if (TowerGolemEntity.shouldContinueMoving(this.owner, this.part, this.nextPart, !this.clockwiseConstant, this.nextChainsAmount)) {
            TowerGolemArmPart newNextPart = this.owner.getNextPriorityArm(this.nextPart, this.stepState == 2 || this.stepState == 3);
            float yawDegree = this.owner.getHeadYaw() < 0 ? this.owner.getHeadYaw() + 360 : this.owner.getHeadYaw();
            float armDegree;
            int t = 0;
            int n = 0;

            if (this.part.getClockwise()) {
                armDegree = GolemMath.subtractDegree(yawDegree, this.nextPart.armSpawnedDegree.get(true));
                if (armDegree < this.nextPart.getPrevDegree()) {
                    n = 360;
                }

            } else {
                armDegree = GolemMath.addDegree(yawDegree, this.nextPart.armSpawnedDegree.get(false));
                if (armDegree > this.nextPart.getPrevDegree()) {
                    t = 360;
                }
            }

            Vec3d newPos;
            if (this.stepState == 2 || this.stepState == 3) {
                Vec3d centerPos = GolemMath.getPosInFond(this.owner, yawDegree);
                this.part.setClockwise(!GolemMath.getClockwise(this.nextPart.getPrevDegree(), armDegree));

                newPos = GolemMath.posFromAngle(centerPos, GolemMath.addDegree(armDegree, 90), false);
                newPos = newPos.add(0, TowerGolemEntity.raycastPos(this.owner, newPos.add(0, this.owner.getY(), 0)).getY(), 0);

                this.nextPart.armGoalSelector.add(1, new TowerGolemStepGoal(
                        this.owner,
                        this.nextPart,
                        newNextPart,
                        newPos,
                        this.nextPart.getBlockPos(),
                        centerPos,
                        n,
                        t,
                        armDegree,
                        this.part.getClockwise(),
                        this.clockwiseConstant,
                        this.stepState,
                        this.nextChainsAmount - 1));
            } else {
                newPos = GolemMath.posFromAngle(this.owner.getPos(), GolemMath.addDegree(armDegree, 90), false);
                newPos.add(0, TowerGolemEntity.raycastPos(this.owner, newPos.add(0, this.owner.getY(), 0)).getY(), 0);

                this.nextPart.armGoalSelector.add(1, new TowerGolemStepGoal(
                        this.owner,
                        this.nextPart,
                        newNextPart,
                        newPos,
                        this.nextPart.getBlockPos(),
                        n,
                        t,
                        armDegree,
                        this.clockwiseConstant));
            }

            this.owner.armsNowMoving.set(this.stepState, Optional.of(this.nextPart));

            Vec3d posOffset = new Vec3d(newPos.getX() - this.owner.getPos().x, newPos.getY() - this.owner.getPos().y, newPos.getZ() - this.owner.getPos().z);
            this.nextPart.armPosOffset = Optional.of(BlockPos.ofFloored(posOffset));
        } else {
            TowerGolemEntity.armStepStop(this.owner, this.stepState);
        }
    }
}
