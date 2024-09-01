package net.eps.lonsbattletowers.block.custom.spawner;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.spawner.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.Uuids;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.collection.Weighted;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class TowerSpawnerData {
    public static final String SPAWN_DATA_KEY = "spawn_data";
    private static final String NEXT_MOB_SPAWNS_AT_KEY = "next_mob_spawns_at";
    public static MapCodec<TowerSpawnerData> codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Uuids.SET_CODEC.optionalFieldOf("registered_players", Sets.newHashSet()).forGetter(data -> data.players),
            Uuids.SET_CODEC.optionalFieldOf("current_mobs", Sets.newHashSet()).forGetter(data -> data.spawnedMobsAlive),
            Codec.LONG.optionalFieldOf("cooldown_ends_at", 0L).forGetter(data -> data.cooldownEnd),
            Codec.LONG.optionalFieldOf(NEXT_MOB_SPAWNS_AT_KEY, 0L).forGetter(data -> data.nextMobSpawnsAt),
            MobSpawnerEntry.CODEC.optionalFieldOf(SPAWN_DATA_KEY).forGetter(data -> data.spawnData)).apply(instance, TowerSpawnerData::new));

    protected final Set<UUID> players = new HashSet<UUID>();
    protected final Set<UUID> spawnedMobsAlive = new HashSet<UUID>();
    protected long cooldownEnd;
    protected long nextMobSpawnsAt;
    protected BlockPos mobSpawnPos;
    protected boolean mobSpawned;
    protected Optional<MobSpawnerEntry> spawnData;
    protected DataPool<MobSpawnerEntry> spawnDataPool;
    @Nullable
    protected Entity displayEntity;
    protected double displayEntityRotation;
    protected double lastDisplayEntityRotation;

    public TowerSpawnerData() {
        this(Collections.emptySet(), Collections.emptySet(), 0L, 0L, Optional.empty());
    }

    public TowerSpawnerData(Set<UUID> players, Set<UUID> spawnedMobsAlive, long cooldownEnd, long nextMobSpawnsAt, Optional<MobSpawnerEntry> spawnData) {
        this.players.addAll(players);
        this.spawnedMobsAlive.addAll(spawnedMobsAlive);
        this.cooldownEnd = cooldownEnd;
        this.nextMobSpawnsAt = nextMobSpawnsAt;
        this.spawnData = spawnData;
        this.mobSpawned = false;
    }

    public void populateSpawnDataPool(TowerSpawnerConfig config) {
        DataPool<MobSpawnerEntry> dataPool = config.spawnPotentialsDefinition();
        this.spawnDataPool = dataPool.isEmpty() ? DataPool.of(this.spawnData.orElseGet(MobSpawnerEntry::new)) : dataPool;
    }

    public boolean canSpawnMore(ServerWorld world, TowerSpawnerConfig config, int additionalPlayers) {
        return world.getTime() >= this.nextMobSpawnsAt && this.spawnedMobsAlive.size() < config.getSimultaneousMobs(additionalPlayers);
    }

    public void reset() {
        this.players.clear();
        this.nextMobSpawnsAt = 0L;
        this.cooldownEnd = 0L;
        this.spawnedMobsAlive.clear();
    }

    public boolean hasSpawnData() {
        boolean bl = this.spawnData.isPresent() && this.spawnData.get().getNbt().contains("id", NbtElement.STRING_TYPE);
        return bl || !this.spawnDataPool.isEmpty();
    }

    public int getAdditionalPlayers(BlockPos pos) {
        if (this.players.isEmpty()) {
            Util.error("Tower Spawner at " + pos + " has no detected players");
        }
        return Math.max(0, this.players.size() - 1);
    }

    public void updatePlayers(ServerWorld world, BlockPos pos, EntityDetector entityDetector, int range) {
        List<UUID> list = entityDetector.detect(world, pos, range);
        boolean bl = this.players.addAll(list);
        if (bl) {
            this.nextMobSpawnsAt = Math.max(world.getTime() + 40L, this.nextMobSpawnsAt);
            //world.syncWorldEvent(WorldEvents.TRIAL_SPAWNER_DETECTS_PLAYER, pos, this.players.size());
        }
    }

    /*public boolean hasPlayerGone(ServerWorld world, BlockPos pos, EntityDetector entityDetector, int range) {
        List<UUID> list = entityDetector.detect(world, pos, range);

        if (!this.players.isEmpty() && list.isEmpty()) {
            this.players.clear();
            return true;
        }
        return false;
    }*/

    public void setEntityType(TowerSpawnerLogic logic, Random random, EntityType<?> type) {
        this.getSpawnData(logic, random).getNbt().putString("id", Registries.ENTITY_TYPE.getId(type).toString());
    }

    protected MobSpawnerEntry getSpawnData(TowerSpawnerLogic logic, Random random) {
        if (this.spawnData.isPresent()) {
            return this.spawnData.get();
        }
        this.spawnData = Optional.of(this.spawnDataPool.getOrEmpty(random).map(Weighted.Present::getData).orElseGet(MobSpawnerEntry::new));
        logic.updateListeners();
        return this.spawnData.get();
    }

    @Nullable
    public Entity setDisplayEntity(TowerSpawnerLogic logic, World world, TowerSpawnerState state) {
        NbtCompound nbtCompound;
        if (!logic.canActivate(world) || !state.doesDisplayRotate()) {
            return null;
        }
        if (this.displayEntity == null && (nbtCompound = this.getSpawnData(logic, world.getRandom()).getNbt()).contains("id", NbtElement.STRING_TYPE)) {
            this.displayEntity = EntityType.loadEntityWithPassengers(nbtCompound, world, Function.identity());
        }
        return this.displayEntity;
    }

    public NbtCompound getSpawnDataNbt(TowerSpawnerState state) {
        NbtCompound nbtCompound = new NbtCompound();
        if (state == TowerSpawnerState.ACTIVE) {
            nbtCompound.putLong(NEXT_MOB_SPAWNS_AT_KEY, this.nextMobSpawnsAt);
        }
        this.spawnData.ifPresent(spawnData -> nbtCompound.put(SPAWN_DATA_KEY, MobSpawnerEntry.CODEC.encodeStart(NbtOps.INSTANCE, (MobSpawnerEntry)spawnData).result().orElseThrow(() -> new IllegalStateException("Invalid SpawnData"))));
        return nbtCompound;
    }

    public double getDisplayEntityRotation() {
        return this.displayEntityRotation;
    }

    public double getLastDisplayEntityRotation() {
        return this.lastDisplayEntityRotation;
    }
}
