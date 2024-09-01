package net.eps.lonsbattletowers.entity;

import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.entity.custom.TowerMimicEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<TowerMimicEntity> TOWER_MIMIC = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(LonsBattleTowers.MOD_ID, "tower_mimic"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TowerMimicEntity::new).dimensions(EntityDimensions.fixed(0.9f, 2.2f)).build());
}
