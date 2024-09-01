package net.eps.lonsbattletowers.particle;

import net.eps.lonsbattletowers.LonsBattleTowers;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static final DefaultParticleType TOWER_SPAWNER_DETECTION = FabricParticleTypes.simple();
    public static final DefaultParticleType TOWER_VAULT_CONNECTION = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(LonsBattleTowers.MOD_ID, "tower_spawner_detection"), TOWER_SPAWNER_DETECTION);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(LonsBattleTowers.MOD_ID, "tower_vault_connection"), TOWER_VAULT_CONNECTION);
    }
}
