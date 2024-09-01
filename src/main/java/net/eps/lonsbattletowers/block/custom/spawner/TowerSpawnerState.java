package net.eps.lonsbattletowers.block.custom.spawner;

import net.minecraft.block.spawner.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Optional;

public enum TowerSpawnerState implements StringIdentifiable {
    INACTIVE("inactive", 0, ParticleEmitter.NONE, -1.0, false),
    WAITING_FOR_PLAYERS("waiting_for_players", 4, ParticleEmitter.WAITING, 200.0, true),
    ACTIVE("active", 8, ParticleEmitter.ACTIVE, 1000.0, true),
    ACTIVATING("activating", 8, ParticleEmitter.ACTIVE, 1000.0, true);

    private final String id;
    private final int luminance;
    private final double displayRotationSpeed;
    private final ParticleEmitter particleEmitter;
    private final boolean playsSound;

    private TowerSpawnerState(String id, int luminance, ParticleEmitter particleEmitter, double displayRotationSpeed, boolean playsSound) {
        this.id = id;
        this.luminance = luminance;
        this.particleEmitter = particleEmitter;
        this.displayRotationSpeed = displayRotationSpeed;
        this.playsSound = playsSound;
    }

    TowerSpawnerState tick(BlockPos pos, TowerSpawnerLogic logic, ServerWorld world) {
        TowerSpawnerData towerSpawnerData = logic.getData();
        TowerSpawnerConfig towerSpawnerConfig = logic.getConfig();
        EntityDetector entityDetector = logic.getEntityDetector();
        return switch (this) {
            default -> throw new IncompatibleClassChangeError();
            case INACTIVE -> {
                if (towerSpawnerData.setDisplayEntity(logic, world, WAITING_FOR_PLAYERS) == null) {
                    yield this;
                }
                yield WAITING_FOR_PLAYERS;
            }
            case ACTIVE -> {
                if (!towerSpawnerData.hasSpawnData()) {
                    yield INACTIVE;
                }

                int i = towerSpawnerData.getAdditionalPlayers(pos);
                if (towerSpawnerData.canSpawnMore(world, towerSpawnerConfig, i)) {
                    logic.trySpawnMob(world, pos).ifPresent(uuid -> {
                        towerSpawnerData.spawnedMobsAlive.add(uuid);
                        towerSpawnerData.nextMobSpawnsAt = world.getTime() + (long) towerSpawnerConfig.ticksBetweenSpawn();
                        towerSpawnerData.spawnDataPool.getOrEmpty(world.getRandom()).ifPresent(spawnData -> {
                            towerSpawnerData.spawnData = Optional.of(spawnData.getData());
                            logic.updateListeners();
                        }); });
                }

                yield this;
            }
            case WAITING_FOR_PLAYERS -> {
                if (!towerSpawnerData.hasSpawnData()) {
                    yield INACTIVE;
                }
                towerSpawnerData.updatePlayers(world, pos, entityDetector, towerSpawnerConfig.requiredPlayerRange());
                if (towerSpawnerData.players.isEmpty()) {
                    yield this;
                }
                yield ACTIVATING;
            }
            case ACTIVATING -> {
                if (!towerSpawnerData.hasSpawnData()) {
                    yield INACTIVE;
                }

                int i = towerSpawnerData.getAdditionalPlayers(pos);
                if (towerSpawnerData.canSpawnMore(world, towerSpawnerConfig, i)) {
                    logic.trySpawnMob(world, pos).ifPresent(uuid -> {
                        towerSpawnerData.spawnedMobsAlive.add(uuid);
                        towerSpawnerData.nextMobSpawnsAt = world.getTime() + (long) towerSpawnerConfig.ticksBetweenSpawn();
                        towerSpawnerData.spawnDataPool.getOrEmpty(world.getRandom()).ifPresent(spawnData -> {
                            towerSpawnerData.spawnData = Optional.of(spawnData.getData());
                            logic.updateListeners();
                        }); });
                }

                yield ACTIVE;
            }
        };
    }

    @Override
    public String asString() {
        return this.id;
    }

    public int getLuminance() {
        return this.luminance;
    }

    public double getDisplayRotationSpeed() {
        return this.displayRotationSpeed;
    }

    public boolean doesDisplayRotate() {
        return this.displayRotationSpeed >= 0.0;
    }

    public boolean playsSound() {
        return this.playsSound;
    }

    public void emitParticles(World world, BlockPos pos) {
        this.particleEmitter.emit(world, world.getRandom(), pos);
    }

    static interface ParticleEmitter {
        public static final ParticleEmitter NONE = (world, random, pos) -> {};
        public static final ParticleEmitter WAITING = (world, random, pos) -> {
            if (random.nextInt(2) == 0) {
                Vec3d vec3d = pos.toCenterPos().addRandom(random, 0.9f);
                ParticleEmitter.emitParticle(ParticleTypes.SMALL_FLAME, vec3d, world);
            }
        };
        public static final ParticleEmitter ACTIVE = (world, random, pos) -> {
            Vec3d vec3d = pos.toCenterPos().addRandom(random, 1.0f);
            ParticleEmitter.emitParticle(ParticleTypes.SMOKE, vec3d, world);
            ParticleEmitter.emitParticle(ParticleTypes.SOUL_FIRE_FLAME, vec3d, world);
        };

        private static void emitParticle(DefaultParticleType type, Vec3d pos, World world) {
            world.addParticle(type, pos.getX(), pos.getY(), pos.getZ(), 0.0, 0.0, 0.0);
        }

        public void emit(World var1, Random var2, BlockPos var3);
    }

    static class Luminance {
        private static final int NONE = 0;
        private static final int LOW = 4;
        private static final int HIGH = 8;

        private Luminance() {
        }
    }

    static class DisplayRotationSpeed {
        private static final double NONE = -1.0;
        private static final double SLOW = 200.0;
        private static final double FAST = 1000.0;

        private DisplayRotationSpeed() {
        }
    }
}
