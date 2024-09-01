package net.eps.lonsbattletowers.entity.custom;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.block.custom.vault.TowerVaultConfig;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.brain.Memory;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.BreezeJumpTask;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.MathUtil;

import java.util.*;

public class TowerMimicEntity extends HostileEntity implements RangedAttackMob {
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> JUMP = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> JUMP_LAND = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> BREAK = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SPAWN = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> TOP_OPEN = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final TrackedData<Integer> SHOOTING_STATE = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> LAND = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> ANIMATION_RESET = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.INTEGER);


    /*public static final TrackedData<Float> HEAD_YAW = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> HEAD_PITCH = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> HEAD_ROLL = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> LEGS_YAW = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> LEGS_PITCH = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> LEGS_ROLL = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public static final TrackedData<Boolean> MIMIC_ROTATION_FIXED = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.BOOLEAN);*/
    //private static final TrackedData<Integer> JUMP_STATE = DataTracker.registerData(TowerMimicEntity.class, TrackedDataHandlerRegistry.INTEGER);


    private boolean targetChange = false;
    private boolean allowGoalsInit = true;

    private int jumpCooldown = 0;
    private final int maxJumpCooldown = 25;

    private BlockPos jumpTarget = null;

    private List<ItemStack> itemsToEject = new ObjectArrayList<>();
    private long nextEjectingTime = 0;

    private int timeWithoutAttacking = 0;
    private final int maxTimeWithoutAttacking = 60;


    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState landAnimationState = new AnimationState();
    public final AnimationState breakAnimationState = new AnimationState();
    public final AnimationState spawnAnimationState = new AnimationState();

    public final AnimationState jumpAnimationStartState = new AnimationState();
    public final AnimationState jumpAnimationLandState = new AnimationState();

    public final AnimationState shootAnimationStartState = new AnimationState();
    public final AnimationState shootAnimationShootState = new AnimationState();
    public final AnimationState shootAnimationEndState = new AnimationState();

    private int idleAnimationCooldown = 0;
    private int attackAnimationCooldown = 0;
    private int landAnimationCooldown = 0;
    private int breakAnimationCooldown = 0;
    private int spawnAnimationCooldown = 0;

    private int jumpAnimationCooldown = 0;
    private int jumpAnimationLandCooldown = 0;

    private int shootAnimationStartCooldown = 0;
    private int shootAnimationShootCooldown = 0;
    private int shootAnimationEndCooldown = 0;


    private final TowerMimicShootGoal shootGoal = new TowerMimicShootGoal(this, 1.0, 100, 5);
    private final TowerMimicAttackGoal attackGoal = new TowerMimicAttackGoal(this, 1.0, false);

    public TowerMimicEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            decreaseAnimationCooldowns();

            if (!this.isDead()) {
                updateAliveAnimations();
            } else {
                updateBreakAnimation();
                disableAliveAnimations();
            }
        } else {
            updateAttackType();
        }

        if (this.getTarget() != null) {
            if (!this.targetChange) {
                if (this.getTarget() != null && !this.hasShootingGoal()) {
                    LivingEntity target = this.getTarget();
                    double x = target.getX() - this.getX();
                    double y = target.getY() - this.getY();
                    double z = target.getZ() - this.getZ();

                    double distance = Math.sqrt(x * x + z * z);

                    if (this.distanceTo(target) > 3 && y < 10 && !(distance <= 1 && y > 3)) {
                        this.goalSelector.add(4, new TowerMimicJumpGoal(this, this.getTarget(), (int) Math.round(y), (int) Math.round(distance)));
                        this.goalSelector.add(3, this.attackGoal);
                        this.setTimeWithoutAttacking(0);
                    }
                }

                this.targetChange = true;
            }
            this.timeWithoutAttacking++;
        } else {
            this.setTimeWithoutAttacking(0);
            if (this.targetChange && !hasJumpingGoal()) {
                this.targetChange = false;
            }
        }

        if (!this.getWorld().isClient()) {
            if (this.getShootState() > 0 && this.getShootState() < 3 && this.getTarget() == null) {
                this.setShootState(3);
                this.dataTracker.set(ANIMATION_RESET, 1);
            }
            if (this.getJumpAnimation() && this.getPose() != EntityPose.INHALING && this.isOnGround()) {
                this.setJumpAnimation(false);
                this.dataTracker.set(ANIMATION_RESET, 2);
            }
        } else {
            if (this.dataTracker.get(ANIMATION_RESET) == 1) {
                this.setShootState(3);
                this.shootAnimationStartState.stop();
                this.shootAnimationShootState.stop();
            }
            if (this.dataTracker.get(ANIMATION_RESET) == 2) {
                this.setJumpLandAnimation(true);
                this.jumpAnimationStartState.stop();
            }
            this.dataTracker.set(ANIMATION_RESET, 0);
        }

        //if (allowGoalsInit && this.isAlive()) {
        //    this.initGoals();
        //}

    }

    @Override
    protected void initGoals() {
        if (!this.allowGoalsInit && !this.isAlive()) {
            return;
        }

        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.6, 12));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f, 0.02F, true));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(2, new RevengeGoal(this, RaiderEntity.class).setGroupRevenge(new Class[0]));
        this.targetSelector.add(3, new ActiveTargetGoal<PlayerEntity>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<IronGolemEntity>(this, IronGolemEntity.class, true));
    }

    private void clearGoals() {
        this.allowGoalsInit = false;
        goalSelector.getGoals().forEach(PrioritizedGoal::stop);
        goalSelector.clear(Objects::nonNull);
    }

    @Override
    protected void updateLimbs(float posDelta) {
        //if (!this.isAlive()) { return; }

        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    private void updateAliveAnimations() {
        if (this.idleAnimationCooldown <= 0) {
            this.idleAnimationCooldown = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        }

        /* Melee Attack Animations */
        if (this.getAttackAnimation() && attackAnimationCooldown <= 0) {
            this.attackAnimationCooldown = 14;
            this.attackAnimationState.start(this.age);
        }

        /* Shoot Animations */
        if (this.getShootState() == 3 && shootAnimationEndCooldown <= 0) {
            this.shootAnimationStartState.stop();
            this.shootAnimationShootState.stop();

            this.shootAnimationEndCooldown = 6;
            this.shootAnimationEndState.start(this.age);
            setShootState(0);
        }
        if (this.getShootState() == 1 && shootAnimationStartCooldown <= 0) {
            this.shootAnimationEndState.stop();
            this.shootAnimationShootState.stop();

            this.shootAnimationStartCooldown = 6;
            this.shootAnimationStartState.start(this.age);
            setShootState(0);
        }
        if (this.getShootState() == 2 && shootAnimationShootCooldown <= 0) {
            this.shootAnimationStartState.stop();
            this.shootAnimationEndState.stop();

            this.shootAnimationShootCooldown = 20;
            this.shootAnimationShootState.start(this.age);
        }
        if (getShootState() == -1 && (this.shootAnimationStartState.isRunning() || this.shootAnimationShootState.isRunning())) {
            setShootState(3);
        }

        /* Land Animation */
        if (this.getLandAnimation() > 0 && landAnimationCooldown <= 0) {
            this.landAnimationCooldown = 14;
            this.landAnimationState.start(this.age);
            setLandAnimation(0);

        }

        /* Jump Animation */
        if (this.getJumpAnimation() && jumpAnimationCooldown <= 0) {
            this.jumpAnimationLandState.stop();

            this.jumpAnimationCooldown = 18;
            this.jumpAnimationStartState.start(this.age);
            setJumpAnimation(false);

        }
        if (this.getJumpLandAnimation() && jumpAnimationLandCooldown <= 0) {
            this.jumpAnimationStartState.stop();

            this.jumpAnimationLandCooldown = 13;
            this.jumpAnimationLandState.start(this.age);
            setJumpLandAnimation(false);

        }

        /* Spawn */
        if (this.getSpawnAnimation() && spawnAnimationCooldown <= 0) {
            this.spawnAnimationCooldown = 14;
            this.spawnAnimationState.start(this.age);
            setSpawnAnimation(false);
        }




        /*if (this.dataTracker.get(JUMP_STATE) == 1 && jumpAnimationCooldown <= 0) {
            System.out.println("JUMP STATE " + 1);
            this.jumpAnimationCooldown = 18;
            this.jumpAnimationStartState.start(this.age);
            this.dataTracker.set(JUMP_STATE, 0);

        } else {
            --this.jumpAnimationCooldown;
        }

        if (this.dataTracker.get(JUMP_STATE) == 2 && jumpAnimationCooldown <= 0) {
            System.out.println("JUMP STATE " + 2);
            this.jumpAnimationStartState.stop();
            this.dataTracker.set(JUMP_STATE, 0);

        } else {
            --this.jumpAnimationCooldown;
        }*/
    }

    private void updateBreakAnimation() {
        /* Break */
        if (this.getBreakAnimation() && breakAnimationCooldown <= 0) {
            this.breakAnimationCooldown = 14;
            this.breakAnimationState.start(this.age);
            System.out.println("Breaking animation started");
            setBreakAnimation(false);
        }
    }


    private void disableAliveAnimations() {
        this.idleAnimationState.stop();
        this.attackAnimationState.stop();
        this.shootAnimationEndState.stop();
        this.shootAnimationStartState.stop();
        this.shootAnimationShootState.stop();
        this.landAnimationState.stop();
        this.jumpAnimationStartState.stop();
        this.jumpAnimationLandState.stop();
    }

    private void decreaseAnimationCooldowns() {
        --this.idleAnimationCooldown;
        --this.attackAnimationCooldown;
        this.shootAnimationEndCooldown = Math.max(this.shootAnimationEndCooldown - 1, 0);
        this.shootAnimationStartCooldown = Math.max(this.shootAnimationStartCooldown - 1, 0);
        this.shootAnimationShootCooldown = Math.max(--this.shootAnimationShootCooldown, 0);
        --this.landAnimationCooldown;
        --this.jumpAnimationCooldown;
        --this.jumpAnimationLandCooldown;
        --this.breakAnimationCooldown;
    }

    public void updateAttackType() {
        if (this.getWorld() == null || this.getWorld().isClient) {
            return;
        }

        setLandAnimation(Math.max(getLandAnimation() - 1, 0));
        setJumpCooldown(Math.max(getJumpCooldown() - 1, 0));


        if (this.timeWithoutAttacking > this.maxTimeWithoutAttacking) {
            this.goalSelector.remove(this.attackGoal);
            this.goalSelector.add(4, this.shootGoal);
        } else {
            this.goalSelector.remove(this.shootGoal);
            this.goalSelector.add(4, this.attackGoal);
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.getWorld().isClient) {
            return false;
        }
        if (this.isDead()) {
            return false;
        }

        /*if (source.isOf(DamageTypes.TRIDENT)) {
            System.out.println("JUMP STATE should be " + 1);
            this.dataTracker.set(JUMP_STATE, 1);
            return false;
        } else if (source.isOf(DamageTypes.ARROW)) {
            System.out.println("JUMP STATE should be " + 2);
            this.jumpAnimationStartState.stop();
            //this.dataTracker.set(JUMP_STATE, 2);
            return false;
        } else if (source.isOf(DamageTypes.PLAYER_ATTACK)) {
            System.out.println("JUMP STATE should be " + 0);
            this.dataTracker.set(JUMP_STATE, 0);
        }*/
        if (source.isOf(DamageTypes.TRIDENT)) {
            System.out.println("JUMP STATE should be " + 1);
            this.setSpawnAnimation(true);
            return false;
        }

        if (this.getHealth() <= this.getMaxHealth() / 2 && source.getAttacker() == this.getTarget() && this.goalSelector.getRunningGoals().noneMatch(goal -> goal.getGoal() instanceof TowerMimicJumpGoal)) {
            if ((hasMeleeGoal() && this.getRandom().nextInt(100) < 20) || hasShootingGoal()) {
                this.goalSelector.add(4, new TowerMimicJumpGoal(this, this.getTarget(), 0, 0));
            }
        }

        if (source.isOf(DamageTypes.FALL)) {
            setLandAnimation(7);

            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        this.setBreakAnimation(true);

        if (!this.getWorld().isClient()) {
            Entity killer = damageSource.getAttacker();
            ServerWorld world = (ServerWorld) this.getWorld();
            Vec3d pos = this.getPos().add(0.0, 0.0, 0.0);

            this.nextEjectingTime = this.getWorld().getTime() + 15L;
            this.itemsToEject = generateLoot(world, pos, killer != null ? (killer.isPlayer() ? (PlayerEntity) killer : null) : null);

            this.getNavigation().stop();
            this.setVelocity(Vec3d.ZERO);
        }

        //super.onDeath(damageSource);
    }

    @Override
    protected void updatePostDeath() {
        if (this.getWorld().isClient()) {
            return;
        }

        this.clearGoals();

        ServerWorld world = (ServerWorld) this.getWorld();
        Vec3d pos = this.getPos().add(0.0, 0.0, 0.0);

        if (!this.isRemoved() && !this.itemsToEject.isEmpty()) {

            if (world.getTime() >= this.nextEjectingTime && this.getTopOpen()) {
                this.ejectItem(world, pos, this.getItemToEject(), 1.0F);

                this.nextEjectingTime = world.getTime() + 20L;
            } else if (world.getTime() >= this.nextEjectingTime) {
                this.setTopOpen(true);

                this.nextEjectingTime = world.getTime() + 20L;
            }
        }

        if (this.itemsToEject.isEmpty() && (world.getTime() >= this.nextEjectingTime)) {
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_DEATH_PARTICLES);
            this.remove(RemovalReason.KILLED);
        }
    }

    private static List<ItemStack> generateLoot(ServerWorld world, Vec3d pos, PlayerEntity player) {
        LootTable lootTable = world.getServer().getLootManager().getLootTable(new Identifier(LonsBattleTowers.MOD_ID, "chests/tower_vault"));
        LootContextParameterSet lootContextParameterSet = player != null ?
                new LootContextParameterSet.Builder(world).add(LootContextParameters.ORIGIN, pos).luck(player.getLuck()).add(LootContextParameters.THIS_ENTITY, player).build(LootContextTypes.CHEST) :
                new LootContextParameterSet.Builder(world).add(LootContextParameters.ORIGIN, pos).build(LootContextTypes.CHEST);

        return lootTable.generateLoot(lootContextParameterSet);
    }

    private void ejectItem(ServerWorld world, Vec3d pos, ItemStack stack, float pitchModifier) {
        ItemDispenserBehavior.spawnItem(world, stack, 2, Direction.UP, pos.offset(Direction.UP, 1.2));
        //world.syncWorldEvent(/*WorldEvents.VAULT_EJECTS_ITEM*/ WorldEvents.TRIAL_SPAWNER_EJECTS_ITEM, BlockPos.ofFloored(pos), 0);
        world.playSound(null, BlockPos.ofFloored(pos), /*SoundEvents.BLOCK_VAULT_EJECT_ITEM*/ SoundEvents.BLOCK_TRIAL_SPAWNER_EJECT_ITEM, SoundCategory.BLOCKS, 1.0F, 0.8F + 0.4F * pitchModifier);
    }

    public ItemStack getItemToEject() {
        if (this.itemsToEject.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            return Objects.requireNonNullElse(this.itemsToEject.remove(this.itemsToEject.size() - 1), ItemStack.EMPTY);
        }
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }


    public boolean hasMeleeGoal() {
        return this.goalSelector.getRunningGoals().anyMatch(goal -> goal.getGoal() instanceof TowerMimicAttackGoal);
    }
    public boolean hasShootingGoal() {
        return this.goalSelector.getRunningGoals().anyMatch(goal -> goal.getGoal() instanceof TowerMimicShootGoal);
    }
    public boolean hasJumpingGoal() {
        return this.goalSelector.getRunningGoals().anyMatch(goal -> goal.getGoal() instanceof TowerMimicJumpGoal);
    }


    public void setJumpPos(BlockPos pos) {
        this.jumpTarget = pos;
    }
    public BlockPos getJumpPos() {
        return this.jumpTarget;
    }

    public void setTimeWithoutAttacking(int time) {
        this.timeWithoutAttacking = time;
    }

    public void setJumpCooldown(int land) {
        this.jumpCooldown = land;
    }
    public int getJumpCooldown() {
        return this.jumpCooldown;
    }
    public void setShootState(int state) {
        this.dataTracker.set(SHOOTING_STATE, state);
    }
    public int getShootState() {
        return this.dataTracker.get(SHOOTING_STATE);
    }
    public void setLandAnimation(int land) {
        this.dataTracker.set(LAND, land);
    }
    public int getLandAnimation() {
        return this.dataTracker.get(LAND);
    }
    public void setTopOpen(boolean open) {
        this.dataTracker.set(TOP_OPEN, open);
    }
    public boolean getTopOpen() {
        return this.dataTracker.get(TOP_OPEN);
    }

    public void setAttackAnimation(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }
    public boolean getAttackAnimation() {
        return this.dataTracker.get(ATTACKING);
    }
    public void setJumpAnimation(boolean land) {
        this.dataTracker.set(JUMP, land);
    }
    public boolean getJumpAnimation() {
        return this.dataTracker.get(JUMP);
    }
    public void setJumpLandAnimation(boolean land) {
        this.dataTracker.set(JUMP_LAND, land);
    }
    public boolean getJumpLandAnimation() {
        return this.dataTracker.get(JUMP_LAND);
    }
    public void setBreakAnimation(boolean Break) {
        this.dataTracker.set(BREAK, Break);
    }
    public boolean getBreakAnimation() {
        return this.dataTracker.get(BREAK);
    }
    public void setSpawnAnimation(boolean Break) {
        this.dataTracker.set(SPAWN, Break);
    }
    public boolean getSpawnAnimation() {
        return this.dataTracker.get(SPAWN);
    }


    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(SHOOTING_STATE, 0);
        this.dataTracker.startTracking(LAND, 0);
        this.dataTracker.startTracking(JUMP, false);
        this.dataTracker.startTracking(JUMP_LAND, false);
        this.dataTracker.startTracking(ANIMATION_RESET, 0);
        this.dataTracker.startTracking(BREAK, false);
        this.dataTracker.startTracking(TOP_OPEN, false);
        this.dataTracker.startTracking(SPAWN, false);

        /*this.dataTracker.startTracking(MIMIC_ROTATION_FIXED, false);
        this.dataTracker.startTracking(HEAD_YAW, 0f);
        this.dataTracker.startTracking(HEAD_PITCH, 0f);
        this.dataTracker.startTracking(HEAD_ROLL, 0f);
        this.dataTracker.startTracking(LEGS_YAW, 0f);
        this.dataTracker.startTracking(LEGS_PITCH, 0f);
        this.dataTracker.startTracking(LEGS_ROLL, 0f);*/
        //this.dataTracker.startTracking(JUMP_STATE, 0);
    }

    public static DefaultAttributeContainer.Builder createTowerMimicAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40)
                .add(EntityAttributes.GENERIC_ARMOR, 10)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1);
    }

    /* Goals & Attacks */

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        PersistentProjectileEntity persistentProjectileEntity = this.createArrowProjectile(pullProgress);
        if (persistentProjectileEntity instanceof ArrowEntity) {
            int i = MathHelper.nextBetween(Random.create(), 0, 4);
            switch (i) {
                case 0: ((ArrowEntity) persistentProjectileEntity).addEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1));
                case 1: ((ArrowEntity) persistentProjectileEntity).addEffect(new StatusEffectInstance(StatusEffects.POISON, 200));
            }
        }

        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333) - persistentProjectileEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);

        double ef = target.getBodyY(0.5) - this.getBodyY(0.5);
        double h = Math.sqrt(Math.sqrt(this.squaredDistanceTo(target))) * 0.5;


        persistentProjectileEntity.setVelocity(d, e + g * (double)0.2f, f, 1.6f, 14 - this.getWorld().getDifficulty().getId() * 4);

        this.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
        if (Math.random() >= 0.2) {
            this.getWorld().spawnEntity(persistentProjectileEntity);
        } else {
            for (int i = 0; i < 3; i++) {
                SmallFireballEntity smallFireballEntity = new SmallFireballEntity(this.getWorld(), this, this.getRandom().nextTriangular(d, 2.297 * h), ef + this.getRandom().nextBetween(-1, 1), this.getRandom().nextTriangular(f, 2.297 * h));
                smallFireballEntity.setPosition(smallFireballEntity.getX(), this.getBodyY(0.5), smallFireballEntity.getZ());

                this.getWorld().spawnEntity(smallFireballEntity);
            }
        }
    }

    protected PersistentProjectileEntity createArrowProjectile(float damageModifier) {
        return ProjectileUtil.createArrowProjectile(this, Items.AIR.getDefaultStack(), damageModifier);
    }



    /* Attack Goal */

    public static class TowerMimicAttackGoal extends MeleeAttackGoal {
        private final TowerMimicEntity entity;
        private int ticksUntilNextAttack = 7;
        private boolean shouldCountTillNextAttack = false;

        public TowerMimicAttackGoal(TowerMimicEntity mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
            entity = mob;
        }

        @Override
        public void start() {
            super.start();
            this.entity.setShootState(-1);
            shouldCountTillNextAttack = false;
        }


        @Override
        protected void attack(LivingEntity pEnemy) {
            //System.out.println("Overall attack started");
            if (this.canAttack(pEnemy)) {
                //System.out.println("System thinks he can attack player");
                shouldCountTillNextAttack = true;
            }

            if (shouldCountTillNextAttack) {
                //System.out.println("shouldCountTillNextAttack started");
                if (ticksUntilNextAttack == 7) {
                    //System.out.println("Animation started");
                    entity.setAttackAnimation(true);
                }

                if (this.entity.getPose() != EntityPose.STANDING && ticksUntilNextAttack <= 7) {
                    //System.out.println("Attack in air started");

                    this.resetCooldown();
                    this.mob.tryAttack(pEnemy);

                    entity.setTimeWithoutAttacking(0);
                } else if (ticksUntilNextAttack <= 4 && canContinueAttack(pEnemy)) {
                    //System.out.println("Actual attack started");

                    this.resetCooldown();
                    this.mob.tryAttack(pEnemy);

                    entity.setTimeWithoutAttacking(0);
                }

                if (ticksUntilNextAttack <= 0) {
                    //System.out.println("Attack ended");
                    resetAttackCooldown();
                    shouldCountTillNextAttack = false;
                    entity.setAttackAnimation(false);
                    entity.setAttacking(false);
                    entity.attackAnimationCooldown = 0;
                }
            }
        }

        protected void resetAttackCooldown() {
            this.ticksUntilNextAttack = 7;
        }

        @Override
        public void tick() {
            super.tick();
            if(shouldCountTillNextAttack) {
                this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);

            } else if (ticksUntilNextAttack <= 0) {
                resetAttackCooldown();
            }
        }

        protected boolean canContinueAttack(LivingEntity target) {
            return this.entity.getAttackBox().expand(0.2, 0, 0.2).intersects(target.getHitbox());
        }

        @Override
        public void stop() {
            entity.setAttackAnimation(false);
            entity.setAttacking(false);
            this.ticksUntilNextAttack = 7;
            shouldCountTillNextAttack = false;
            super.stop();
        }
    }

    public static class TowerMimicShootGoal extends Goal {
        private final TowerMimicEntity actor;
        private final double speed;
        private int attackInterval;
        private final float squaredRange;
        private int targetSeeingTicker;
        private int combatTicks = -1;
        private final int attackIntervalSave;

        public TowerMimicShootGoal(TowerMimicEntity actor, double speed, int attackInterval, float range) {
            this.actor = actor;
            this.speed = speed;
            this.attackInterval = attackInterval;
            this.attackIntervalSave = attackInterval;
            this.squaredRange = range * range;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            if ((this.actor).getTarget() == null) {
                return false;
            }
            return true;
        }

        @Override
        public boolean shouldContinue() {
            //return (this.canStart() || !(this.actor).getNavigation().isIdle());
            return this.canStart();
        }

        @Override
        public void start() {
            super.start();
            this.attackInterval = this.attackIntervalSave;
            this.actor.setShootState(1);
            (this.actor).setAttacking(true);
        }

        @Override
        public void stop() {
            super.stop();
            (this.actor).setAttacking(false);
            this.targetSeeingTicker = 0;
            (this.actor).clearActiveItem();
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            boolean bl2;
            LivingEntity livingEntity = (this.actor).getTarget();
            if (livingEntity == null) return;


            double distance = (this.actor).squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            boolean bl = (this.actor).getVisibilityCache().canSee(livingEntity);
            boolean bl3 = bl2 = this.targetSeeingTicker > 0;

            if (bl != bl2) {
                this.targetSeeingTicker = 0;
            }
            this.targetSeeingTicker = bl ? ++this.targetSeeingTicker : --this.targetSeeingTicker;

            if (distance > (double)this.squaredRange || this.targetSeeingTicker < 20) {
                (this.actor).getNavigation().startMovingTo(livingEntity, this.speed);
                this.combatTicks = -1;
            } else {
                (this.actor).getNavigation().stop();
                ++this.combatTicks;
            }

            if (this.combatTicks >= 20) {
                this.combatTicks = 0;
            }

            if (this.combatTicks > -1) {
                Entity entity = (this.actor).getControllingVehicle();
                if (entity instanceof MobEntity mobEntity) {
                    mobEntity.lookAtEntity(livingEntity, 30.0f, 30.0f);
                }
                (this.actor).lookAtEntity(livingEntity, 30.0f, 30.0f);
            } else {
                (this.actor).getLookControl().lookAt(livingEntity, 30.0f, 30.0f);
            }

            if (bl) {
                (this.actor).getNavigation().stop();
                (this.actor).lookAtEntity(livingEntity, 30.0f, 30.0f);


                this.attackInterval--;
                switch (this.attackInterval) {
                    case 100 -> this.actor.shootAnimationStartState.stop();
                    case 60, 40, 20 -> {
                        this.actor.setShootState(2);
                        ((RangedAttackMob) this.actor).shootAt(livingEntity, 1);
                    }
                    case 0 -> {
                        this.actor.setShootState(3);
                        this.actor.setTimeWithoutAttacking(0);
                    }
                }
            }
        }
    }

    public static class TowerMimicJumpGoal extends Goal {
        private final TowerMimicEntity entity;
        private LivingEntity attacker;
        private static ObjectArrayList<Integer> POSSIBLE_JUMP_ANGLES = new ObjectArrayList<Integer>(Lists.newArrayList(65, 70, 75));
        private boolean canChangeAttackType = true;
        private boolean isDistanceTrigger = false;
        private int ticksInAir = 10;

        public TowerMimicJumpGoal(TowerMimicEntity entity, LivingEntity attacker, int distanceY, int distance) {
            this.entity = entity;
            this.attacker = attacker;
            if (distanceY != 0 || distance != 0) {
                this.canChangeAttackType = false;
                this.isDistanceTrigger = true;
                POSSIBLE_JUMP_ANGLES = new ObjectArrayList<Integer>(Lists.newArrayList(60 + distanceY * 2 - distance, 70 + distanceY * 2 - distance, 80 + distanceY - distance));
            }
        }

        @Override
        public boolean canStart() {
            if (this.attacker == null) {
                return false;
            }
            BlockPos blockPos = TowerMimicJumpGoal.getPosToJumpTo(this.entity, TowerMimicJumpGoal.getRandomPosBehindTarget(this.attacker, this.entity.getRandom(), this.isDistanceTrigger));
            if (blockPos == null) {
                return false;
            }
            if (!TowerMimicJumpGoal.canJumpTo(this.entity, blockPos.toCenterPos()) && !TowerMimicJumpGoal.canJumpTo(this.entity, blockPos.up(4).toCenterPos())) {
                return false;
            }
            if (this.entity.getJumpCooldown() > 0) {
                return false;
            }
            this.entity.setJumpPos(blockPos);
            return true;
        }

        @Override
        public void start() {
            if (this.entity.getJumpPos() != null && this.attacker != null) {
                this.entity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, this.attacker.getEyePos());
            }
            if (this.entity.hasShootingGoal()) {
                this.canChangeAttackType = false;
            }
            this.entity.setPose(EntityPose.INHALING);
        }

        @Override
        public boolean shouldContinue() {
            return this.entity.getPose() != EntityPose.STANDING && this.entity.getJumpCooldown() == 0 && this.entity.isAlive();
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (this.ticksInAir == 10 && !this.entity.getJumpAnimation()) {
                this.entity.setJumpAnimation(true);
            }

            if (this.entity.getPose() == EntityPose.INHALING) {
                this.ticksInAir = Math.max(this.ticksInAir - 1, 0);
            }


            if (this.ticksInAir == 0 && this.entity.getPose() == EntityPose.INHALING) {
                BlockPos pos = this.entity.getJumpPos();
                Vec3d vec3d = TowerMimicJumpGoal.getJumpingVelocity(this.entity, this.entity.getRandom(), Vec3d.ofBottomCenter(pos)).orElse(null);
                if (vec3d == null) {
                    this.entity.setPose(EntityPose.STANDING);
                    return;
                }
                if (this.entity.getTarget() == null) {
                    this.entity.setTarget(this.attacker);
                }
                this.entity.setPose(EntityPose.LONG_JUMPING);
                this.entity.setYaw(this.entity.bodyYaw);
                this.entity.setNoDrag(true);
                this.entity.setVelocity(vec3d);

            } else if (this.entity.getPose() == EntityPose.LONG_JUMPING && this.entity.isOnGround()) {
                this.entity.setPose(EntityPose.STANDING);
                this.entity.setNoDrag(false);
                this.entity.setJumpCooldown(this.entity.maxJumpCooldown);

                this.entity.setJumpAnimation(false);
                this.entity.setJumpLandAnimation(true);

                if (this.canChangeAttackType) {
                    this.entity.setTimeWithoutAttacking(this.entity.maxTimeWithoutAttacking);
                }
            }

        }

        @Override
        public void stop() {
            if (this.entity.getPose() == EntityPose.LONG_JUMPING || this.entity.getPose() == EntityPose.INHALING) {
                this.entity.setPose(EntityPose.STANDING);
            }
            this.ticksInAir = 10;
            this.attacker = null;
            this.entity.setJumpLandAnimation(false);
        }

        @VisibleForTesting
        public static boolean canJumpTo(TowerMimicEntity mimic, Vec3d jumpPos) {
            Vec3d vec3d = new Vec3d(mimic.getX(), mimic.getY(), mimic.getZ());
            if (jumpPos.distanceTo(vec3d) > 50.0) {
                return false;
            }
            return mimic.getWorld().raycast(new RaycastContext(vec3d, jumpPos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mimic)).getType() == HitResult.Type.MISS;
        }

        private static Vec3d getRandomPosBehindTarget(LivingEntity target, Random random, boolean isDistanceTrigger) {
            if (isDistanceTrigger) {
                float f = target.headYaw + 180.0f + (float)random.nextGaussian() * 90.0f / 2.0f;
                Vec3d vec3d = Vec3d.fromPolar(0.0f, f).multiply(0.5);
                return target.getPos().add(vec3d);
            }
            int i = 90;
            float f = target.headYaw + 180.0f + (float)random.nextGaussian() * 90.0f / 4.0f;
            float g = MathHelper.lerp(random.nextFloat(), 9.0f, 11.0f);
            Vec3d vec3d = Vec3d.fromPolar(0.0f, f).negate().multiply(g);
            return target.getPos().add(vec3d);
        }

        @Nullable
        private static BlockPos getPosToJumpTo(LivingEntity mimic, Vec3d pos) {
            RaycastContext raycastContext = new RaycastContext(pos, pos.offset(Direction.DOWN, 10.0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mimic);
            BlockHitResult hitResult = mimic.getWorld().raycast(raycastContext);
            if (((HitResult)hitResult).getType() == HitResult.Type.BLOCK) {
                return BlockPos.ofFloored(hitResult.getPos()).up();
            }
            RaycastContext raycastContext2 = new RaycastContext(pos, pos.offset(Direction.UP, 10.0), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mimic);
            BlockHitResult hitResult2 = mimic.getWorld().raycast(raycastContext2);
            if (((HitResult)hitResult2).getType() == HitResult.Type.BLOCK) {
                return BlockPos.ofFloored(hitResult.getPos()).up();
            }
            return null;
        }

        private static Optional<Vec3d> getJumpingVelocity(TowerMimicEntity mimic, Random random, Vec3d jumpTarget) {
            List<Integer> list = Util.copyShuffled(POSSIBLE_JUMP_ANGLES, random);
            for (int i : list) {
                Optional<Vec3d> optional = LongJumpUtil.getJumpingVelocity(mimic, jumpTarget, 10.4f, i, false);
                if (!optional.isPresent()) continue;
                return optional;
            }
            return Optional.empty();
        }
    }


}
