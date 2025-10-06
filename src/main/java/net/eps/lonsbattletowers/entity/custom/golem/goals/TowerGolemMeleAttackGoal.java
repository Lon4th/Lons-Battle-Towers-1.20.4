package net.eps.lonsbattletowers.entity.custom.golem.goals;

import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemArmPart;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemEntity;
import net.eps.lonsbattletowers.entity.math.GolemMath;
import net.eps.lonsbattletowers.sounds.ModSounds;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class TowerGolemMeleAttackGoal extends MeleeAttackGoal {
    private final TowerGolemEntity entity;
    private int ticksUntilNextAttack = 10;
    private int idleTime = 0;
    private boolean shouldCountTillNextAttack = false;

    public TowerGolemMeleAttackGoal(TowerGolemEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        entity = mob;
    }

    @Override
    public void start() {
        super.start();
        shouldCountTillNextAttack = false;
    }


    @Override
    protected void attack(LivingEntity target, double squaredDistance) {
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = 10;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.mob.getNavigation().isIdle()) {
            this.idleTime++;
            if (this.idleTime >= 10 && ((TowerGolemEntity)this.mob).armsNowMoving.get(1).isEmpty()) {
                TowerGolemEntity owner = (TowerGolemEntity) this.mob;
                float yawDegree = owner.getHeadYaw() < 0 ? owner.getHeadYaw() + 360 : owner.getHeadYaw();
                boolean clockwise = GolemMath.getOptimalStartDirection(yawDegree, owner.getArmByDegree(TowerGolemEntity.FRONT_RIGHT_ARM_DEGREE).getPrevDegree(), owner.getArmByDegree(TowerGolemEntity.FRONT_LEFT_ARM_DEGREE).getPrevDegree());

                owner.setIsTurning(true);

                TowerGolemArmPart armPart;
                float armDegree;
                int t = 0;
                int n = 0;
                if (clockwise) {
                    //System.out.println("это между 0 и 45");
                    armPart = owner.getArmByDegree(TowerGolemEntity.FRONT_RIGHT_ARM_DEGREE);
                    armDegree = GolemMath.addDegree(yawDegree, armPart.armSpawnedDegree.get(false));
                    if (armDegree < armPart.getPrevDegree()) {
                        n = 360;
                    }
                } else {
                    //System.out.println("это между 315 и 360");
                    armPart = owner.getArmByDegree(TowerGolemEntity.FRONT_LEFT_ARM_DEGREE);
                    armDegree = GolemMath.subtractDegree(yawDegree, armPart.armSpawnedDegree.get(true));
                    if (armDegree > armPart.getPrevDegree()) {
                        t = 360;
                    }
                }

                clockwise = !GolemMath.getClockwise(armPart.getPrevDegree(), armDegree);

                Vec3d newPos = GolemMath.posFromAngle(owner.getPos(), GolemMath.addDegree(armDegree, 90), false);
                newPos.add(0, TowerGolemEntity.raycastPos(owner, newPos.add(0, owner.getY(), 0)).getY(), 0);

                owner.createPriorityList(armPart, false);
                armPart.armGoalSelector.add(1, new TowerGolemStepGoal(
                        owner,
                        armPart,
                        owner.getNextPriorityArm(armPart, false),
                        newPos,
                        armPart.getBlockPos(),
                        n,
                        t,
                        armDegree,
                        clockwise));

                owner.armsNowMoving.set(1, Optional.of(armPart));

                Vec3d posOffset = new Vec3d(newPos.getX() - owner.getPos().x, newPos.getY() - owner.getPos().y, newPos.getZ() - owner.getPos().z);
                armPart.armPosOffset = Optional.of(BlockPos.ofFloored(posOffset));
            }
        } else if (this.idleTime > 0) {
            this.idleTime = 0;
        }

        if(shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);

        } else if (ticksUntilNextAttack <= 0) {
            resetAttackCooldown();
        }
    }

    @Override
    public void stop() {
        this.ticksUntilNextAttack = 10;
        shouldCountTillNextAttack = false;
        super.stop();
    }
}
