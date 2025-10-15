package net.eps.lonsbattletowers.mixins;

import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemArmPart;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemEntity;
import net.eps.lonsbattletowers.entity.custom.golem.goals.TowerGolemStepGoal;
import net.eps.lonsbattletowers.entity.math.GolemMath;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import java.lang.Math;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(MoveControl.class)
public class EntityNavigationMixin {

    @Shadow @Final protected MobEntity entity;

    @Shadow protected double targetX;

    @Shadow protected double targetY;

    /*@ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;setYaw(F)V"))
    private float changeArg(float par1) {
        return this.entity.getYaw();
    }
     */

    @Shadow protected double targetZ;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;setMovementSpeed(F)V", ordinal = 1))
    private void tickMoveMixin(CallbackInfo ci) {
        if (this.entity instanceof TowerGolemEntity owner) {
            float newDegree = this.entity.getHeadYaw(); // < 0 ? this.entity.getHeadYaw() + 360 : this.entity.getHeadYaw();

            if (owner.armsNowMoving.get(1).isEmpty() && (owner.armsNowMoving.get(2).isEmpty() || owner.armsNowMoving.get(3).isEmpty()) && owner.armsNowMoving.get(4).isEmpty()) {
                boolean clockwise = GolemMath.getOptimalStartDirection(newDegree, owner.getArmByDegree(TowerGolemEntity.FRONT_RIGHT_ARM_DEGREE).getPrevDegree(), owner.getArmByDegree(TowerGolemEntity.FRONT_LEFT_ARM_DEGREE).getPrevDegree());

                owner.setIsTurning(true);

                TowerGolemArmPart armPart;
                TowerGolemArmPart backArmPart;
                if (clockwise) {
                    armPart = owner.getArmByDegree(TowerGolemEntity.FRONT_RIGHT_ARM_DEGREE);
                    backArmPart = owner.getArmByDegree(TowerGolemEntity.BACK_LEFT_ARM_DEGREE);
                } else {
                    armPart = owner.getArmByDegree(TowerGolemEntity.FRONT_LEFT_ARM_DEGREE);
                    backArmPart = owner.getArmByDegree(TowerGolemEntity.BACK_RIGHT_ARM_DEGREE);
                }

                owner.createPriorityList(armPart, true);
                if (owner.armsNowMoving.get(2).isEmpty()) {
                    this.runStepGoalForArm(armPart, owner, clockwise, newDegree, 2);
                    owner.setBackArmsCooldownTime(10);
                }
                if (owner.armsNowMoving.get(3).isEmpty()) {
                    this.runStepGoalForArm(backArmPart, owner, clockwise, newDegree, 3);
                }

            } else {
                Optional<TowerGolemArmPart> armPart = owner.armsNowMoving.get(2);
                Optional<TowerGolemArmPart> backArmPart = owner.armsNowMoving.get(3);

                if (armPart.isPresent()) {
                    this.changeStepFinishPos(armPart.get(), owner, newDegree);
                }
                if (backArmPart.isPresent()) {
                    this.changeStepFinishPos(backArmPart.get(), owner, newDegree);
                }
            }
        }
    }

    @Unique
    private void runStepGoalForArm(TowerGolemArmPart armPart, TowerGolemEntity owner, boolean clockwise, float yawDegree, int index) {
        Vec3d centerPos;
        if (owner.getTarget() != null) {
            centerPos = GolemMath.getPosInFond(owner, owner.getTarget().getPos());
        } else {
            centerPos = GolemMath.getPosInFond(owner, yawDegree);
        }

        float armDegree;
        int t = 0;
        int n = 0;
        if (clockwise) {
            armDegree = GolemMath.addDegree(yawDegree, armPart.armSpawnedDegree.get(false));
            if (armDegree < armPart.getPrevDegree()) {
                n = 360;
            }
        } else {
            armDegree = GolemMath.subtractDegree(yawDegree, armPart.armSpawnedDegree.get(true));
            if (armDegree > armPart.getPrevDegree()) {
                t = 360;
            }
        }

        clockwise = !GolemMath.getClockwise(armPart.getPrevDegree(), armDegree);
        System.out.println("(ArmPart " + armPart.armSpawnedDegree.get(false) + " is setting up) clockwise is " + clockwise);

        Vec3d newPos = GolemMath.posFromAngle(centerPos, GolemMath.addDegree(armDegree, 90), false);
        newPos.add(0, TowerGolemEntity.raycastPos(owner, newPos.add(0, owner.getY(), 0)).getY(), 0);

        armPart.armGoalSelector.add(1, new TowerGolemStepGoal(
                owner,
                armPart,
                owner.getNextPriorityArm(armPart, true),
                newPos,
                armPart.getBlockPos(),
                centerPos,
                n,
                t,
                armDegree,
                clockwise,
                clockwise,
                index,
                2));

        owner.armsNowMoving.set(index, Optional.of(armPart));

        Vec3d posOffset = new Vec3d(newPos.getX() - owner.getPos().x, newPos.getY() - owner.getPos().y, newPos.getZ() - owner.getPos().z);
        armPart.armPosOffset = Optional.of(BlockPos.ofFloored(posOffset));
    }

    @Unique
    private void changeStepFinishPos(TowerGolemArmPart armPart, TowerGolemEntity owner, float yawDegree) {
        Vec3d centerPos;
        if (owner.getTarget() != null) {
            centerPos = GolemMath.getPosInFond(owner, owner.getTarget().getPos());
            yawDegree = (float) ((Math.toDegrees(Math.atan2(centerPos.x - owner.getPos().x, centerPos.z - owner.getPos().z)) % 360) * -1);
        } else {
            centerPos = GolemMath.getPosInFond(owner, yawDegree);
        }
        ((ServerWorld) owner.getWorld()).spawnParticles(ParticleTypes.ANGRY_VILLAGER, centerPos.getX(), centerPos.getY(), centerPos.getZ(), 3, 0, 0, 0, 0);

        boolean clockwise = armPart.getClockwise();
        float armDegree;
        if (clockwise) {
            armDegree = GolemMath.addDegree(yawDegree, armPart.armSpawnedDegree.get(false));
        } else {
            armDegree = GolemMath.subtractDegree(yawDegree, armPart.armSpawnedDegree.get(true));
        }

        Vec3d newPos = GolemMath.posFromAngle(centerPos, GolemMath.addDegree(armDegree, 90), false);

        float currentArmDegree = (float) ((Math.toDegrees(Math.atan2(armPart.getPos().x - centerPos.x, armPart.getPos().z - centerPos.z)) % 360) * -1);
        float newArmDegree = (float) ((Math.toDegrees(Math.atan2(newPos.x - centerPos.x, newPos.z - centerPos.z)) % 360) * -1);
        boolean newClockwise = !GolemMath.getClockwise(currentArmDegree, newArmDegree);
        armPart.setClockwise(newClockwise);

        newPos = newPos.add(0, TowerGolemEntity.raycastPos(owner, newPos.add(0, owner.getY(), 0)).getY(), 0);

        armPart.setStepFinishPos(newPos);
        armPart.setPartsCenterPos(centerPos);
    }
}
