package net.eps.lonsbattletowers.entity.custom.golem;

import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class GolemWanderAroundGoal extends WanderAroundGoal {

    public GolemWanderAroundGoal(PathAwareEntity mob, double speed) {
        this(mob, speed, 120);
    }

    public GolemWanderAroundGoal(PathAwareEntity mob, double speed, int chance) {
        this(mob, speed, chance, true);
    }

    public GolemWanderAroundGoal(PathAwareEntity entity, double speed, int chance, boolean canDespawn) {
        super(entity, speed, chance, canDespawn);
    }

    public void start() {
        EntityNavigation navigation = this.mob.getNavigation();
        Path path = navigation.findPathTo(this.targetX, this.targetY, this.targetZ, 1);
        navigation.startMovingAlong(path, this.speed);

        /*
        System.out.println("GolemWanderAroundGoal started");
        System.out.println("Path is " + path);
        //path.getLastNode()

        Vec3d pos = new Vec3d(this.targetX, this.targetY, this.targetZ);
        System.out.println("    (! VecPos !) МУС POS IS " + pos);

        for (TowerGolemArmPart armPart : ((TowerGolemEntity)this.mob).getBodyParts()) {
        System.out.println("    (! ! !) ARM " + armPart + " OF GOLEM IS GETTING PROCESSED");
        if (armPart.armPosOffset.isEmpty()) {
            throw new RuntimeException("armPosOffset is not defined");
        }
        Vec3d armPos = pos.add(armPart.armPosOffset.get().toCenterPos());
        System.out.println("    (! Pos !) ARM'S NEXT POS IS " + armPos);

        armPart.setPosition(armPos);
        System.out.println("    (! ! !) ARM'S POSITION SET");
        }

        if (path != null) {
            //path.setLength(10);
            //this.mob.getNavigation().startMovingAlong(path, this.speed);
        } else {
            //throw new RuntimeException("Path in GolemWanderAroundGoal is null");
        }

         */
    }
}
