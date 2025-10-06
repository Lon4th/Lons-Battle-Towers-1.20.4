package net.eps.lonsbattletowers.entity.custom.golem;

import net.eps.lonsbattletowers.entity.custom.TowerMimicEntity;
import net.eps.lonsbattletowers.entity.custom.golem.goals.TowerGolemMeleAttackGoal;
import net.eps.lonsbattletowers.entity.custom.golem.goals.TowerGolemStepGoal;
import net.eps.lonsbattletowers.entity.math.GolemMath;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.jar.Attributes;


public class TowerGolemEntity extends HostileEntity implements Monster {
    public static final int ARM_RADIUS = 4;

    public static final float FRONT_RIGHT_ARM_DEGREE = 50;
    public static final float FRONT_LEFT_ARM_DEGREE = 310;
    public static final float BACK_RIGHT_ARM_DEGREE = 135;
    public static final float BACK_LEFT_ARM_DEGREE = 225;

    private static final TrackedData<Boolean> IS_TURNING = DataTracker.registerData(TowerGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private final Map<Float, TowerGolemArmPart> armPartMap;
    public final List<TowerGolemArmPart> frontArmParts;
    public final List<TowerGolemArmPart> backArmParts;
    public final List<Optional<TowerGolemArmPart>> armsNowMoving;
    /**
     * indexes:
     *      1 - arm turning;
     *      2 - front arm moving;
     *      3 - back arm moving;
     *      4 - arm attacking;
     */

    private final List<TowerGolemArmPart> turningArmsPriorityList;
    private final List<TowerGolemArmPart> frontArmsPriorityList;
    private final List<TowerGolemArmPart> backArmsPriorityList;

    private boolean isInit;
    private int backArmsCooldownTime = 10;
    private int updateCountdownTicks;
    private double targetX = 0;
    private double targetY = 0;
    private double targetZ = 0;
    //public boolean isArmNowTurning = false;
    // public TowerGolemArmPart armNowTurning = null;

    //private final TowerGolemPart body;

    public TowerGolemEntity(EntityType<? extends TowerGolemEntity> entityType, World world) {
        super(entityType, world);
        //this.body = new TowerGolemPart(ModEntities.TOWER_GOLEM_ARM_PART, this, "body", EntityDimensions.changing(1.0f, 1.0f), 1.0f);
        //this.getWorld().spawnEntity(this.body);

        this.armPartMap = new HashMap<>();
        this.frontArmParts = new ArrayList<>();
        this.backArmParts = new ArrayList<>();

        this.turningArmsPriorityList = new ArrayList<>(this.getBodyParts().size());
        this.frontArmsPriorityList = new ArrayList<>(2);
        this.backArmsPriorityList = new ArrayList<>(2);

        this.armsNowMoving = new ArrayList<>(5);

        this.isInit = true;
        this.updateCountdownTicks = 0;
        this.setStepHeight(1.0F);
    }

    @Override
    protected void initGoals() {
        //this.goalSelector.add(6, new GolemTurnAroundGoal(this));
        //this.goalSelector.add(6, new GolemLookAroundGoal(this, this.goalSelector));
        // Потом, чтобы заставить его двигаться к жертве, нужно будет разобраться с MoveToTargetGoal


        //this.goalSelector.add(5, new GolemWanderAroundGoal(this, 0.5));
        this.goalSelector.add(3, new TowerGolemMeleAttackGoal(this, this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED), true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, ArmorStandEntity.class, true));
        //this.goalSelector.add(4, new TrackTargetGoal

        super.initGoals();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(IS_TURNING, false);
    }

    @Override
    public void tick() {
        if (!getWorld().isClient()) {
            this.tickServer();
        }

        super.tick();
    }

    public void tickServer() {
        if (this.isInit) {
            for (int i = 45; i < 360; i += 90) {
                BlockPos blockPos = TowerGolemEntity.raycastPos(this, GolemMath.posFromAngle(this.getPos(), GolemMath.addDegree(i, 90), false).add(0, this.getY(), 0));
                TowerGolemArmPart arm = new TowerGolemArmPart(this, "arm", 1.0F, 1.0F);

                if (i == 315 || i == 45) {
                    this.spawnArm(arm, this.getPos(), Vec3d.ofBottomCenter(blockPos), this.frontArmParts, this.armPartMap, i == 315 ? FRONT_LEFT_ARM_DEGREE : FRONT_RIGHT_ARM_DEGREE);
                } else {
                    this.spawnArm(arm, this.getPos(), Vec3d.ofBottomCenter(blockPos), this.backArmParts, this.armPartMap, i == 225 ? BACK_LEFT_ARM_DEGREE : BACK_RIGHT_ARM_DEGREE);
                }
            }

            this.isInit = false;
        }

        /*
        for (TowerGolemArmPart armPart : this.armPartMap.values()) {
            if (this.getWorld() instanceof ServerWorld) {
                //System.out.println("|||||||||| OK ||||||||||||");
                ((ServerWorld) this.getWorld()).getChunkManager().sendToOtherNearbyPlayers(this, new EntityAttachS2CPacket(armPart, this));
            }
        }
        */


        if (!this.isOnGround()) {
            //System.out.println("golem is falling");
            double meanY = 0;

            for (TowerGolemArmPart armPart : armPartMap.values()) {
                meanY += armPart.getY();
            }

            meanY /= armPartMap.values().size();

            //System.out.println("mean is " + meanY);
            //System.out.println("Y is " + this.getY());
            if ((meanY - this.getY()) > 1) {
                //System.out.println("mean is higher");
                //this.setVelocity(Vec3d.ZERO);
                TowerGolemEntity.applyElasticity(this, new Vec3d(this.getX(), meanY, this.getZ()), (meanY - this.getY()));
            }
        }

        this.backArmsCooldownTime = Math.max(this.backArmsCooldownTime - 1, 0);
        //System.out.println("back arms cooldown is " + this.backArmsCooldownTime);
    }

    private static void applyElasticity(Entity entity, Vec3d targetPos, double distance) {
        double d = (targetPos.getX() - entity.getX()) / distance;
        double e = (targetPos.getY() - entity.getY()) / distance;
        double f = (targetPos.getZ() - entity.getZ()) / distance;
        entity.setVelocity(java.lang.Math.copySign(d * d * 0.4, d), java.lang.Math.copySign(e * e * 0.4, e), java.lang.Math.copySign(f * f * 0.4, f));
    }

    public static boolean shouldContinueMoving(TowerGolemEntity owner, TowerGolemArmPart lastArmPart, TowerGolemArmPart nextArmPart, boolean clockwise, int nextChainsAmount) {
        if (/*nextArmPart.getIsTurned() &&*/ (nextChainsAmount - 1) < 1) {
            return false;
        }

        /*TowerGolemArmPart arm;
        if (owner.backArmParts.contains(lastArmPart)) {
            return true;
        } else if (owner.backArmParts.contains(nextArmPart)) {
            List<TowerGolemArmPart> list = new ArrayList<>(owner.frontArmParts);
            list.remove(lastArmPart);

            arm = list.get(0);
        } else {
            return true;
        }

        float armNext = nextArmPart.armFullDegree;
        float armCurrent = arm.armFullDegree;

        if (armNext == armCurrent) {
            return false;
        }

        if (nextArmPart.distanceTo(arm) <= 1.5) {
            return true;
        }
        if (!clockwise) {
            if (armNext <= armCurrent) {
                return true;
            }
        } else if (armNext >= armCurrent) {
            return true;
        }

        return false;

         */
        return true;
    }

    public TowerGolemArmPart getArmByDegree(float degree) {
        return this.armPartMap.get(degree);
    }

    public boolean createPriorityList(TowerGolemArmPart firstArm, boolean forMoving) {
        List<TowerGolemArmPart> frontArmParts = this.frontArmParts;
        List<TowerGolemArmPart> backArmParts = this.backArmParts;
        if (forMoving) {
            if (!this.frontArmsPriorityList.isEmpty() && !this.backArmsPriorityList.isEmpty()) {
                System.out.println("Moving Arms priority list is NOT empty!");
                return false;
            }
            if (this.frontArmsPriorityList.isEmpty()) {
                this.frontArmsPriorityList.add(0, firstArm);

                TowerGolemArmPart secondArm = frontArmParts.get(frontArmParts.indexOf(firstArm) + 1 > frontArmParts.size() - 1 ? 0 : frontArmParts.indexOf(firstArm) + 1);
                this.frontArmsPriorityList.add(1, secondArm);
            }
            if (this.backArmsPriorityList.isEmpty()) {
                TowerGolemArmPart thirdArm;
                if (this.getArmByDegree(FRONT_RIGHT_ARM_DEGREE).equals(firstArm)) {
                    thirdArm = this.getArmByDegree(BACK_LEFT_ARM_DEGREE);
                } else {
                    thirdArm = this.getArmByDegree(BACK_RIGHT_ARM_DEGREE);
                }
                this.backArmsPriorityList.add(0, thirdArm);

                TowerGolemArmPart fourthArm = backArmParts.get(backArmParts.indexOf(thirdArm) + 1 > backArmParts.size() - 1 ? 0 : backArmParts.indexOf(thirdArm) + 1);
                this.backArmsPriorityList.add(1, fourthArm);
            }
        } else {
            if (this.turningArmsPriorityList.isEmpty()) {
                if (!frontArmParts.contains(firstArm)) {
                    throw new RuntimeException("First arm in turning priority list is NOT from frontArmParts!");
                }

                this.turningArmsPriorityList.add(0, firstArm);

                TowerGolemArmPart secondArm = frontArmParts.get(frontArmParts.indexOf(firstArm) + 1 > frontArmParts.size() - 1 ? 0 : frontArmParts.indexOf(firstArm) + 1);
                this.turningArmsPriorityList.add(1, secondArm);

                if (!backArmParts.isEmpty()) {
                    TowerGolemArmPart thirdArm;
                    if (this.getArmByDegree(FRONT_RIGHT_ARM_DEGREE).equals(firstArm)) {
                        thirdArm = this.getArmByDegree(BACK_RIGHT_ARM_DEGREE);
                    } else {
                        thirdArm = this.getArmByDegree(BACK_LEFT_ARM_DEGREE);
                    }
                    this.turningArmsPriorityList.add(2, thirdArm);

                    TowerGolemArmPart fourthArm = backArmParts.get(backArmParts.indexOf(thirdArm) + 1 > backArmParts.size() - 1 ? 0 : backArmParts.indexOf(thirdArm) + 1);
                    this.turningArmsPriorityList.add(3, fourthArm);
                }
            } else {
                System.out.println("Turning Arms priority list is NOT empty!");
                return false;
            }
        }

        return true;
    }

    public TowerGolemArmPart getNextPriorityArm(TowerGolemArmPart arm, boolean forMoving) {
        List<TowerGolemArmPart> targetedList;
        if (forMoving) {
            if (this.frontArmsPriorityList.contains(arm)) {
                targetedList = this.frontArmsPriorityList;
            } else if (this.backArmsPriorityList.contains(arm)) {
                targetedList = this.backArmsPriorityList;
            } else {
                throw new RuntimeException("forMoving is true, but NONE of priority lists contain this armPart!");
            }
        } else {
            if (this.turningArmsPriorityList.isEmpty()) {
                throw new RuntimeException("Next arm in turning priority list is null!");
            }
            targetedList = this.turningArmsPriorityList;
        }

        return targetedList.get(targetedList.indexOf(arm) + 1 > targetedList.size() - 1 ? 0 : targetedList.indexOf(arm) + 1);
        //return this.turningArmsPriorityList.get(this.turningArmsPriorityList.indexOf(arm) + 1 > this.turningArmsPriorityList.size() - 1 ? 0 : this.turningArmsPriorityList.indexOf(arm) + 1);
    }
    /*
    public TowerGolemArmPart getFirstPriorityArm(boolean forMoving, boolean shouldUseFrontArmsList/*Not important if first bl is false/) {
        if (forMoving) {
            if (shouldUseFrontArmsList) {
                return this.frontArmsPriorityList.get(0);
            } else {
                return this.backArmsPriorityList.get(0);
            }
        } else {
            return this.turningArmsPriorityList.get(0);
        }
    }*/

    public void cleanTurningArmsPriorityList() {
        this.turningArmsPriorityList.clear(); /* = new ArrayList<>(this.getBodyParts().size())*/;
    }

    public void cleanFrontArmsPriorityList() {
        this.frontArmsPriorityList.clear() /* = new ArrayList<>(2)*/;
    }

    public void cleanBackArmsPriorityList() {
        this.backArmsPriorityList.clear() /* = new ArrayList<>(2)*/;
    }

    public void cleanArmsNowMovingList(int whatToClear) {
        switch (whatToClear) {
            case 0 -> {
                for (int i = 1; i < 5; i++) {
                    System.out.println("    On i " + i + " arm is " + this.armsNowMoving.get(i));
                    this.armsNowMoving.set(i, Optional.empty());
                }
            }
            case 1 -> this.armsNowMoving.set(1, Optional.empty());
            case 2 -> this.armsNowMoving.set(2, Optional.empty());
            case 3 -> this.armsNowMoving.set(3, Optional.empty());
            case 4 -> this.armsNowMoving.set(4, Optional.empty());
            case 23 -> {
                this.armsNowMoving.set(2, Optional.empty());
                this.armsNowMoving.set(3, Optional.empty());
            }
        }
    }

    public List<TowerGolemArmPart> getArmParts() {
        List<TowerGolemArmPart> partList = new ArrayList<>();
        partList.add(0, this.armPartMap.get(FRONT_RIGHT_ARM_DEGREE));
        partList.add(1, this.armPartMap.get(FRONT_LEFT_ARM_DEGREE));
        partList.add(2, this.armPartMap.get(BACK_RIGHT_ARM_DEGREE));
        partList.add(3, this.armPartMap.get(BACK_LEFT_ARM_DEGREE));
        return partList;
    }

    public List<TowerGolemArmPart> getBodyParts() {
        return new ArrayList<>(this.armPartMap.values());
    }

    private TowerGolemArmPart spawnArm(TowerGolemArmPart arm, Vec3d ownerPos, Vec3d spawnPos, List<TowerGolemArmPart> armList, Map<Float, TowerGolemArmPart> armMap, float degree) {
        arm.setPosition(spawnPos);
        Vec3d posOffset = new Vec3d(spawnPos.x - ownerPos.x, spawnPos.y - ownerPos.y, spawnPos.z - ownerPos.z);
        arm.armPosOffset = Optional.of(BlockPos.ofFloored(posOffset));
        arm.setPartOwnerById(this.getUuid());
        arm.setHoldingEntityId(this.getId());

        arm.setSpawnedDegree(degree);
        arm.armSpawnedDegree.put(false, degree);
        arm.armSpawnedDegree.put(true, 360 - degree);
        arm.setPrevDegree(degree);
        arm.armFullDegree = degree;

        armList.add(arm);
        armMap.put(degree, arm);
        this.getWorld().spawnEntity(arm);

        return arm;
    }

    public static void armStepStop(TowerGolemEntity owner, int stepState) {
        if (stepState == 1) {
            for (TowerGolemArmPart arm : owner.armPartMap.values()) {
                arm.armGoalSelector.clear(goal -> goal instanceof TowerGolemStepGoal);
                //arm.armGoalSelector.clear(goal -> goal instanceof TowerGolemArmPart.TowerGolemStepGoal);
                arm.setIsTurned(false);
                arm.setNoDrag(false);
            }

            owner.cleanTurningArmsPriorityList();
            owner.cleanArmsNowMovingList(1);
            //owner.armNowTurning = null;
            owner.setIsTurning(false);
        } else if (stepState == 2) {
            for (TowerGolemArmPart arm : owner.frontArmParts) {
                arm.armGoalSelector.clear(goal -> goal instanceof TowerGolemStepGoal);
                arm.setIsTurned(false);
                arm.setNoDrag(false);
            }

            owner.cleanFrontArmsPriorityList();
            owner.cleanArmsNowMovingList(2);
            owner.setIsTurning(false);
        } else if (stepState == 3) {
            for (TowerGolemArmPart arm : owner.backArmParts) {
                arm.armGoalSelector.clear(goal -> goal instanceof TowerGolemStepGoal);
                arm.setIsTurned(false);
                arm.setNoDrag(false);
            }

            owner.cleanBackArmsPriorityList();
            owner.cleanArmsNowMovingList(3);
            owner.setIsTurning(false);
        } else if (stepState == 0) {
            for (TowerGolemArmPart arm : owner.armPartMap.values()) {
                arm.armGoalSelector.clear(goal -> goal instanceof TowerGolemStepGoal);
                arm.setIsTurned(false);
                arm.setNoDrag(false);
            }

            owner.cleanTurningArmsPriorityList();
            owner.cleanFrontArmsPriorityList();
            owner.cleanBackArmsPriorityList();
            owner.cleanArmsNowMovingList(0);
            owner.setIsTurning(false);
        }

    }

    private static Vec3d getRandomPosForLegs(LivingEntity target, Random random, float turn) {
        float f = turn + (float)random.nextGaussian() * 10.0f;
        Vec3d vec3d = Vec3d.fromPolar(0.0f, turn).multiply(5);
        return target.getPos().add(vec3d);
    }

    @Nullable
    public static BlockPos raycastPos(LivingEntity golem, Vec3d pos) {
        //System.out.println("Начался рейкаст на позиции " + pos);
        RaycastContext raycastContext = new RaycastContext(pos, pos.offset(Direction.UP, 1.0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, golem);
        BlockHitResult hitResult = golem.getWorld().raycast(raycastContext);
        if (((HitResult)hitResult).getType() == HitResult.Type.BLOCK) {
            //System.out.println("рейкаст ВВЕРХ нашел блок -> позиция - " + hitResult.getPos() + "; позиция блока сверху - " + BlockPos.ofFloored(hitResult.getPos()).up());
            return BlockPos.ofFloored(hitResult.getPos()).up();
        }
        RaycastContext raycastContext2 = new RaycastContext(pos, pos.offset(Direction.DOWN, 2.0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, golem);
        BlockHitResult hitResult2 = golem.getWorld().raycast(raycastContext2);
        if (((HitResult)hitResult2).getType() == HitResult.Type.BLOCK) {
            //System.out.println("рейкаст ВНИЗ нашел блок -> позиция - " + hitResult2.getPos() + "; позиция блока сверху - " + BlockPos.ofFloored(hitResult2.getPos()).up());
            return BlockPos.ofFloored(hitResult2.getPos()).up();
        }

        //return golem.getBlockPos().add((int) Math.round(pos.getX()), (int) Math.round(pos.getY()), (int) Math.round(pos.getZ()));
        //System.out.println("рейкаст НЕ нашел блок -> позиция - " + pos + "; позиция блока (не сверху... OwO) - " + BlockPos.ofFloored(pos));
        return BlockPos.ofFloored(pos);
    }

    public int getBackArmsCooldownTime() {
        return this.backArmsCooldownTime;
    }

    public void setBackArmsCooldownTime(int newTime) {
        this.backArmsCooldownTime = newTime;
    }

    public boolean getIsTurning() {
        return this.dataTracker.get(IS_TURNING);
    }

    public void setIsTurning(boolean bl) {
        this.dataTracker.set(IS_TURNING, bl);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        NbtCompound frontRightArmNbt = new NbtCompound();
        frontRightArmNbt.put("pos", this.toNbtList(this.armPartMap.get(FRONT_RIGHT_ARM_DEGREE).getPos().getX(), this.armPartMap.get(FRONT_RIGHT_ARM_DEGREE).getPos().getY(), this.armPartMap.get(FRONT_RIGHT_ARM_DEGREE).getPos().getZ()));
        frontRightArmNbt.putFloat("posInDegree", this.armPartMap.get(FRONT_RIGHT_ARM_DEGREE).getPrevDegree());
        frontRightArmNbt.putFloat("fullDegree", this.armPartMap.get(FRONT_RIGHT_ARM_DEGREE).armFullDegree);
        //frontRightArmNbt.put("arm", (NbtElement) this.armPartMap.get(FRONT_RIGHT_ARM_DEGREE));
        NbtCompound backRightArmNbt = new NbtCompound();
        backRightArmNbt.put("pos", this.toNbtList(this.armPartMap.get(BACK_RIGHT_ARM_DEGREE).getPos().getX(), this.armPartMap.get(BACK_RIGHT_ARM_DEGREE).getPos().getY(), this.armPartMap.get(BACK_RIGHT_ARM_DEGREE).getPos().getZ()));
        backRightArmNbt.putFloat("posInDegree", this.armPartMap.get(BACK_RIGHT_ARM_DEGREE).getPrevDegree());
        backRightArmNbt.putFloat("fullDegree", this.armPartMap.get(BACK_RIGHT_ARM_DEGREE).armFullDegree);
        //backRightArmNbt.put("arm", (NbtElement) this.armPartMap.get(BACK_RIGHT_ARM_DEGREE));
        NbtCompound frontLeftArmNbt = new NbtCompound();
        frontLeftArmNbt.put("pos", this.toNbtList(this.armPartMap.get(FRONT_LEFT_ARM_DEGREE).getPos().getX(), this.armPartMap.get(FRONT_LEFT_ARM_DEGREE).getPos().getY(), this.armPartMap.get(FRONT_LEFT_ARM_DEGREE).getPos().getZ()));
        frontLeftArmNbt.putFloat("posInDegree", this.armPartMap.get(FRONT_LEFT_ARM_DEGREE).getPrevDegree());
        frontLeftArmNbt.putFloat("fullDegree", this.armPartMap.get(FRONT_LEFT_ARM_DEGREE).armFullDegree);
        //frontLeftArmNbt.put("arm", (NbtElement) this.armPartMap.get(FRONT_LEFT_ARM_DEGREE));
        NbtCompound backLeftArmNbt = new NbtCompound();
        backLeftArmNbt.put("pos", this.toNbtList(this.armPartMap.get(BACK_LEFT_ARM_DEGREE).getPos().getX(), this.armPartMap.get(BACK_LEFT_ARM_DEGREE).getPos().getY(), this.armPartMap.get(BACK_LEFT_ARM_DEGREE).getPos().getZ()));
        backLeftArmNbt.putFloat("posInDegree", this.armPartMap.get(BACK_LEFT_ARM_DEGREE).getPrevDegree());
        backLeftArmNbt.putFloat("fullDegree", this.armPartMap.get(BACK_LEFT_ARM_DEGREE).armFullDegree);
        //backLeftArmNbt.put("arm", (NbtElement) this.armPartMap.get(BACK_LEFT_ARM_DEGREE));


        nbt.put("frontRightArmNbt", frontRightArmNbt);
        nbt.put("backRightArmNbt", backRightArmNbt);
        nbt.put("frontLeftArmNbt", frontLeftArmNbt);
        nbt.put("backLeftArmNbt", backLeftArmNbt);

        nbt.putFloat("golemHeadYaw", this.getHeadYaw());
        nbt.putFloat("golemBodyYaw", this.getBodyYaw());
        nbt.putFloat("golemYaw", this.getYaw());


        if (this.armsNowMoving.get(1).isPresent()) {
            nbt.putFloat("armTurning", this.armsNowMoving.get(1).get().armSpawnedDegree.get(false));
        } else if (nbt.contains("armTurning")) {
            nbt.remove("armTurning");
        }
        if (this.armsNowMoving.get(2).isPresent()) {
            nbt.putFloat("frontArmMoving", this.armsNowMoving.get(2).get().armSpawnedDegree.get(false));
        } else if (nbt.contains("frontArmMoving")) {
            nbt.remove("frontArmMoving");
        }
        if (this.armsNowMoving.get(3).isPresent()) {
            nbt.putFloat("backArmMoving", this.armsNowMoving.get(3).get().armSpawnedDegree.get(false));
        } else if (nbt.contains("backArmMoving")) {
            nbt.remove("backArmMoving");
        }
        if (this.armsNowMoving.get(4).isPresent()) {
            nbt.putFloat("armAttacking", this.armsNowMoving.get(4).get().armSpawnedDegree.get(false));
        } else if (nbt.contains("armAttacking")) {
            nbt.remove("armAttacking");
        }

        System.out.println("Spawned is " + this.isInit);
        nbt.putBoolean("spawned", this.isInit);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("spawned")) {
            this.isInit = nbt.getBoolean("spawned");
        }

        System.out.println("Reading started; Init " + this.isInit);

        if (!this.isInit) {

            NbtList pos;
            TowerGolemArmPart armEntity;
            NbtCompound arm = ((NbtCompound) nbt.get("frontRightArmNbt"));

            if (arm != null) {
                pos = arm.getList("pos", 6);
                Vec3d position = new Vec3d(MathHelper.clamp(pos.getDouble(0), -3.0000512E7, 3.0000512E7), MathHelper.clamp(pos.getDouble(1), -2.0E7, 2.0E7), MathHelper.clamp(pos.getDouble(2), -3.0000512E7, 3.0000512E7));
                armEntity = this.spawnArm(new TowerGolemArmPart(this, "arm", 1.0F, 1.0F), this.getPos(), position, this.frontArmParts, this.armPartMap, FRONT_RIGHT_ARM_DEGREE);

                System.out.println("(nbt) Reading armPartMap ( " + this.armPartMap + " )");
                armEntity.setPrevDegree(arm.getFloat("posInDegree"));
                armEntity.armFullDegree = (arm.getFloat("fullDegree"));

            }

            arm = ((NbtCompound) nbt.get("backRightArmNbt"));
            if (arm != null) {
                pos = arm.getList("pos", 6);
                Vec3d position = new Vec3d(MathHelper.clamp(pos.getDouble(0), -3.0000512E7, 3.0000512E7), MathHelper.clamp(pos.getDouble(1), -2.0E7, 2.0E7), MathHelper.clamp(pos.getDouble(2), -3.0000512E7, 3.0000512E7));
                armEntity = this.spawnArm(new TowerGolemArmPart(this, "arm", 1.0F, 1.0F), this.getPos(), position, this.backArmParts, this.armPartMap, BACK_RIGHT_ARM_DEGREE);

                System.out.println("(nbt) Reading armPartMap ( " + this.armPartMap + " )");
                armEntity.setPrevDegree(arm.getFloat("posInDegree"));
                armEntity.armFullDegree = (arm.getFloat("fullDegree"));

            }

            arm = ((NbtCompound) nbt.get("frontLeftArmNbt"));
            if (arm != null) {
                pos = arm.getList("pos", 6);
                Vec3d position = new Vec3d(MathHelper.clamp(pos.getDouble(0), -3.0000512E7, 3.0000512E7), MathHelper.clamp(pos.getDouble(1), -2.0E7, 2.0E7), MathHelper.clamp(pos.getDouble(2), -3.0000512E7, 3.0000512E7));
                armEntity = this.spawnArm(new TowerGolemArmPart(this, "arm", 1.0F, 1.0F), this.getPos(), position, this.frontArmParts, this.armPartMap, FRONT_LEFT_ARM_DEGREE);

                System.out.println("(nbt) Reading armPartMap ( " + this.armPartMap + " )");
                armEntity.setPrevDegree(arm.getFloat("posInDegree"));
                armEntity.armFullDegree = (arm.getFloat("fullDegree"));

            }

            arm = ((NbtCompound) nbt.get("backLeftArmNbt"));
            if (arm != null) {
                pos = arm.getList("pos", 6);
                Vec3d position = new Vec3d(MathHelper.clamp(pos.getDouble(0), -3.0000512E7, 3.0000512E7), MathHelper.clamp(pos.getDouble(1), -2.0E7, 2.0E7), MathHelper.clamp(pos.getDouble(2), -3.0000512E7, 3.0000512E7));
                armEntity = this.spawnArm(new TowerGolemArmPart(this, "arm", 1.0F, 1.0F), this.getPos(), position, this.backArmParts, this.armPartMap, BACK_LEFT_ARM_DEGREE);

                System.out.println("(nbt) Reading armPartMap ( " + this.armPartMap + " )");
                armEntity.setPrevDegree(arm.getFloat("posInDegree"));
                armEntity.armFullDegree = (arm.getFloat("fullDegree"));

            }
        }

        this.setYaw(nbt.getFloat("golemYaw"));
        this.setHeadYaw(nbt.getFloat("golemHeadYaw"));
        this.setBodyYaw(nbt.getFloat("golemBodyYaw"));


        System.out.println("armsNowMoving List before setting up is " + this.armsNowMoving + ", size " + this.armsNowMoving.size());
        this.armsNowMoving.add(Optional.empty());
        if (nbt.contains("armTurning")) {
            this.armsNowMoving.add(1, Optional.of(this.getArmByDegree(nbt.getFloat("armTurning"))));
        } else {
            this.armsNowMoving.add(Optional.empty());
        }
        if (nbt.contains("frontArmMoving")) {
            this.armsNowMoving.add(2, Optional.of(this.getArmByDegree(nbt.getFloat("frontArmMoving"))));
        } else {
            this.armsNowMoving.add(Optional.empty());
        }
        if (nbt.contains("backArmMoving")) {
            this.armsNowMoving.add(3, Optional.of(this.getArmByDegree(nbt.getFloat("backArmMoving"))));
        } else {
            this.armsNowMoving.add(Optional.empty());
        }
        if (nbt.contains("armAttacking")) {
            this.armsNowMoving.add(4, Optional.of(this.getArmByDegree(nbt.getFloat("armAttacking"))));
        } else {
            this.armsNowMoving.add(Optional.empty());
        }
        System.out.println("armsNowMoving List AFTER setting up is " + this.armsNowMoving + ", size " + this.armsNowMoving.size());
        //this.isArmNowTurning = nbt.getBoolean("armTurning");

        super.readCustomDataFromNbt(nbt);
    }

    public boolean damagePart(/*TowerGolemPart part, */DamageSource source, float amount) {
            if (amount < 0.01F) {
                return false;
            } else {
                if (source.getAttacker() instanceof PlayerEntity) {
                    this.parentDamage(source, amount);
                }

                return true;
            }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return !this.getWorld().isClient ? this.damagePart(/*this.body, */source, amount) : false;
    }

    protected boolean parentDamage(DamageSource source, float amount) {
        return super.damage(source, amount);
    }

    @Override
    public boolean isPushable() {
        return false;
    }
    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        List<TowerGolemArmPart> towerGolemArmParts = this.getBodyParts();

        for (int i = 0; i < towerGolemArmParts.size(); ++i) {
            towerGolemArmParts.get(i).setId(i + packet.getId());
        }
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        for (TowerGolemPart part : this.armPartMap.values()) {
            //System.out.println("Part removed by onDeath");
            part.remove(RemovalReason.KILLED);
        }
    }

    public static DefaultAttributeContainer.Builder createTowerGolemAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40)
                .add(EntityAttributes.GENERIC_ARMOR, 10)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0);
    }

}


