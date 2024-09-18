package net.eps.lonsbattletowers.entity.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class TowerMimicAnimations {
    /*public static final Animation MIMIC_IDLE = Animation.Builder.create(2.5f).looping()
            .addBoneAnimation("Jaws", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createTranslationalVector(0f, -2f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Lower_Jaw", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createRotationalVector(12.5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Arm", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createTranslationalVector(0f, -2f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createRotationalVector(10f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Blade", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createRotationalVector(0f, 0f, -10f), Transformation.Interpolations.CUBIC), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createTranslationalVector(0f, -2f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createRotationalVector(10f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Blade", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createRotationalVector(0f, 0f, 10f), Transformation.Interpolations.CUBIC), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pick_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createRotationalVector(-3.5f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Shovel_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createRotationalVector(-4.5f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Scythe_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createRotationalVector(-3f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pitchfork_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createRotationalVector(-4.5f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR))).build();
    public static final Animation MIMIC_SPAWN = Animation.Builder.create(0.20833f)
            .addBoneAnimation("Lower_Jaw", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -11f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2083f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Upper_Jaw", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -14.4f, -2f), Transformation.Interpolations.LINEAR), new Keyframe(0.2083f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Upper_Jaw", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2083f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Barrel", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -14.4f, -2f), Transformation.Interpolations.LINEAR), new Keyframe(0.2083f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Barrel", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2083f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Arm", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(-7f, -9f, 9f), Transformation.Interpolations.LINEAR), new Keyframe(0.2083f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Blade", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 90f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2083f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Connector", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(-1f, 0f, 1f), Transformation.Interpolations.LINEAR), new Keyframe(0.2083f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(7f, -9f, 8f), Transformation.Interpolations.LINEAR), new Keyframe(0.2083f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Connector", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(1f, 0f, 1f), Transformation.Interpolations.LINEAR), new Keyframe(0.2083f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Blade", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, -90f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2083f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC))).build();
    public static final Animation MIMIC_WALK = Animation.Builder.create(3.35f).looping()
            .addBoneAnimation("Jaws", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.3433333f, AnimationHelper.createTranslationalVector(0f, -2f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.6766665f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Lower_Jaw", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createRotationalVector(12.5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Arm", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createTranslationalVector(0f, -2f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createRotationalVector(10f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Blade", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createRotationalVector(0f, 0f, -10f), Transformation.Interpolations.CUBIC), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createTranslationalVector(0f, -2f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createRotationalVector(10f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Blade", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.25f, AnimationHelper.createRotationalVector(0f, 0f, 10f), Transformation.Interpolations.CUBIC), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Head", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 5f), Transformation.Interpolations.CUBIC), new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(0f, 0f, 3f), Transformation.Interpolations.CUBIC), new Keyframe(0.8343334f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, -5f), Transformation.Interpolations.CUBIC), new Keyframe(1.5f, AnimationHelper.createRotationalVector(0f, 0f, -3f), Transformation.Interpolations.CUBIC), new Keyframe(1.6766667f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.8343333f, AnimationHelper.createRotationalVector(0f, 0f, 5f), Transformation.Interpolations.CUBIC), new Keyframe(2.3433335f, AnimationHelper.createRotationalVector(0f, 0f, 3f), Transformation.Interpolations.CUBIC), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.6766665f, AnimationHelper.createRotationalVector(0f, 0f, -5f), Transformation.Interpolations.CUBIC), new Keyframe(3.1676665f, AnimationHelper.createRotationalVector(0f, 0f, -3f), Transformation.Interpolations.CUBIC), new Keyframe(3.35f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pick", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(-45.91f, -1.18f, -59.09f), Transformation.Interpolations.CUBIC), new Keyframe(0.8343334f, AnimationHelper.createRotationalVector(-60.91f, -1.18f, -59.09f), Transformation.Interpolations.LINEAR), new Keyframe(1.6766667f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.8343333f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(2.3433335f, AnimationHelper.createRotationalVector(-45.91f, -1.18f, -59.09f), Transformation.Interpolations.CUBIC), new Keyframe(2.5f, AnimationHelper.createRotationalVector(-60.91f, -1.18f, -59.09f), Transformation.Interpolations.LINEAR), new Keyframe(3.35f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pick_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(-10f, 0f, -30f), Transformation.Interpolations.CUBIC), new Keyframe(1.8343333f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.3433335f, AnimationHelper.createRotationalVector(-10f, 0f, -30f), Transformation.Interpolations.CUBIC), new Keyframe(3.35f, AnimationHelper.createRotationalVector(-1.08f, 0f, -3.24f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Shovel", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(1.49f, 2.78f, 3.24f), Transformation.Interpolations.LINEAR), new Keyframe(0.35f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.5f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(1f, AnimationHelper.createRotationalVector(18.72f, 6.95f, 8.1f), Transformation.Interpolations.CUBIC), new Keyframe(1.1676667f, AnimationHelper.createRotationalVector(3.72f, 6.95f, 8.1f), Transformation.Interpolations.LINEAR), new Keyframe(2f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.1676665f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(2.6766665f, AnimationHelper.createRotationalVector(18.72f, 6.95f, 8.1f), Transformation.Interpolations.CUBIC), new Keyframe(2.8343335f, AnimationHelper.createRotationalVector(3.72f, 6.95f, 8.1f), Transformation.Interpolations.LINEAR), new Keyframe(3.35f, AnimationHelper.createRotationalVector(1.49f, 2.78f, 3.24f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Shovel_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-13.91f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1f, AnimationHelper.createRotationalVector(-30f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(2.1676665f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.6766665f, AnimationHelper.createRotationalVector(-30f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(3.35f, AnimationHelper.createRotationalVector(-13.91f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Scythe", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-60.91f, 1.18f, 59.09f), Transformation.Interpolations.LINEAR), new Keyframe(0.8343334f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(1.5f, AnimationHelper.createRotationalVector(-45.91f, 1.18f, 59.09f), Transformation.Interpolations.CUBIC), new Keyframe(1.6766667f, AnimationHelper.createRotationalVector(-60.91f, 1.18f, 59.09f), Transformation.Interpolations.LINEAR), new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(2.6766665f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(3.1676665f, AnimationHelper.createRotationalVector(-45.91f, 1.18f, 59.09f), Transformation.Interpolations.CUBIC), new Keyframe(3.35f, AnimationHelper.createRotationalVector(-60.91f, 1.18f, 59.09f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Scythe_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-9.53f, 0f, 28.6f), Transformation.Interpolations.CUBIC), new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.5f, AnimationHelper.createRotationalVector(-10f, 0f, 30f), Transformation.Interpolations.CUBIC), new Keyframe(2.6766665f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(3.1676665f, AnimationHelper.createRotationalVector(-10f, 0f, 30f), Transformation.Interpolations.CUBIC), new Keyframe(3.35f, AnimationHelper.createRotationalVector(-9.53f, 0f, 28.6f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pitchfork", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(19.29f, -4.89f, -5.7f), Transformation.Interpolations.CUBIC), new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(18.72f, -6.95f, -8.1f), Transformation.Interpolations.CUBIC), new Keyframe(0.35f, AnimationHelper.createRotationalVector(3.72f, -6.95f, -8.1f), Transformation.Interpolations.LINEAR), new Keyframe(1.1676667f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.3433333f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(1.8343333f, AnimationHelper.createRotationalVector(18.72f, -6.95f, -8.1f), Transformation.Interpolations.CUBIC), new Keyframe(2f, AnimationHelper.createRotationalVector(3.72f, -6.95f, -8.1f), Transformation.Interpolations.LINEAR), new Keyframe(2.8343335f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(3f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(3.35f, AnimationHelper.createRotationalVector(19.29f, -4.89f, -5.7f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pitchfork_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-22.22f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(-30f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(1.3433333f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(1.8343333f, AnimationHelper.createRotationalVector(-30f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(3f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(3.35f, AnimationHelper.createRotationalVector(-22.22f, 0f, 0f), Transformation.Interpolations.CUBIC))).build();
    public static final Animation MIMIC_ATTACK = Animation.Builder.create(0.7f)
            .addBoneAnimation("Head", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0f, 2f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, -5f), Transformation.Interpolations.CUBIC), new Keyframe(0.7f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Head", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.375f, AnimationHelper.createRotationalVector(10f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(-0.56f, -24.93f, 2.19f), Transformation.Interpolations.LINEAR), new Keyframe(0.3f, AnimationHelper.createRotationalVector(-1.78f, 19.45f, -4.87f), Transformation.Interpolations.LINEAR), new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(-0.56f, -24.93f, 2.19f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.3f, AnimationHelper.createRotationalVector(-3.37f, 24.53f, -4.29f), Transformation.Interpolations.LINEAR), new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Blade", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.3f, AnimationHelper.createTranslationalVector(-4.33f, 0.57f, -1.01f), Transformation.Interpolations.LINEAR), new Keyframe(0.4583433f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Blade", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.3f, AnimationHelper.createRotationalVector(1.74f, -24.69f, 3.17f), Transformation.Interpolations.LINEAR), new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Connector", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.3f, AnimationHelper.createTranslationalVector(-3.84f, 0.53f, -1.14f), Transformation.Interpolations.LINEAR), new Keyframe(0.4583433f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Connector", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.3f, AnimationHelper.createRotationalVector(-0.79f, 7.38f, -1.1f), Transformation.Interpolations.LINEAR), new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(-0.33f, 19.94f, -1.66f), Transformation.Interpolations.LINEAR), new Keyframe(0.3f, AnimationHelper.createRotationalVector(-1.49f, -19.5f, 4.62f), Transformation.Interpolations.LINEAR), new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(-0.33f, 19.94f, -1.66f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.3f, AnimationHelper.createRotationalVector(-3.37f, -24.53f, 4.29f), Transformation.Interpolations.LINEAR), new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Connector", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.3f, AnimationHelper.createTranslationalVector(3.84f, 0.53f, -1.14f), Transformation.Interpolations.LINEAR), new Keyframe(0.4583433f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Connector", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.3f, AnimationHelper.createRotationalVector(-0.79f, -7.38f, 1.1f), Transformation.Interpolations.LINEAR), new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Blade", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.3f, AnimationHelper.createTranslationalVector(4.33f, 0.57f, -1.01f), Transformation.Interpolations.LINEAR), new Keyframe(0.4583433f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Blade", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.3f, AnimationHelper.createRotationalVector(1.74f, 24.69f, -3.17f), Transformation.Interpolations.LINEAR), new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Lower_Jaw", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.375f, AnimationHelper.createRotationalVector(20f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(-25f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pick_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(2.55f, -4.16f, -1.21f), Transformation.Interpolations.CUBIC), new Keyframe(0.375f, AnimationHelper.createRotationalVector(-2.46f, 4.21f, 1.03f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(-4.87f, 8.45f, 1.89f), Transformation.Interpolations.CUBIC), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Shovel_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(-2.25f, -0.08f, 1.08f), Transformation.Interpolations.CUBIC), new Keyframe(0.375f, AnimationHelper.createRotationalVector(2.44f, 2.52f, -0.39f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(4.81f, 5.09f, -0.77f), Transformation.Interpolations.CUBIC), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Scythe_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(2.55f, 4.16f, 1.21f), Transformation.Interpolations.CUBIC), new Keyframe(0.375f, AnimationHelper.createRotationalVector(-2.46f, -4.21f, -1.03f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(-4.87f, -8.45f, -1.89f), Transformation.Interpolations.CUBIC), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pitchfork_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(-2.25f, 0.08f, -1.08f), Transformation.Interpolations.CUBIC), new Keyframe(0.375f, AnimationHelper.createRotationalVector(2.44f, -2.52f, 0.39f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(4.81f, -5.09f, 0.77f), Transformation.Interpolations.CUBIC), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR))).build();
    public static final Animation MIMIC_SHOOT_START = Animation.Builder.create(0.3f)
            .addBoneAnimation("Pick_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Shovel_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Scythe_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pitchfork_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Head", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createTranslationalVector(0f, -4f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createRotationalVector(0f, -30f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createRotationalVector(0f, 30f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Lower_Jaw", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.CUBIC))).build();
    public static final Animation MIMIC_SHOOT_SHOOT = Animation.Builder.create(0.5416766f)
            .addBoneAnimation("Pick_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.125f, AnimationHelper.createRotationalVector(-2.3f, -8.25f, -2.62f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Shovel_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.125f, AnimationHelper.createRotationalVector(-7.88f, -4.79f, -1.35f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Scythe_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.125f, AnimationHelper.createRotationalVector(-2.3f, 8.25f, 2.62f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pitchfork_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.125f, AnimationHelper.createRotationalVector(-7.88f, 4.79f, 1.35f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Head", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -4f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0f, -4f, 4f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createTranslationalVector(0f, -4f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, -30f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.125f, AnimationHelper.createRotationalVector(0f, -70f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(0f, -30f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 30f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.125f, AnimationHelper.createRotationalVector(0f, 70f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(0f, 30f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Lower_Jaw", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.125f, AnimationHelper.createRotationalVector(45f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.LINEAR))).build();
    public static final Animation MIMIC_SHOOT_END = Animation.Builder.create(0.3f)
            .addBoneAnimation("Pick_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Shovel_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Scythe_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pitchfork_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Head", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -4f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, -30f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 30f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Lower_Jaw", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.3f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC))).build();


    public static final Animation MIMIC_LAND = Animation.Builder.create(0.7f)
            .addBoneAnimation("Head", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2f, AnimationHelper.createTranslationalVector(0f, -5f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Blade", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Blade", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Lower_Jaw", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pick", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2f, AnimationHelper.createTranslationalVector(0f, -6f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pick", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2f, AnimationHelper.createRotationalVector(1.73f, -19.01f, -13.27f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Shovel", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2f, AnimationHelper.createTranslationalVector(0f, -6f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Shovel", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2f, AnimationHelper.createRotationalVector(10f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Scythe", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2f, AnimationHelper.createTranslationalVector(0f, -6f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Scythe", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2f, AnimationHelper.createRotationalVector(-1.73f, 19.01f, 13.27f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pitchfork", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2f, AnimationHelper.createTranslationalVector(0f, -6f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pitchfork", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2f, AnimationHelper.createRotationalVector(10f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Barrel", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.2f, AnimationHelper.createTranslationalVector(0f, -1f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.7f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR))).build();


    public static final Animation MIMIC_JUMP_START = Animation.Builder.create(0.9f)
            .addBoneAnimation("Pick_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.375f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Shovel_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.375f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Scythe_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.375f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pitchfork_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.375f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Head", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -7f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pick", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pick", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.375f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.9f, AnimationHelper.createRotationalVector(-60f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pick_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.9f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Shovel", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Shovel", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.375f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.9f, AnimationHelper.createRotationalVector(-62.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Shovel_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.9f, AnimationHelper.createRotationalVector(20f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Scythe", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Scythe", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.375f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.9f, AnimationHelper.createRotationalVector(-60f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Scythe_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.9f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pitchfork", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pitchfork", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.375f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.9f, AnimationHelper.createRotationalVector(-60f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pitchfork_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.9f, AnimationHelper.createRotationalVector(20f, 0f, 0f), Transformation.Interpolations.LINEAR))).build();

    public static final Animation MIMIC_JUMP_LAND = Animation.Builder.create(0.625f)
            .addBoneAnimation("Pick_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.125f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.25f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Shovel_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.125f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.25f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Scythe_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.125f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.25f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pitchfork_Holder", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.125f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.25f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Head", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, -7f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pick", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pick", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-60f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.125f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.25f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pick_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.125f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Shovel", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Shovel", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-62.5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.125f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.25f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Shovel_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(20f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.125f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Scythe", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Scythe", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-60f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.125f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.25f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Scythe_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.125f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pitchfork", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pitchfork", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(-60f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.125f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.25f, AnimationHelper.createRotationalVector(5f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pitchfork_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(20f, 0f, 0f), Transformation.Interpolations.LINEAR), new Keyframe(0.125f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.LINEAR))).build();

    public static final Animation BREAK = Animation.Builder.create(0.7f)
            .addBoneAnimation("Leg_Pick", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, -10f, 2f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -15f, -3f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pick", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pick_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Shovel", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, -10f, 2f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -15f, -3f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Shovel", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Shovel_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Scythe", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, -10f, 2f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -15f, -3f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Scythe", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Scythe_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pitchfork", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, -10f, 2f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -15f, -3f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pitchfork", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pitchfork_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Head", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, -10.5f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.2916767f, AnimationHelper.createTranslationalVector(0f, -9.5f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -10.5f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Arm", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, 2f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(0f, -4.51f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Base", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.4167667f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-32.4f, -2.68f, -19.22f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Blade", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createTranslationalVector(-1.6f, 2.5f, 2f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Blade", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-5f, 0f, 10f), Transformation.Interpolations.CUBIC), new Keyframe(0.4167667f, AnimationHelper.createRotationalVector(-5f, 0f, 10f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-1.92f, 4.62f, -62.58f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Connector", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createTranslationalVector(-1.9f, 2.6f, 2.2f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Connector", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.4167667f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(0f, -15f, -55f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, 2f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -3.51f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Base", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -4f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.375f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(-57.92f, -6.07f, 7.96f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Connector", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(2f, 3f, 3.2f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Connector", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.375f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 70f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Blade", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(1.4f, 2.7f, 3.1f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Blade", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-5f, 0f, -10f), Transformation.Interpolations.CUBIC), new Keyframe(0.375f, AnimationHelper.createRotationalVector(-5f, 0f, -10f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(3.64f, 7.11f, 52.42f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Upper_Jaw", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createRotationalVector(29f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.7f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Barrel", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createTranslationalVector(0f, -4.5f, -2.3f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0f, -4f, -2.3f), Transformation.Interpolations.CUBIC), new Keyframe(0.7f, AnimationHelper.createTranslationalVector(0f, -4.5f, -2.3f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Barrel", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.CUBIC))).build();

    public static final Animation BREAK_BASE = Animation.Builder.create(0.7f)
            .addBoneAnimation("Head", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, -10.5f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.2916767f, AnimationHelper.createTranslationalVector(0f, -9.5f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -10.5f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Arm", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, 2f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(0f, -4.51f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Base", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createTranslationalVector(0f, -3f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.4167667f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-32.4f, -2.68f, -19.22f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Blade", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createTranslationalVector(-1.6f, 2.5f, 2f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Blade", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-5f, 0f, 10f), Transformation.Interpolations.CUBIC), new Keyframe(0.4167667f, AnimationHelper.createRotationalVector(-5f, 0f, 10f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-1.92f, 4.62f, -62.58f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Connector", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createTranslationalVector(-1.9f, 2.6f, 2.2f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Connector", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.4167667f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(0f, -15f, -55f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, 2f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -3.51f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Base", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -4f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.375f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(-57.92f, -6.07f, 7.96f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Connector", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(2f, 3f, 3.2f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Connector", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0.375f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 70f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Blade", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(1.4f, 2.7f, 3.1f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Blade", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-5f, 0f, -10f), Transformation.Interpolations.CUBIC), new Keyframe(0.375f, AnimationHelper.createRotationalVector(-5f, 0f, -10f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createRotationalVector(3.64f, 7.11f, 52.42f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Upper_Jaw", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createRotationalVector(29f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.7f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Barrel", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createTranslationalVector(0f, -4.5f, -2.3f), Transformation.Interpolations.CUBIC), new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0f, -4f, -2.3f), Transformation.Interpolations.CUBIC), new Keyframe(0.7f, AnimationHelper.createTranslationalVector(0f, -4.5f, -2.3f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Barrel", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-10f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(30f, 0f, 0f), Transformation.Interpolations.CUBIC))).build();
    public static final Animation BREAK_LEGS = Animation.Builder.create(0.7f)
            .addBoneAnimation("Leg_Pick", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, -10f, 2f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -15f, -3f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pick", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pick_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Shovel", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, -10f, 2f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -15f, -3f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Shovel", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Shovel_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Scythe", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, -10f, 2f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -15f, -3f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Scythe", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Scythe_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pitchfork", new Transformation(Transformation.Targets.TRANSLATE, new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, -10f, 2f), Transformation.Interpolations.CUBIC), new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -15f, -3f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pitchfork", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(25f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f), Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pitchfork_Base", new Transformation(Transformation.Targets.ROTATE, new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-15f, 0f, 0f), Transformation.Interpolations.CUBIC), new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(15f, 0f, 0f), Transformation.Interpolations.CUBIC))).build();
*/
    public static final Animation MIMIC_IDLE = Animation.Builder.create(2.5f).looping()
            .addBoneAnimation("Jaws",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createTranslationalVector(0f, -2f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Lower_Jaw",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(12.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Arm",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createTranslationalVector(0f, -2f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(10f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Blade",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(0f, 0f, -10f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createTranslationalVector(0f, -2f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(10f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Blade",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(0f, 0f, 10f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pick_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(-3.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Shovel_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(-4.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Scythe_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(-3f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pitchfork_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(-4.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
    public static final Animation MIMIC_WALK = Animation.Builder.create(3.3433335f).looping()
            .addBoneAnimation("Jaws",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.3433333f, AnimationHelper.createTranslationalVector(0f, -2f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.6766665f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Lower_Jaw",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(12.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Arm",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createTranslationalVector(0f, -2f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(10f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Blade",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(0f, 0f, -10f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createTranslationalVector(0f, -2f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(10f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Blade",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.25f, AnimationHelper.createRotationalVector(0f, 0f, 10f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Head",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 5f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(0f, 0f, 3f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.8343334f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, -5f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.5f, AnimationHelper.createRotationalVector(0f, 0f, -3f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.6766667f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.8343333f, AnimationHelper.createRotationalVector(0f, 0f, 5f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2.3433335f, AnimationHelper.createRotationalVector(0f, 0f, 3f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.6766665f, AnimationHelper.createRotationalVector(0f, 0f, -5f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(3.1676665f, AnimationHelper.createRotationalVector(0f, 0f, -3f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(3.3433335f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pick",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(15f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(-45.91f, -1.18f, -59.09f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.8343334f, AnimationHelper.createRotationalVector(-53.98f, 2.26f, -48.15f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.6766667f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.8343333f, AnimationHelper.createRotationalVector(15f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2.3433335f, AnimationHelper.createRotationalVector(-45.91f, -1.18f, -59.09f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(-53.98f, 2.26f, -48.15f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(3.3433335f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pick_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-1.08f, 0f, -3.24f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(-15f, 0f, -30f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.8343333f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.3433335f, AnimationHelper.createRotationalVector(-15f, 0f, -30f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(3.3433335f, AnimationHelper.createRotationalVector(-1.08f, 0f, -3.24f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Shovel",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(3.39f, 2.78f, 3.24f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.35f, AnimationHelper.createRotationalVector(1f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(15f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(18.72f, 6.95f, 8.1f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.1676667f, AnimationHelper.createRotationalVector(4.32f, 6.95f, 8.1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2f, AnimationHelper.createRotationalVector(1f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.1676665f, AnimationHelper.createRotationalVector(15f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2.6766665f, AnimationHelper.createRotationalVector(18.72f, 6.95f, 8.1f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2.8343335f, AnimationHelper.createRotationalVector(3.72f, 6.95f, 8.1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(3.3433335f, AnimationHelper.createRotationalVector(3.39f, 2.78f, 3.24f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Shovel_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-13.91f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(-30f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2.1676665f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.6766665f, AnimationHelper.createRotationalVector(-30f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(3.3433335f, AnimationHelper.createRotationalVector(-13.91f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Scythe",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-66.31f, 1.18f, 59.09f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.8343334f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(15f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.5f, AnimationHelper.createRotationalVector(-58.15f, 4.11f, 69.64f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.6766667f, AnimationHelper.createRotationalVector(-66.31f, 1.18f, 59.09f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.6766665f, AnimationHelper.createRotationalVector(15f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(3.1676665f, AnimationHelper.createRotationalVector(-58.15f, 4.11f, 69.64f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(3.3433335f, AnimationHelper.createRotationalVector(-66.31f, 1.18f, 59.09f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Scythe_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-9.53f, 0f, 28.6f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.5f, AnimationHelper.createRotationalVector(-15f, 0f, 30f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2.6766665f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(3.1676665f, AnimationHelper.createRotationalVector(-15f, 0f, 30f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(3.3433335f, AnimationHelper.createRotationalVector(-9.53f, 0f, 28.6f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pitchfork",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(19.29f, -4.89f, -5.7f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(18.72f, -6.95f, -8.1f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.35f, AnimationHelper.createRotationalVector(3.72f, -6.95f, -8.1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.1676667f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.3433333f, AnimationHelper.createRotationalVector(15f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.8343333f, AnimationHelper.createRotationalVector(18.72f, -6.95f, -8.1f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(2f, AnimationHelper.createRotationalVector(3.72f, -6.95f, -8.1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(2.8343335f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(3f, AnimationHelper.createRotationalVector(15f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(3.3433335f, AnimationHelper.createRotationalVector(19.29f, -4.89f, -5.7f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pitchfork_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-22.22f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(-30f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.3433333f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.8343333f, AnimationHelper.createRotationalVector(-30f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(3f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(3.3433335f, AnimationHelper.createRotationalVector(-22.22f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC))).build();
    public static final Animation MIMIC_ATTACK = Animation.Builder.create(0.7083434f)
            .addBoneAnimation("Head",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0f, 2f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, -5f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.6766666f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Head",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(10f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(-0.56f, -24.93f, 2.19f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-1.78f, 19.45f, -4.87f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(-0.56f, -24.93f, 2.19f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-3.37f, 24.53f, -4.29f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Blade",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.2916767f, AnimationHelper.createTranslationalVector(-4.33f, 0.57f, -1.01f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.4583433f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Blade",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(1.74f, -24.69f, 3.17f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Connector",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.2916767f, AnimationHelper.createTranslationalVector(-3.84f, 0.53f, -1.14f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.4583433f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Connector",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-0.79f, 7.38f, -1.1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(-0.33f, 19.94f, -1.66f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-1.49f, -19.5f, 4.62f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(-0.33f, 19.94f, -1.66f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-3.37f, -24.53f, 4.29f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Connector",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.2916767f, AnimationHelper.createTranslationalVector(3.84f, 0.53f, -1.14f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.4583433f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Connector",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-0.79f, -7.38f, 1.1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Blade",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.16766666f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.2916767f, AnimationHelper.createTranslationalVector(4.33f, 0.57f, -1.01f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.4583433f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Blade",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(1.74f, 24.69f, -3.17f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.4583433f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Lower_Jaw",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(20f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(-25f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pick_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(2.55f, -4.16f, -1.21f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(-2.46f, 4.21f, 1.03f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(-4.87f, 8.45f, 1.89f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Shovel_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(-2.25f, -0.08f, 1.08f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(2.44f, 2.52f, -0.39f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(4.81f, 5.09f, -0.77f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Scythe_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(2.55f, 4.16f, 1.21f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(-2.46f, -4.21f, -1.03f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(-4.87f, -8.45f, -1.89f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pitchfork_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.16766666f, AnimationHelper.createRotationalVector(-2.25f, 0.08f, -1.08f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(2.44f, -2.52f, 0.39f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(4.81f, -5.09f, 0.77f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.6766666f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
    public static final Animation MIMIC_SHOOT_START = Animation.Builder.create(0.2916767f)
            .addBoneAnimation("Pick_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Shovel_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Scythe_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pitchfork_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Head",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createTranslationalVector(0f, -4f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(0f, -30f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(0f, 30f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Lower_Jaw",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(30f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC))).build();
    public static final Animation MIMIC_SHOOT_SHOOT = Animation.Builder.create(0.5416766f)
            .addBoneAnimation("Pick_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(-2.3f, -8.25f, -2.62f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Shovel_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(-7.88f, -4.79f, -1.35f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Scythe_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(-2.3f, 8.25f, 2.62f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pitchfork_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(-7.88f, 4.79f, 1.35f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Head",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -4f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0f, -4f, 4f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createTranslationalVector(0f, -4f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, -30f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(0f, -70f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(0f, -30f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 30f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(0f, 70f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(0f, 30f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Lower_Jaw",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(30f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(45f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(30f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
    public static final Animation MIMIC_SHOOT_END = Animation.Builder.create(0.2916767f)
            .addBoneAnimation("Pick_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Shovel_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Scythe_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pitchfork_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Head",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -4f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, -30f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 30f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Lower_Jaw",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(30f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC))).build();
    public static final Animation MIMIC_LAND = Animation.Builder.create(0.5f)
            .addBoneAnimation("Head",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -5f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(25f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Right_Blade",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(25f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(25f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Left_Arm_Blade",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(25f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Lower_Jaw",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(25f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pick",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -6f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pick",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(1.73f, -19.01f, -13.27f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Shovel",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -6f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Shovel",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(10f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Scythe",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -6f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Scythe",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-1.73f, 19.01f, 13.27f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pitchfork",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -6f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pitchfork",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(10f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Barrel",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -1f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
    public static final Animation MIMIC_JUMP_START = Animation.Builder.create(0.875f)
            .addBoneAnimation("Pick_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(-10f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Shovel_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(-10f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Scythe_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(-10f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pitchfork_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(-10f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Head",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -7f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pick",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pick",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.875f, AnimationHelper.createRotationalVector(-60f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pick_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.875f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Shovel",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Shovel",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.875f, AnimationHelper.createRotationalVector(-62.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Shovel_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.875f, AnimationHelper.createRotationalVector(20f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Scythe",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Scythe",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.875f, AnimationHelper.createRotationalVector(-60f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Scythe_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.875f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pitchfork",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pitchfork",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.875f, AnimationHelper.createRotationalVector(-60f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pitchfork_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.875f, AnimationHelper.createRotationalVector(20f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
    public static final Animation MIMIC_JUMP_LAND = Animation.Builder.create(0.625f)
            .addBoneAnimation("Pick_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(-10f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Shovel_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(-10f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Scythe_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(-10f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pitchfork_Holder",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(-10f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Head",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, -7f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pick",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pick",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-60f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pick_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Shovel",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Shovel",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-62.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Shovel_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(20f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Scythe",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Scythe",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-60f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Scythe_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(17.5f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pitchfork",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Leg_Pitchfork",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(-60f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("Pitchfork_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(20f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(0.125f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR))).build();
    public static final Animation BREAK = Animation.Builder.create(1.375f)
            .addBoneAnimation("Leg_Pick",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, -10f, 2f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -15f, -3f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pick",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(25f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(35f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pick_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-15f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createRotationalVector(-21.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Shovel",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, -10f, 2f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -15f, -3f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Shovel",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(25f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(45f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Shovel_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-15f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createRotationalVector(-38.62f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.8343334f, AnimationHelper.createRotationalVector(-52.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(-57.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.1676667f, AnimationHelper.createRotationalVector(-55f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.375f, AnimationHelper.createRotationalVector(-52.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Scythe",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, -10f, 2f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -15f, -3f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Scythe",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(25f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(35f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Scythe_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-15f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createRotationalVector(-22f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pitchfork",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, -10f, 2f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -15f, -3f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pitchfork",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(25f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(40.37f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pitchfork_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-15f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createRotationalVector(-38.62f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.8343334f, AnimationHelper.createRotationalVector(-52.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1f, AnimationHelper.createRotationalVector(-57.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.1676667f, AnimationHelper.createRotationalVector(-55f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(1.375f, AnimationHelper.createRotationalVector(-52.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Head",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, -10.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createTranslationalVector(0f, -9.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -10.5f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Arm",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, 2f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(0f, -4.51f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Base",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.4167667f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-32.4f, -2.68f, -19.22f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Blade",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createTranslationalVector(-1.6f, 2.5f, 2f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Blade",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-5f, 0f, 10f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.4167667f, AnimationHelper.createRotationalVector(-5f, 0f, 10f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(-1.92f, 4.62f, -62.58f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Connector",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.4167667f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createTranslationalVector(-1.9f, 2.6f, 2.2f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Connector",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.4167667f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(0f, -15f, -55f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, 2f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -3.51f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Base",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -4f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(-57.92f, -6.07f, 7.96f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Connector",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(2f, 3f, 3.2f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Connector",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 70f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Blade",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createTranslationalVector(1.4f, 2.7f, 3.1f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Blade",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-5f, 0f, -10f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.375f, AnimationHelper.createRotationalVector(-5f, 0f, -10f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5f, AnimationHelper.createRotationalVector(3.64f, 7.11f, 52.42f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Upper_Jaw",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-10f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(30f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createRotationalVector(29f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.7083434f, AnimationHelper.createRotationalVector(30f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Barrel",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createTranslationalVector(0f, 0.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createTranslationalVector(0f, -4.5f, -2.3f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.625f, AnimationHelper.createTranslationalVector(0f, -4f, -2.3f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.7083434f, AnimationHelper.createTranslationalVector(0f, -4.5f, -2.3f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Barrel",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.20834334f, AnimationHelper.createRotationalVector(-10f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.5416766f, AnimationHelper.createRotationalVector(30f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC))).build();
    public static final Animation SPAWN = Animation.Builder.create(0.35f)
            .addBoneAnimation("Leg_Pick",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(-1f, -33f, 2f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pick",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.08343333f, AnimationHelper.createRotationalVector(-20.3f, -8.91f, -92.72f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.35f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pick",
                    new Transformation(Transformation.Targets.SCALE,
                            new Keyframe(0f, AnimationHelper.createScalingVector(0.6f, 1f, 0.3f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pick_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.041676664f, AnimationHelper.createRotationalVector(89.03f, 6.52f, -8.34f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Shovel",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(-1f, -32f, -3f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Shovel",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.08343333f, AnimationHelper.createRotationalVector(58.16f, -2.69f, -13.33f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.35f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Shovel",
                    new Transformation(Transformation.Targets.SCALE,
                            new Keyframe(0f, AnimationHelper.createScalingVector(0.6f, 1f, 0.3f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Shovel_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.041676664f, AnimationHelper.createRotationalVector(88.76f, 1.09f, 16.23f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Scythe",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(-1f, -33f, 2f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Scythe",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.08343333f, AnimationHelper.createRotationalVector(-20.3f, 8.91f, 92.72f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.35f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Scythe",
                    new Transformation(Transformation.Targets.SCALE,
                            new Keyframe(0f, AnimationHelper.createScalingVector(0.6f, 1f, 0.3f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Scythe_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.041676664f, AnimationHelper.createRotationalVector(89.03f, -6.52f, 8.34f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pitchfork",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(-1f, -32f, -3f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pitchfork",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.08343333f, AnimationHelper.createRotationalVector(58.16f, 2.69f, 13.33f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.35f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Leg_Pitchfork",
                    new Transformation(Transformation.Targets.SCALE,
                            new Keyframe(0f, AnimationHelper.createScalingVector(0.6f, 1f, 0.3f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createScalingVector(1f, 1f, 1f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Pitchfork_Base",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.041676664f, AnimationHelper.createRotationalVector(88.76f, -1.09f, -16.23f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Head",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.125f, AnimationHelper.createTranslationalVector(0f, -10.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Arm",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.35f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.08343333f, AnimationHelper.createRotationalVector(0f, 132.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.35f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Right_Blade",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.08343333f, AnimationHelper.createRotationalVector(0f, 55f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.35f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0.35f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.08343333f, AnimationHelper.createRotationalVector(0f, -132.5f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.35f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Left_Arm_Blade",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0.08343333f, AnimationHelper.createRotationalVector(0f, -55f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.35f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Upper_Jaw",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(30f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Barrel",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -4f, -2.5f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC)))
            .addBoneAnimation("Barrel",
                    new Transformation(Transformation.Targets.ROTATE,
                            new Keyframe(0f, AnimationHelper.createRotationalVector(30f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC),
                            new Keyframe(0.25f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.CUBIC))).build();

}