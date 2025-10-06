package net.eps.lonsbattletowers.entity.custom.golem;

import net.eps.lonsbattletowers.entity.ModEntities;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;

public class TowerGolemArmPart extends TowerGolemPart {
    private static final TrackedData<Float> SPAWNED_DEGREE = DataTracker.registerData(TowerGolemArmPart.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> PREV_DEGREE = DataTracker.registerData(TowerGolemArmPart.class, TrackedDataHandlerRegistry.FLOAT);

    private static final TrackedData<Boolean> IS_TURNED = DataTracker.registerData(TowerGolemArmPart.class, TrackedDataHandlerRegistry.BOOLEAN);

    public final GoalSelector armGoalSelector;
    public Optional<BlockPos> armPosOffset;

    public final Map<Boolean, Float> armSpawnedDegree;
    public float armFullDegree = 0;

    private Vec3d stepFinishPos;
    private Vec3d partsCenterPos;
    private boolean clockwise;

    protected boolean isArmTurning;


    public TowerGolemArmPart(EntityType<? extends TowerGolemArmPart> type, World world) {
        super(type, world);
        this.armGoalSelector = this.goalSelector;
        this.armSpawnedDegree = new HashMap<>();
    }

    public TowerGolemArmPart(TowerGolemEntity owner, String name, float width, float height) {
        super(ModEntities.TOWER_GOLEM_ARM_PART, owner, name, EntityDimensions.changing(width, height), 1.0f);
        this.armGoalSelector = this.goalSelector;
        this.armSpawnedDegree = new HashMap<>();
        //this.partDimensions = EntityDimensions.fixed(width, height);
        //this.calculateDimensions();
        //this.owner = owner;
        //this.name = name;
    }

    @Override
    protected void initGoals() {

        super.initGoals();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SPAWNED_DEGREE, -1.0f);
        this.dataTracker.startTracking(PREV_DEGREE, 0.0f);

        this.dataTracker.startTracking(IS_TURNED, false);
    }

    public float getSpawnedDegree() {
        return this.dataTracker.get(SPAWNED_DEGREE);
    } // TEMP
    public float getPrevDegree() {
        return this.dataTracker.get(PREV_DEGREE);
    }
    public boolean getIsTurned() {
        return this.dataTracker.get(IS_TURNED);
    }
    public Vec3d getStepFinishPos() {
        return this.stepFinishPos;
    }
    public Vec3d getPartsCenterPos() {
        return this.partsCenterPos;
    }
    public boolean getClockwise() {
        return this.clockwise;
    }

    public void setSpawnedDegree(float degree) {
        this.dataTracker.set(SPAWNED_DEGREE, degree);
    } // TEMP
    public void setPrevDegree(float degree) {
        this.dataTracker.set(PREV_DEGREE, degree);
    }
    public void setIsTurned(boolean isTurning) {
        this.dataTracker.set(IS_TURNED, isTurning);
    }
    public void setStepFinishPos(Vec3d newPos) {
        this.stepFinishPos = newPos;
    }
    public void setPartsCenterPos(Vec3d newPartsCenterPos) {
        this.partsCenterPos = newPartsCenterPos;
    }
    public void setClockwise(boolean clockwise) {
        this.clockwise = clockwise;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }


    /*
    public static class TowerGolemStepGoal extends Goal {
        private final TowerGolemEntity owner;
        private final TowerGolemArmPart part;
        private final TowerGolemArmPart nextPart;
        private final BlockPos prevPos;

        public double armsStepTime;

        public float mainDegree;
        public float partDegree;
        public int delta1;
        public int delta2;

        public boolean clockwiseConstant;
        public boolean duringMoving;
        public boolean foundObstacle;

        public TowerGolemStepGoal(TowerGolemEntity golem, TowerGolemArmPart part, TowerGolemArmPart nextPart, BlockPos pos, BlockPos prevPos, int delta1, int delta2, float partDegree, float mainDegree, boolean clockwise) {
            this(golem, part, nextPart, pos, prevPos, delta1, delta2, partDegree, mainDegree, clockwise, clockwise, false);
        }

        public TowerGolemStepGoal(TowerGolemEntity golem, TowerGolemArmPart part, TowerGolemArmPart nextPart, BlockPos pos, BlockPos prevPos, int delta1, int delta2, float partDegree, float mainDegree, boolean clockwise, boolean clockwiseConstant, boolean duringMoving) {
            this.owner = golem;
            this.part = part;
            this.nextPart = nextPart;

            this.part.stepFinishPos = pos.toCenterPos();
            this.prevPos = prevPos;

            this.mainDegree = mainDegree;
            this.partDegree = partDegree;
            this.delta1 = delta1;
            this.delta2 = delta2;

            this.part.clockwise = clockwise;
            this.clockwiseConstant = clockwiseConstant;
            this.duringMoving = duringMoving;
            this.foundObstacle = false;

            this.armsStepTime = 0;
        }

        @Override
        public boolean canStart() {
            //System.out.println("(! 0 !) - " + (this.owner == null));
            if (this.owner == null) {
                return false;
            }
            //System.out.println("(! 1 !) - " + (this.part == null));
            if (this.part == null) {
                return false;
            }
            //System.out.println("(! 2 !) - " + (this.part.getIsTurned()));
            if (this.part.getIsTurned()) {
                return false;
            }
            //System.out.println("(! 3 !) - " + (this.part.jumpPos == null));
            if (this.part.stepFinishPos == null) {
                return false;
            }
            //System.out.println("(! 4 !) - " + (this.prevPos == null));
            if (this.prevPos == null) {
                return false;
            }
            //System.out.println("(! 5 !) - " + (!this.owner.getIsTurning()));
            if (!this.owner.getIsTurning()) {
                return false;
            }

            return true;
        }

        @Override
        public void start() {
            this.armsStepTime = 0;
        }

        @Override
        public boolean shouldContinue() {
            boolean bl = this.owner.armNowTurning != null &&
                    this.owner.getIsTurning() &&
                    this.owner.isAlive() && this.part.isAlive();

            return bl && this.armsStepTime < 1;
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (this.armsStepTime < 1 && this.owner.armNowTurning != null) {
                Vec3d newPos = GolemMath.calculateArmPosition(
                        Vec3d.ofBottomCenter(this.prevPos).add(-0.5, 0, -0.5),
                        this.part.stepFinishPos,
                        this.owner.getPos(),
                        this.armsStepTime,
                        !this.part.clockwise);

                //Box box = new Box(newPos.x - 0.5, newPos.y - 0.5, newPos.z - 0.5, newPos.x + 0.5, newPos.y + 0.5, newPos.z + 0.5);

                //Vec3d minBoundingPos = new Vec3d(newPos.x + this.part.minXdiff, newPos.y + this.part.minYdiff, newPos.z + this.part.minZdiff);
                //Vec3d maxBoundingPos = new Vec3d(newPos.x + this.part.maxXdiff, newPos.y + this.part.maxYdiff, newPos.z + this.part.maxZdiff);
                /*
                Vec3d leftFrontDownBoundingPos = new Vec3d(newPos.x + 0.5, newPos.y, newPos.z - 0.5);
                Vec3d leftFrontUpBoundingPos =   new Vec3d(newPos.x + 0.5, newPos.y + 1, newPos.z - 0.5);
                Vec3d leftBackDownBoundingPos =  new Vec3d(newPos.x - 0.5, newPos.y, newPos.z - 0.5);
                Vec3d leftBackUpBoundingPos =    new Vec3d(newPos.x - 0.5, newPos.y + 1, newPos.z - 0.5);

                Vec3d rightBackUpBoundingPos =    new Vec3d(newPos.x - 0.5, newPos.y + 1, newPos.z + 0.5);
                Vec3d rightBackDownBoundingPos =  new Vec3d(newPos.x - 0.5, newPos.y, newPos.z + 0.5);
                Vec3d rightFrontUpBoundingPos =   new Vec3d(newPos.x + 0.5, newPos.y + 1, newPos.z + 0.5);
                Vec3d rightFrontDownBoundingPos = new Vec3d(newPos.x + 0.5, newPos.y, newPos.z + 0.5);

                if (solidCheck(leftFrontDownBoundingPos) ||
                        solidCheck(leftFrontUpBoundingPos) ||
                        solidCheck(leftBackDownBoundingPos) ||
                        solidCheck(leftBackUpBoundingPos) ||
                        solidCheck(rightBackUpBoundingPos) ||
                        solidCheck(rightBackDownBoundingPos) ||
                        solidCheck(rightFrontUpBoundingPos) ||
                        solidCheck(rightFrontDownBoundingPos)) {
                    Vec3d adjustedPos = new Vec3d(0, 0, 0);
                    BlockPos xMinCheck = BlockPos.ofFloored(new Vec3d(newPos.x - 0.5, newPos.y, newPos.z));
                    BlockPos xMaxCheck = BlockPos.ofFloored(new Vec3d(newPos.x + 0.5, newPos.y, newPos.z));
                    BlockPos yMinCheck = BlockPos.ofFloored(new Vec3d(newPos.x, newPos.y, newPos.z));
                    BlockPos yMaxCheck = BlockPos.ofFloored(new Vec3d(newPos.x, newPos.y + 1, newPos.z));
                    BlockPos zMinCheck = BlockPos.ofFloored(new Vec3d(newPos.x, newPos.y, newPos.z - 0.5));
                    BlockPos zMaxCheck = BlockPos.ofFloored(new Vec3d(newPos.x, newPos.y, newPos.z + 0.5));

                    if (this.part.getWorld().getBlockState(xMinCheck).isSolidBlock(this.part.getWorld(), xMinCheck) ||
                        this.part.getWorld().getBlockState(xMaxCheck).isSolidBlock(this.part.getWorld(), xMaxCheck)) {
                        adjustedPos = adjustedPos.add(this.part.getPos().x, 0, 0);
                    } else {
                        adjustedPos = adjustedPos.add(newPos.x, 0, 0);
                    }
                    if (this.part.getWorld().getBlockState(yMinCheck).isSolidBlock(this.part.getWorld(), yMinCheck) ||
                        this.part.getWorld().getBlockState(yMaxCheck).isSolidBlock(this.part.getWorld(), yMaxCheck)) {
                        adjustedPos = adjustedPos.add(0, this.part.getPos().y, 0);
                    } else {
                        adjustedPos = adjustedPos.add(0, newPos.y, 0);
                    }
                    if (this.part.getWorld().getBlockState(zMinCheck).isSolidBlock(this.part.getWorld(), zMinCheck) ||
                        this.part.getWorld().getBlockState(zMaxCheck).isSolidBlock(this.part.getWorld(), zMaxCheck)) {
                        adjustedPos = adjustedPos.add(0, 0, this.part.getPos().z);
                    } else {
                        adjustedPos = adjustedPos.add(0, 0, newPos.z);
                    }

                    System.out.println("adjusted pos is now " + adjustedPos);
                    this.part.setPosition(adjustedPos);
                } else {
                    this.part.setPosition(newPos);
                }/
                //Vec3d minBoundingPos = new Vec3d(newPos.x + 0.5, newPos.y, newPos.z - 0.5);
                //Vec3d maxBoundingPos = new Vec3d(newPos.x - 0.5, newPos.y + 1, newPos.z + 0.5);
                Vec3d leftFrontDownBoundingPos = new Vec3d(newPos.x + 0.5, newPos.y, newPos.z - 0.5);
                Vec3d leftFrontUpBoundingPos =   new Vec3d(newPos.x + 0.5, newPos.y + 1, newPos.z - 0.5);
                Vec3d leftBackDownBoundingPos =  new Vec3d(newPos.x - 0.5, newPos.y, newPos.z - 0.5);
                Vec3d leftBackUpBoundingPos =    new Vec3d(newPos.x - 0.5, newPos.y + 1, newPos.z - 0.5);

                Vec3d rightBackUpBoundingPos =    new Vec3d(newPos.x - 0.5, newPos.y + 1, newPos.z + 0.5);
                Vec3d rightBackDownBoundingPos =  new Vec3d(newPos.x - 0.5, newPos.y, newPos.z + 0.5);
                Vec3d rightFrontUpBoundingPos =   new Vec3d(newPos.x + 0.5, newPos.y + 1, newPos.z + 0.5);
                Vec3d rightFrontDownBoundingPos = new Vec3d(newPos.x + 0.5, newPos.y, newPos.z + 0.5);

                if (solidCheck(leftFrontDownBoundingPos) ||
                        solidCheck(leftFrontUpBoundingPos) ||
                        solidCheck(leftBackDownBoundingPos) ||
                        solidCheck(leftBackUpBoundingPos) ||
                        solidCheck(rightBackUpBoundingPos) ||
                        solidCheck(rightBackDownBoundingPos) ||
                        solidCheck(rightFrontUpBoundingPos) ||
                        solidCheck(rightFrontDownBoundingPos)) {
                    this.foundObstacle = true;
                    this.part.setPosition(solidCheckPos(newPos));
                } else {
                    this.foundObstacle = false;
                    this.part.setPosition(newPos);
                }
                /*else if (solidCheck(maxBoundingPos)) {
                    System.out.println("found obstacle on max pos : " + maxBoundingPos);
                    Vec3d adjustedPos = new Vec3d(0, 0, 0);
                    Vec3d xMinCheck = new Vec3d(newPos.x - 0.5, newPos.y, newPos.z);
                    Vec3d xMaxCheck = new Vec3d(newPos.x + 0.5, newPos.y, newPos.z);
                    Vec3d yMinCheck = new Vec3d(newPos.x, newPos.y, newPos.z);
                    Vec3d yMaxCheck = new Vec3d(newPos.x, newPos.y + 1, newPos.z);
                    Vec3d zMinCheck = new Vec3d(newPos.x, newPos.y, newPos.z - 0.5);
                    Vec3d zMaxCheck = new Vec3d(newPos.x, newPos.y, newPos.z + 0.5);

                    if (solidCheck(xMinCheck) || solidCheck(xMaxCheck)) {
                        adjustedPos = adjustedPos.add(this.part.getPos().x, 0, 0);
                    } else {
                        adjustedPos = adjustedPos.add(newPos.x, 0, 0);
                    }

                    if (solidCheck(yMinCheck) || solidCheck(yMaxCheck)) {
                        adjustedPos = adjustedPos.add(0, this.part.getPos().y, 0);
                    } else {
                        adjustedPos = adjustedPos.add(0, newPos.y, 0);
                    }

                    if (solidCheck(zMinCheck) || solidCheck(zMaxCheck)) {
                        adjustedPos = adjustedPos.add(0, 0, this.part.getPos().z);
                    } else {
                        adjustedPos = adjustedPos.add(0, 0, newPos.z);
                    }

                    this.part.setPosition(adjustedPos);
                    System.out.println("adjusted pos is now " + adjustedPos);

                } else {
                    this.part.setPosition(newPos);
                }

                 */
                /*
                if (this.part.getWorld().getBlockState(BlockPos.ofFloored(newPos)).isSolidBlock(this.part.getWorld(), BlockPos.ofFloored(newPos))) {
                    //System.out.println("found obstacle on " + newPos);
                    Vec3d adjustedPos = new Vec3d(0, 0, 0);
                    BlockPos xCheck = BlockPos.ofFloored(new Vec3d(newPos.x, this.part.getPos().y, this.part.getPos().z));
                    BlockPos yCheck = BlockPos.ofFloored(new Vec3d(this.part.getPos().x, newPos.y, this.part.getPos().z));
                    BlockPos zCheck = BlockPos.ofFloored(new Vec3d(this.part.getPos().x, this.part.getPos().y, newPos.z));

                    if (this.part.getWorld().getBlockState(yCheck).isSolidBlock(this.part.getWorld(), yCheck)) {
                        adjustedPos = adjustedPos.add(0, this.part.getPos().y, 0);
                        //System.out.println("yCheck failed, adjustedPos is " + adjustedPos + ", and partposY is " + this.part.getPos().y);
                    } else {
                        adjustedPos = adjustedPos.add(0, newPos.y, 0);
                    }
                    if (this.part.getWorld().getBlockState(xCheck).isSolidBlock(this.part.getWorld(), xCheck)) {
                        adjustedPos = adjustedPos.add(this.part.getPos().x, 0, 0);
                        //System.out.println("xCheck failed, adjustedPos is " + adjustedPos + ", and partposX is " + this.part.getPos().x);
                    } else {
                        adjustedPos = adjustedPos.add(newPos.x, 0, 0);
                    }
                    if (this.part.getWorld().getBlockState(zCheck).isSolidBlock(this.part.getWorld(), zCheck)) {
                        adjustedPos = adjustedPos.add(0, 0, this.part.getPos().z);
                        //System.out.println("zCheck failed, adjustedPos is " + adjustedPos + ", and partposZ is " + this.part.getPos().z);
                    } else {
                        adjustedPos = adjustedPos.add(0, 0, newPos.z);
                    }

                    this.part.setPosition(adjustedPos);
                } else {
                    this.part.setPosition(newPos);
                }

                 /



                this.armsStepTime = Math.min(this.armsStepTime + /*0.075/0.19 * this.owner.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED), 1);

            }
        }

        public boolean solidCheck(Vec3d solidPos) {
            return this.part.getWorld().getBlockState(BlockPos.ofFloored(solidPos)).isSolidBlock(this.part.getWorld(), BlockPos.ofFloored(solidPos));
        }

        public Vec3d solidCheckPos(Vec3d newPos) {
            Vec3d adjustedPos = new Vec3d(0, 0, 0);
            Vec3d xMinCheck = new Vec3d(newPos.x - 0.5, newPos.y, newPos.z);
            Vec3d xMaxCheck = new Vec3d(newPos.x + 0.5, newPos.y, newPos.z);
            Vec3d yMinCheck = new Vec3d(newPos.x, newPos.y, newPos.z);
            Vec3d yMaxCheck = new Vec3d(newPos.x, newPos.y + 1, newPos.z);
            Vec3d zMinCheck = new Vec3d(newPos.x, newPos.y, newPos.z - 0.5);
            Vec3d zMaxCheck = new Vec3d(newPos.x, newPos.y, newPos.z + 0.5);

            if (solidCheck(xMinCheck) || solidCheck(xMaxCheck)) {
                adjustedPos = adjustedPos.add(this.part.getPos().x, 0, 0);
            } else {
                adjustedPos = adjustedPos.add(newPos.x, 0, 0);
            }

            if (solidCheck(yMinCheck) || solidCheck(yMaxCheck)) {
                adjustedPos = adjustedPos.add(0, this.part.getPos().y, 0);
            } else {
                adjustedPos = adjustedPos.add(0, newPos.y, 0);
            }

            if (solidCheck(zMinCheck) || solidCheck(zMaxCheck)) {
                adjustedPos = adjustedPos.add(0, 0, this.part.getPos().z);
            } else {
                adjustedPos = adjustedPos.add(0, 0, newPos.z);
            }

            return adjustedPos;
        }

        @Override
        public void stop() {
            if (this.foundObstacle) {
                float newArmDegree = (float) ((Math.toDegrees(Math.atan2(this.part.getPos().x - this.owner.getPos().x, this.part.getPos().z - this.owner.getPos().z)) % 360) * -1);

                this.part.armFullDegree += (newArmDegree + delta1) - (this.part.getPrevDegree() + delta2);
                this.part.setPrevDegree(newArmDegree);
            } else {
                this.part.armFullDegree += (this.partDegree + delta1) - (this.part.getPrevDegree() + delta2);
                this.part.setPrevDegree(this.partDegree);
            }


            this.part.setIsTurned(true);
            this.owner.armNowTurning = null;


            if (TowerGolemEntity.shouldContinueMoving(this.owner, this.part, this.nextPart, !this.clockwiseConstant, 2)) {
                TowerGolemArmPart newNextPart;
                float armDegree;
                int t = 0;
                int n = 0;

                if (this.part.clockwise) {
                    newNextPart = this.owner.frontArmParts.get(this.owner.frontArmParts.indexOf(this.nextPart) + 1 > this.owner.frontArmParts.size() - 1 ? 0 : this.owner.frontArmParts.indexOf(this.nextPart) + 1);
                    if (newNextPart.getIsTurned() && false/*Новое/) {
                        boolean bl = this.owner.backArmParts.get(0).getIsTurned();
                        boolean bl1 = this.owner.backArmParts.get(0) == this.nextPart;

                        newNextPart = bl || bl1 ? this.owner.backArmParts.get(1) : this.owner.backArmParts.get(0);
                    }
                    armDegree = GolemMath.subtractDegree(this.mainDegree, this.nextPart.armSpawnedDegree.get(true));
                    if (armDegree < this.nextPart.getPrevDegree()) {
                        n = 360;
                    }

                } else {
                    newNextPart = this.owner.frontArmParts.get(this.owner.frontArmParts.indexOf(this.nextPart) - 1 < 0 ? this.owner.frontArmParts.size() - 1 : this.owner.frontArmParts.indexOf(this.nextPart) - 1);
                    if (newNextPart.getIsTurned() && false/*Новое/) {
                        boolean bl = this.owner.backArmParts.get(1).getIsTurned();
                        boolean bl1 = this.owner.backArmParts.get(1) == this.nextPart;

                        newNextPart = bl || bl1 ? this.owner.backArmParts.get(0) : this.owner.backArmParts.get(1);
                    }
                    armDegree = GolemMath.addDegree(this.mainDegree, this.nextPart.armSpawnedDegree.get(false));
                    if (armDegree > this.nextPart.getPrevDegree()) {
                        t = 360;
                    }
                }

                BlockPos newPos;
                if (this.duringMoving) {
                    System.out.println("    clockwise before of arm " + this.nextPart.armSpawnedDegree.get(false) + " is " + this.part.clockwise);
                    float firstDegree = this.nextPart.armFullDegree;
                    float secondDegreeAsProjected = firstDegree + (armDegree + n) - (this.nextPart.getPrevDegree() + t);
                    System.out.println("    armDegree is " + armDegree);
                    System.out.println("    n is " + n);
                    System.out.println("    armPart.getPrevDegree() is " + this.nextPart.getPrevDegree());
                    System.out.println("    t is " + t);
                    System.out.println("    secondDegreeAsProjected is " + secondDegreeAsProjected);

                    //float newDegree = this.owner.getHeadYaw() < 0 ? this.owner.getHeadYaw() + 360 : this.owner.getHeadYaw();
                    this.part.clockwise = !GolemMath.getClockwise(this.nextPart.getPrevDegree(), armDegree);
                    //this.clockwise = GolemMath.getOptimalAngle(newDegree, owner.getArmByDegree(TowerGolemEntity.FRONT_RIGHT_ARM_DEGREE).getPrevDegree(), owner.getArmByDegree(TowerGolemEntity.FRONT_LEFT_ARM_DEGREE).getPrevDegree(), owner.getPos());
                    System.out.println("    clockwise after of arm is " + this.part.clockwise);

                    newPos = TowerGolemEntity.raycastPos(this.owner, GolemMath.posFromAngle(this.owner.getPos(), GolemMath.addDegree(armDegree, 90), false).add(0, this.owner.getY(), 0));

                    this.nextPart.armGoalSelector.add(1, new TowerGolemArmPart.TowerGolemStepGoal(
                            this.owner,
                            this.nextPart,
                            this.part,
                            newPos,
                            this.nextPart.getBlockPos(),
                            n,
                            t,
                            armDegree,
                            this.mainDegree,
                            this.part.clockwise,
                            this.clockwiseConstant,
                            true));
                } else {
                    //this.nextPart.armFullDegree += (armDegree + n) - (this.nextPart.getPrevDegree() + t);

                    newPos = TowerGolemEntity.raycastPos(this.owner, GolemMath.posFromAngle(this.owner.getPos(), GolemMath.addDegree(armDegree, 90), false).add(0, this.owner.getY(), 0));

                    this.nextPart.armGoalSelector.add(1, new TowerGolemArmPart.TowerGolemStepGoal(
                            this.owner,
                            this.nextPart,
                            newNextPart,
                            newPos,
                            this.nextPart.getBlockPos(),
                            n,
                            t,
                            armDegree,
                            this.mainDegree,
                            this.clockwiseConstant));
                }


                this.owner.armNowTurning = this.nextPart;
                //this.nextPart.setPrevDegree(armDegree);

                Vec3d posOffset = new Vec3d(newPos.getX() - this.owner.getPos().x, newPos.getY() - this.owner.getPos().y, newPos.getZ() - this.owner.getPos().z);
                this.nextPart.armPosOffset = Optional.of(BlockPos.ofFloored(posOffset));
            } else {
                TowerGolemEntity.armStepStop(this.part, this.owner);
            }
        }
    }
    */


}
