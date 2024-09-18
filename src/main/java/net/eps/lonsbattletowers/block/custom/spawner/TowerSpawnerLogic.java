package net.eps.lonsbattletowers.block.custom.spawner;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.eps.lonsbattletowers.particle.ModParticles;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;

import java.util.Optional;
import java.util.UUID;

public class TowerSpawnerLogic {
    public static final int field_47358 = 40;
    private static final int MAX_ENTITY_DISTANCE = 47;
    private static final int MAX_ENTITY_DISTANCE_SQUARED = MathHelper.square(47);
    private final TowerSpawnerConfig config;
    private final TowerSpawnerData data;
    private final TowerSpawnerLogic.TowerSpawner towerSpawner;
    private EntityDetector entityDetector;
    private boolean forceActivate;

    public Codec<TowerSpawnerLogic> codec() {
        return RecordCodecBuilder.create(instance -> instance.group(
                TowerSpawnerConfig.codec.forGetter(TowerSpawnerLogic::getConfig),
                TowerSpawnerData.codec.forGetter(TowerSpawnerLogic::getData)).apply(instance, (towerSpawnerConfig, towerSpawnerData) -> new TowerSpawnerLogic(towerSpawnerConfig, towerSpawnerData, this.towerSpawner, this.entityDetector)));
    }

    public TowerSpawnerLogic(TowerSpawnerLogic.TowerSpawner towerSpawner, EntityDetector entityDetector) {
        this(TowerSpawnerConfig.defaultInstance, new TowerSpawnerData(), towerSpawner, entityDetector);
    }

    public TowerSpawnerLogic(TowerSpawnerConfig config, TowerSpawnerData data, TowerSpawnerLogic.TowerSpawner towerSpawner, EntityDetector entityDetector) {
        this.config = config;
        this.data = data;
        this.data.populateSpawnDataPool(config);
        this.towerSpawner = towerSpawner;
        this.entityDetector = entityDetector;
    }

    public TowerSpawnerConfig getConfig() {
        return this.config;
    }
    public TowerSpawnerData getData() {
        return this.data;
    }
    public TowerSpawnerState getSpawnerState() {
        return this.towerSpawner.getSpawnerState();
    }
    public TowerSpawnerEvent getSpawnerEvent() {
        return this.towerSpawner.getSpawnerEvent();
    }

    public void setSpawnerState(World world, TowerSpawnerState spawnerState) {
        this.towerSpawner.setSpawnerState(world, spawnerState);
    }
    public void setSpawnerEvent(World world, TowerSpawnerEvent spawnerEvent) {
        this.towerSpawner.setSpawnerEvent(world, spawnerEvent);
    }

    public void updateListeners() {
        this.towerSpawner.updateListeners();
    }

    public EntityDetector getEntityDetector() {
        return this.entityDetector;
    }

    public boolean canActivate(World world) {
        if (this.forceActivate) {
            return true;
        }
        if (world.getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        }
        return world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING);
    }

    public Optional<UUID> trySpawnMob(ServerWorld world, BlockPos pos) {
        double f;
        Random random = world.getRandom();
        MobSpawnerEntry mobSpawnerEntry = this.data.getSpawnData(this, world.getRandom());
        NbtCompound nbtCompound = mobSpawnerEntry.entity();
        NbtList nbtList = nbtCompound.getList("Pos", NbtElement.DOUBLE_TYPE);
        Optional<EntityType<?>> optional = EntityType.fromNbt(nbtCompound);
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        int i = nbtList.size();
        double d = i >= 1 ? nbtList.getDouble(0) : (double)pos.getX() + (random.nextDouble() - random.nextDouble()) * (double)this.config.spawnRange() + 0.5;
        double e = i >= 2 ? nbtList.getDouble(1) : (double)(pos.getY() + random.nextInt(3) - 1);
        double d2 = f = i >= 3 ? nbtList.getDouble(2) : (double)pos.getZ() + (random.nextDouble() - random.nextDouble()) * (double)this.config.spawnRange() + 0.5;
        if (!world.isSpaceEmpty(optional.get().createSimpleBoundingBox(d, e, f))) {
            return Optional.empty();
        }
        Vec3d vec3d = new Vec3d(d, e, f);
        Entity entity2 = EntityType.loadEntityWithPassengers(nbtCompound, world, entity -> {
            entity.refreshPositionAndAngles(d, e, f, random.nextFloat() * 360.0f, 0.0f);
            return entity;
        });

        if (!TowerSpawnerLogic.hasLineOfSight(world, pos.toCenterPos(), vec3d, entity2)) {
            return Optional.empty();
        }
        BlockPos blockPos = BlockPos.ofFloored(vec3d);
        //if (!SpawnRestriction.canSpawn(optional.get(), world, /* SpawnReason.TRIAL_SPAWNER */ SpawnReason.SPAWNER, blockPos, world.getRandom())) {
        //    System.out.println("canSpawn broke");
        //    return Optional.empty();
        //}

        if (entity2 == null) {
            return Optional.empty();
        }
        if (entity2 instanceof MobEntity) {
            MobEntity mobEntity = (MobEntity)entity2;
            if (!mobEntity.canSpawn(world)) {
                return Optional.empty();
            }
            if (mobSpawnerEntry.getNbt().getSize() == 1 && mobSpawnerEntry.getNbt().contains("id", NbtElement.STRING_TYPE)) {
                mobEntity.initialize(world, world.getLocalDifficulty(mobEntity.getBlockPos()), /* SpawnReason.TRIAL_SPAWNER */ SpawnReason.MOB_SUMMONED, null, null);
                //mobEntity.setPersistent();
            }
        }
        if (!world.spawnNewEntityAndPassengers(entity2)) {
            return Optional.empty();
        }

        TowerSpawnerEvent towerSpawnerEvent2;
        TowerSpawnerEvent towerSpawnerEvent = this.getSpawnerEvent();

        if ((towerSpawnerEvent2 = towerSpawnerEvent.syncMobSpawnParticles(blockPos, "mob_spawn")) != towerSpawnerEvent) {
            this.setSpawnerEvent(world, towerSpawnerEvent2);
        }

        world.emitGameEvent(entity2, GameEvent.ENTITY_PLACE, blockPos);
        return Optional.of(entity2.getUuid());
    }

    public void tickClient(World world, BlockPos pos) {
        Random random;

        TowerSpawnerEvent towerSpawnerEvent = this.getSpawnerEvent();
        TowerSpawnerState towerSpawnerState = this.getSpawnerState();

        if (!this.canActivate(world)) {
            this.data.lastDisplayEntityRotation = this.data.displayEntityRotation;
            return;
        }

        /*if (towerSpawnerEvent != TowerSpawnerEvent.NOTHING) {
            Random random1 = world.getRandom();

            switch (towerSpawnerEvent) {
                case MOB_SPAWN -> {
                    BlockPos blockPos = new BlockPos(towerSpawnerEvent.getPosX(), towerSpawnerEvent.getPosY(), towerSpawnerEvent.getPosZ());

                    TowerSpawnerLogic.addMobSpawnParticles(world, pos, random1);
                    world.playSoundAtBlockCenter(blockPos, SoundEvents.BLOCK_TRIAL_SPAWNER_SPAWN_MOB, SoundCategory.BLOCKS, 1.0f, (random1.nextFloat() - random1.nextFloat()) * 0.2f + 1.0f, true);
                    TowerSpawnerLogic.addMobSpawnParticles(world, blockPos, random1);
                }
                case ACTIVATING -> {
                    world.playSoundAtBlockCenter(pos, SoundEvents.BLOCK_TRIAL_SPAWNER_DETECT_PLAYER, SoundCategory.BLOCKS, 1.0f, (random1.nextFloat() - random1.nextFloat()) * 0.2f + 1.0f, true);
                    TowerSpawnerLogic.addDetectionParticles(world, pos, random1, data.players.size());
                }
            }

            TowerSpawnerEvent towerSpawnerEvent2 = towerSpawnerEvent.syncMobSpawnParticles(pos, "nothing");
            this.setSpawnerEvent(world, towerSpawnerEvent2);
        }*/

        if (towerSpawnerEvent == TowerSpawnerEvent.MOB_SPAWN) {
            Random random1 = world.getRandom();
            TowerSpawnerLogic.addMobSpawnParticles(world, pos, random1);

            BlockPos blockPos = new BlockPos(towerSpawnerEvent.getPosX(), towerSpawnerEvent.getPosY(), towerSpawnerEvent.getPosZ());
            world.playSoundAtBlockCenter(blockPos, /* SoundEvents.BLOCK_TRIAL_SPAWNER_SPAWN_MOB */ SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, (random1.nextFloat() - random1.nextFloat()) * 0.2f + 1.0f, true);
            TowerSpawnerLogic.addMobSpawnParticles(world, blockPos, random1);

            TowerSpawnerEvent towerSpawnerEvent2 = towerSpawnerEvent.syncMobSpawnParticles(blockPos, "nothing");
            this.setSpawnerEvent(world, towerSpawnerEvent2);
        }

        if (towerSpawnerState == TowerSpawnerState.ACTIVATING) {
            Random random1 = world.getRandom();
            world.playSoundAtBlockCenter(pos, /* SoundEvents.BLOCK_TRIAL_SPAWNER_DETECT_PLAYER */ SoundEvents.ENTITY_PIGLIN_ANGRY, SoundCategory.BLOCKS, 1.0f, (random1.nextFloat() - random1.nextFloat()) * 0.2f + 1.0f, true);
            TowerSpawnerLogic.addDetectionParticles(world, pos, random1, data.players.size());
        }


        towerSpawnerState.emitParticles(world, pos);



        if (towerSpawnerState.doesDisplayRotate()) {
            double d = Math.max(0L, this.data.nextMobSpawnsAt - world.getTime());
            this.data.lastDisplayEntityRotation = this.data.displayEntityRotation;
            this.data.displayEntityRotation = (this.data.displayEntityRotation + towerSpawnerState.getDisplayRotationSpeed() / (d + 200.0)) % 360.0;
        }
        if (towerSpawnerState.playsSound() && (random = world.getRandom()).nextFloat() <= 0.02f) {
            world.playSoundAtBlockCenter(pos, /* SoundEvents.BLOCK_TRIAL_SPAWNER_AMBIENT */ SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25f + 0.75f, random.nextFloat() + 0.5f, false);
        }
    }

    public void tickServer(ServerWorld world, BlockPos pos) {
        TowerSpawnerState towerSpawnerState2;
        TowerSpawnerState towerSpawnerState = this.getSpawnerState();

        if (!this.canActivate(world)) {
            if (towerSpawnerState.playsSound()) {
                this.data.reset();
                this.setSpawnerState(world, TowerSpawnerState.INACTIVE);
            }
            return;
        }
        if (this.data.spawnedMobsAlive.removeIf(uuid -> TowerSpawnerLogic.shouldRemoveMobFromData(world, pos, uuid))) {
            this.data.nextMobSpawnsAt = world.getTime() + (long)this.config.ticksBetweenSpawn();
        }
        if ((towerSpawnerState2 = towerSpawnerState.tick(pos, this, world)) != towerSpawnerState) {
            this.setSpawnerState(world, towerSpawnerState2);
        }
    }

    private static boolean shouldRemoveMobFromData(ServerWorld world, BlockPos pos, UUID uuid) {
        Entity entity = world.getEntity(uuid);
        return entity == null || !entity.isAlive() || !entity.getWorld().getRegistryKey().equals(world.getRegistryKey()) || entity.getBlockPos().getSquaredDistance(pos) > (double)MAX_ENTITY_DISTANCE_SQUARED;
    }

    private static boolean hasLineOfSight(World world, Vec3d spawnerPos, Vec3d spawnPos, Entity entity) {
        BlockHitResult blockHitResult = world.raycast(new RaycastContext(spawnPos, spawnerPos, RaycastContext.ShapeType.VISUAL, RaycastContext.FluidHandling.NONE, entity));
        return blockHitResult.getBlockPos().equals(BlockPos.ofFloored(spawnerPos)) || blockHitResult.getType() == HitResult.Type.MISS;
    }

    public static void addMobSpawnParticles(World world, BlockPos pos, Random random) {
        for (int i = 0; i < 20; ++i) {
            double d = (double)pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 2.0;
            double e = (double)pos.getY() + 0.5 + (random.nextDouble() - 0.5) * 2.0;
            double f = (double)pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 2.0;
            world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0, 0.0, 0.0);
            world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d, e, f, 0.0, 0.0, 0.0);
        }
    }

    public static void addDetectionParticles(World world, BlockPos pos, Random random, int playerCount) {
        for (int i = 0; i < 30 + Math.min(playerCount, 10) * 5; ++i) {
            double d = (double)(2.0f * random.nextFloat() - 1.0f) * 0.65;
            double e = (double)(2.0f * random.nextFloat() - 1.0f) * 0.65;
            double f = (double)pos.getX() + 0.5 + d;
            double g = (double)pos.getY() + 0.1 + (double)random.nextFloat() * 0.8;
            double h = (double)pos.getZ() + 0.5 + e;
            world.addParticle(ModParticles.TOWER_SPAWNER_DETECTION, f, g, h, 0.0, 0.0, 0.0);
        }
    }

    public interface TowerSpawner {
        public void setSpawnerState(World var1, TowerSpawnerState var2);
        public TowerSpawnerState getSpawnerState();

        public void setSpawnerEvent(World var1, TowerSpawnerEvent var2);
        public TowerSpawnerEvent getSpawnerEvent();

        public void updateListeners();
    }
}
