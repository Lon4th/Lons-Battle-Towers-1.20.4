package net.eps.lonsbattletowers.block.custom.spawner;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.collection.DataPool;
import net.minecraft.world.MobSpawnerEntry;

public record TowerSpawnerConfig(int requiredPlayerRange, int spawnRange, float simultaneousMobs, float simultaneousMobsAddedPerPlayer, int ticksBetweenSpawn, int targetCooldownLength, DataPool<MobSpawnerEntry> spawnPotentialsDefinition) {
    public static TowerSpawnerConfig defaultInstance = new TowerSpawnerConfig(
            14,
            4,
            4.0f,
            1.0f,
            50,
            36000,
            DataPool.<MobSpawnerEntry>empty());
    public static MapCodec<TowerSpawnerConfig> codec = RecordCodecBuilder.mapCodec(instance -> instance.group(Codec.intRange(1, 128).optionalFieldOf("required_player_range",
            TowerSpawnerConfig.defaultInstance.requiredPlayerRange).forGetter(TowerSpawnerConfig::requiredPlayerRange),
            Codec.intRange(1, 128).optionalFieldOf("spawn_range", TowerSpawnerConfig.defaultInstance.spawnRange).forGetter(TowerSpawnerConfig::spawnRange),
            Codec.floatRange(0.0f, Float.MAX_VALUE).optionalFieldOf("simultaneous_mobs", Float.valueOf(TowerSpawnerConfig.defaultInstance.simultaneousMobs)).forGetter(TowerSpawnerConfig::simultaneousMobs),
            Codec.floatRange(0.0f, Float.MAX_VALUE).optionalFieldOf("simultaneous_mobs_added_per_player", Float.valueOf(TowerSpawnerConfig.defaultInstance.simultaneousMobsAddedPerPlayer)).forGetter(TowerSpawnerConfig::simultaneousMobsAddedPerPlayer),
            Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("ticks_between_spawn", TowerSpawnerConfig.defaultInstance.ticksBetweenSpawn).forGetter(TowerSpawnerConfig::ticksBetweenSpawn),
            Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("target_cooldown_length", TowerSpawnerConfig.defaultInstance.targetCooldownLength).forGetter(TowerSpawnerConfig::targetCooldownLength),
            MobSpawnerEntry.DATA_POOL_CODEC.optionalFieldOf("spawn_potentials", DataPool.<MobSpawnerEntry>empty()).forGetter(TowerSpawnerConfig::spawnPotentialsDefinition)).apply(instance, TowerSpawnerConfig::new));


    public int getSimultaneousMobs(int additionalPlayers) {
        return (int)Math.floor(this.simultaneousMobs + this.simultaneousMobsAddedPerPlayer * (float)additionalPlayers);
    }
}
