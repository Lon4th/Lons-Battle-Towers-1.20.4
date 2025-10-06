package net.eps.lonsbattletowers.entity.custom.golem;

import net.eps.lonsbattletowers.entity.custom.TowerMimicEntity;
import net.eps.lonsbattletowers.entity.custom.golem.goals.TowerGolemStepGoal;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class TowerGolemPart extends MobEntity {
    private static final TrackedData<Optional<UUID>> OWNER_ID = DataTracker.registerData(TowerGolemPart.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    public TowerGolemEntity owner;
    public String name;
    protected EntityDimensions partDimensions;

    public double minXdiff;
    public double minYdiff;
    public double minZdiff;

    public double maxXdiff;
    public double maxYdiff;
    public double maxZdiff;

    private boolean TEMP_velocity = false;

    public TowerGolemPart(EntityType<? extends TowerGolemPart> entityType, World world) {
        super(entityType, world);
    }

    public TowerGolemPart(EntityType<? extends TowerGolemPart> type, TowerGolemEntity owner, String name, EntityDimensions dimensions, float ratio) {
        super(type, owner.getWorld());
        this.partDimensions = dimensions.scaled(ratio);
        this.calculateDimensions();
        this.owner = owner;
        this.name = name;

        minXdiff = this.getBoundingBox().minX - this.getX();
        minYdiff = this.getBoundingBox().minY - this.getY();
        minZdiff = this.getBoundingBox().minZ - this.getZ();

        maxXdiff = this.getBoundingBox().maxX - this.getX();
        maxYdiff = this.getBoundingBox().maxY - this.getY();
        maxZdiff = this.getBoundingBox().maxZ - this.getZ();

        this.setNoGravity(false);
        //this.setAiDisabled(true);
    }

    public TowerGolemPart setupPart(TowerGolemEntity owner, String name, float width, float height) {
        this.partDimensions = EntityDimensions.changing(width, height);
        this.calculateDimensions();
        this.owner = owner;
        this.name = name;

        return this;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(OWNER_ID, Optional.empty());
    }

    @Override
    public void tick() {
        if (!this.getWorld().isClient()) {
            if (this.dataTracker.get(OWNER_ID).isPresent() && this.owner == null) {
                UUID ownerUuid = this.dataTracker.get(OWNER_ID).get();
                System.out.println("UUID " + ownerUuid);

                this.getWorld().getServer().execute(() -> {
                    System.out.println("executed");
                    if (this.getWorld() instanceof ServerWorld serverWorld) {
                        System.out.println("executed; serverWorld - " + serverWorld);
                        Entity found = serverWorld.getEntity(ownerUuid);
                        System.out.println("executed; Entity - " + found);
                        if (found != null) {
                            System.out.println("executed; entity non null ");
                            this.owner = (TowerGolemEntity) found;
                        }
                    }
                });
            }

            if (this.owner == null) {
                System.out.println("Part removed by tick (null)");
                this.discard();
            } else if (this.owner.isDead()) {
                System.out.println("Part removed by tick (dead)");
                this.discard();
            } else if (this instanceof TowerGolemArmPart armPart) {
                if (armPart.armGoalSelector.getRunningGoals().noneMatch(goal -> goal.getGoal() instanceof TowerGolemStepGoal)) {
                    double distance = armPart.distanceTo(armPart.owner);

                    /*
                     * Реалистичная физика(кому нахуй ножно...)
                     * - Пока рука не на земле(!this.isOnGround), она тянется к this.owner.getPos()
                     * - Когда рука на земле, ее переносит(или вызывается TowerGolemStepGoal для перестановки) на ее место
                     *
                     * - НЕТ
                     *      - Если голем прыгает, то ему будет прописана отдельная "анимация", где руки располагаются вниз
                     *      - Если голем не прыгает, то для движения руки будет использоваться формула ниже
                     */

                    if ((distance > 5.0 || TEMP_velocity) && armPart.armPosOffset.isPresent() && false) {
                        Vec3d partPos = new Vec3d(
                                this.owner.getX() + armPart.armPosOffset.get().getX(),
                                this.owner.getY() + armPart.armPosOffset.get().getY(),
                                this.owner.getZ() + armPart.armPosOffset.get().getZ());
                        double d = Math.abs(partPos.getX() - armPart.getX());
                        double f = Math.abs(partPos.getZ() - armPart.getZ());

                        if ((d < 1.2 || f < 1.2) && !TEMP_velocity) {
                            //System.out.println("X dif is " + d);
                            //System.out.println("Z dif is " + f);

                            partPos = new Vec3d(
                                    armPart.getX(),
                                    armPart.owner.getY() + armPart.armPosOffset.get().getY(),
                                    armPart.getZ()
                            );
                            distance = armPart.getPos().distanceTo(partPos);
                            armPart.setVelocity(Vec3d.ZERO);
                            applyElasticity(armPart, partPos, distance);

                        } else {
                            distance = armPart.getPos().distanceTo(partPos);
                            applyElasticity(armPart, /*this.owner.getPos()*/ partPos, distance);
                            TEMP_velocity = distance != 0;
                            if (/*this.getPos().distanceTo(partPos)*/ distance < 0.5 /* && this.owner.isOnGround() */) {
                                //System.out.println("Stopppped");
                                armPart.setPosition(/*this.owner.getPos()*/ partPos);
                                armPart.setVelocity(Vec3d.ZERO);
                                TEMP_velocity = false;
                                // part.setNoDrag(false);
                            }
                        }

                    /*
                    //part.setNoDrag(true);

                    Vec3d partPos = new Vec3d(
                            this.owner.getX() + ((TowerGolemArmPart) this).armPosOffset.get().getX(),
                            this.owner.getY() + ((TowerGolemArmPart) this).armPosOffset.get().getY(),
                            this.owner.getZ() + ((TowerGolemArmPart) this).armPosOffset.get().getZ());
                    distance = this.getPos().distanceTo(partPos);

                    double d = Math.abs(partPos.getX() - this.getX());
                    double f = Math.abs(partPos.getZ() - this.getZ());
                    if ((d + f) < 1 && !TEMP_velocity) {
                        System.out.println("Xdif is " + d);
                        System.out.println("Zdif is " + f);
                        this.setVelocity(Vec3d.ZERO);
                        applyElasticity(this, partPos, distance);
                        //part.setNoDrag(false);
                        super.tick();
                        return;
                    }

                    applyElasticity(this, /*this.owner.getPos()/ partPos, distance);
                    TEMP_velocity = distance != 0;
                    System.out.println("Distance is " + distance);
                    if (/*this.getPos().distanceTo(partPos)/ distance < 0.5 /* && this.owner.isOnGround() /) {
                        System.out.println("Stopppped");
                        this.setPosition(/*this.owner.getPos()/ partPos); //TURN BACK later
                        this.setVelocity(Vec3d.ZERO);                      //TURN BACK later
                        TEMP_velocity = false;
                        // part.setNoDrag(false);
                    }
                     */
                    }
            /*
            if (distance > 5.0 && !this.getWorld().isClient() && this instanceof TowerGolemArmPart part && part.armPosOffset.isPresent()) {
                Vec3d partPos = new Vec3d(
                        part.owner.getX() + part.armPosOffset.get().getX(),
                        part.owner.getY() + part.armPosOffset.get().getY(),
                        part.owner.getZ() + part.armPosOffset.get().getZ());
                System.out.println("partPos is " + partPos + " (armposoffset is " + part.armPosOffset.get() + ")");
                System.out.println("(!) distance is " + this.distanceTo(this.owner));
                if (part.owner.getY() - part.getY() > 5) {
                    part.addVelocity(0, (part.owner.getY() - part.getY()) / 10, 0);
                }
                part.moveControl.moveTo(partPos.getX(), part.getY(), partPos.getZ(), Math.sqrt(this.distanceTo(this.owner)));
                //this.moveControl.moveTo((float) ((this.owner.getX() - this.getX()) / 10), this.getY(), (float) ((this.owner.getZ() - this.getZ()) / 10), 1);
                //this.addVelocity((this.owner.getX() - this.getX()) / 10, (this.owner.getY() - this.getY()) / 10, (this.owner.getZ() - this.getZ()) / 10);
            }

             */
                }
            }
        }

        super.tick();
    }

    private static void applyElasticity(Entity entity, Vec3d targetPos, double distance) {
        double d = (targetPos.getX() - entity.getX()) / distance;
        double e = (targetPos.getY() - entity.getY()) / distance;
        double f = (targetPos.getZ() - entity.getZ()) / distance;
        entity.setVelocity(entity.getVelocity().add(Math.copySign(d * d * 0.4, d), Math.copySign(e * e * 0.4, e), Math.copySign(f * f * 0.4, f)));

        double angle = Math.atan2(targetPos.getZ() - entity.getZ(), targetPos.getX() - entity.getX());
        double angle2 = Math.atan2(targetPos.getY() - entity.getY(), targetPos.getX() - entity.getX());
        //entity.setVelocity(entity.getVelocity().add(Math.cos(angle) / 2, Math.sin(angle2) / 2, Math.sin(angle) / 2));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
    }

    public String getPartName() {
        return name;
    }

    public void setPartName(String name) {
        this.name = name;
    }

    public TowerGolemEntity getPartOwnerById() {
        if (!this.getWorld().isClient() && this.dataTracker.get(OWNER_ID).isPresent()) {
            UUID ownerUuid = this.dataTracker.get(OWNER_ID).get();
            System.out.println("UUID " + ownerUuid);

            this.getWorld().getServer().execute(() -> {
                System.out.println("executed");
                if (this.getWorld() instanceof ServerWorld serverWorld) {
                    System.out.println("executed; serverWorld - " + serverWorld);
                    Entity found = serverWorld.getEntity(ownerUuid);
                    System.out.println("executed; Entity - " + found);
                    if (found != null) {
                        System.out.println("executed; entity non null ");
                        this.owner = (TowerGolemEntity) found;
                    }
                }
            });
        }

        return this.owner;
    }

    public void setPartOwnerById(UUID id) {
        this.dataTracker.set(OWNER_ID, Optional.of(id));
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return this.isInvulnerableTo(source) ? false : this.owner != null && this.owner.damagePart(/*this, */source, amount);
    }

    @Override
    public boolean isPartOf(Entity entity) {
        return this == entity || this.owner == entity;
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        //super.getDimensions(pose);
        //return new EntityDimensions(this.dataTracker.get(PART_WIDTH), this.dataTracker.get(PART_HEIGHT), false);
        return this.partDimensions;
    }

    @Override
    public boolean shouldSave() {
        return false;
    }

    @Override
    public boolean cannotDespawn() {
        if (this.owner != null) {
            return true;
        } else {
            return super.cannotDespawn();
        }
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return this.owner != null ? this.owner.getEquippedStack(slot) : ItemStack.EMPTY;
    }
    @Nullable
    public ItemStack getPickBlockStack() {
        return this.owner != null ? this.owner.getPickBlockStack() : ItemStack.EMPTY;
    }
    public Iterable<ItemStack> getArmorItems() {
        return this.owner != null ? this.owner.getArmorItems() : Collections.singleton(ItemStack.EMPTY);
    }
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        if (this.owner != null) {
            this.owner.equipStack(slot, stack);
        }
    }
    public Arm getMainArm() {
        return this.owner != null ? this.owner.getMainArm() : Arm.RIGHT;
    }

    public static DefaultAttributeContainer.Builder createTowerGolemAttributes() {
        //return LivingEntity.createLivingAttributes();
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6)
                ;
    }
}
