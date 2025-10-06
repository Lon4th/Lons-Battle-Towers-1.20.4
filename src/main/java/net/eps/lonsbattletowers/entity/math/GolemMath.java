package net.eps.lonsbattletowers.entity.math;

import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import java.lang.Math;

import net.minecraft.world.World;
import org.joml.Vector2d;

import java.util.Optional;

public class GolemMath {
    public GolemMath() {
    }

    public static Vec3d calculateArmPosition(Vec3d startPos, Vec3d endPos, Vec3d centerPos, double time, boolean counterClockwise, double distance, Optional<Vec3d> projectionOptional, World world) {
        if (projectionOptional.isPresent()) {
            Vec3d projection = projectionOptional.get();

            /* Move of projected pos */
            double startAngle = Math.atan2(projection.z - centerPos.z, projection.x - centerPos.x);
            double endAngle = Math.atan2(endPos.z - centerPos.z, endPos.x - centerPos.x);
            double deltaAngle = endAngle - startAngle;

            if (counterClockwise) {
                if (deltaAngle > 0) deltaAngle -= 2 * Math.PI;
            } else {
                if (deltaAngle < 0) deltaAngle += 2 * Math.PI;
            }
            double angle = startAngle + time * deltaAngle;

            Vec3d projectedPos = GolemMath.posFromAngle(centerPos, angle, true);
            System.out.println("Particles should spawn on " + projectedPos);
            ((ServerWorld) world).spawnParticles(ParticleTypes.END_ROD, projectedPos.getX(), startPos.getY(), projectedPos.getZ(), 3, 0, 0, 0, 0);


            /* Move of main arm */
            double x = startPos.x + time * (projectedPos.x - startPos.x);
            double z = startPos.z + time * (projectedPos.z - startPos.z);

            Vec3d vec3d = new Vec3d(x, 0, z);

            /* Setting up Y */
            double height = 2; //Math.max(distance * 0.3, 2);

            double y = startPos.y + height * (4 * time * (1 - time));

            return vec3d.add(0, y, 0);
        } else {
            double startAngle = Math.atan2(startPos.z - centerPos.z, startPos.x - centerPos.x);
            double endAngle = Math.atan2(endPos.z - centerPos.z, endPos.x - centerPos.x);
            double deltaAngle = endAngle - startAngle;

            if (counterClockwise) {
                if (deltaAngle > 0) deltaAngle -= 2 * Math.PI;
            } else {
                if (deltaAngle < 0) deltaAngle += 2 * Math.PI;
            }
            double angle = startAngle + time * deltaAngle;

            double height = endPos.y - startPos.y > 0 ? endPos.y - startPos.y + 1 : 1;
            double y = startPos.y + height * (-4 * Math.pow(time, 2) + 4 * time);

            Vec3d vec3d = GolemMath.posFromAngle(centerPos, angle, true).add(0, y, 0);

            return vec3d;
        }
    }

    public static double getDistance(Vec3d startPos, Vec3d endPos, Vec3d centerPos, float armDegree, double distanceToCenter) {
        if (distanceToCenter < 5) {
            return Math.sqrt(Math.pow(endPos.x - startPos.x, 2) + Math.pow(endPos.z - startPos.z, 2));
        } else {
            Vec3d projectedPos = GolemMath.posFromAngle(centerPos, GolemMath.addDegree(armDegree, 90), false);

            double distance1 = Math.sqrt(Math.pow(projectedPos.x - startPos.x, 2) + Math.pow(projectedPos.z - startPos.z, 2));
            double distance2 = Math.sqrt(Math.pow(endPos.x - projectedPos.x, 2) + Math.pow(endPos.z - projectedPos.z, 2));
            return distance1 + distance2;
        }
    }

    public static Vec3d getPosInFond(TowerGolemEntity mob, float yawDegree) {
        double d = Math.PI / 2 + Math.toRadians(yawDegree);
        double deltaX = Math.cos(d) * 3;
        double deltaZ = Math.sin(d) * 3;

        return mob.getPos().add(deltaX, 0, deltaZ);
    }

    public static Vec3d getPosInFond(TowerGolemEntity mob, Vec3d target) {
        float toTargetDegree = (float) ((Math.toDegrees(Math.atan2(target.x - mob.getPos().x, target.z - mob.getPos().z)) % 360) * -1);
        return GolemMath.getPosInFond(mob, toTargetDegree);
    }

    public static boolean getOptimalStartDirection(double startAngle, double firstAngle/*45*/, double secondAngle/*315*/) {
        if (firstAngle < startAngle) firstAngle += 360;
        if (secondAngle > startAngle) secondAngle -= 360;

        double distanceClockwise = Math.abs(firstAngle - startAngle);
        double distanceCounterClockwise = Math.abs(secondAngle - startAngle);
        if (distanceCounterClockwise < distanceClockwise /* Means that it is from 315 to 360 */) {
            return false;
        } else {
            return true;
        }

    }
    public static boolean getClockwise(double firstDegree, double secondDegree) {
        double distance = (((secondDegree - firstDegree) + 540f) % 360f) - 180f;

        return distance < 0;
    }

    public static Vec3d posFromAngle(Vec3d centerPos, double degree, boolean inRads) {
        double radius = TowerGolemEntity.ARM_RADIUS;

        if (!inRads) {
            degree = Math.toRadians(degree);
        }

        double x = centerPos.x + radius * Math.cos(degree);
        double z = centerPos.z + radius * Math.sin(degree);

        return new Vec3d(x, 0, z);
    }

    public static float subtractDegree(float degree, float subtrahend) {
        float result = degree - subtrahend;
        if (result < 0) { result += 360; }
        return result % 360;
    }
    public static float addDegree(float degree, float addend) {
        return (degree + addend) % 360;
    }
}
