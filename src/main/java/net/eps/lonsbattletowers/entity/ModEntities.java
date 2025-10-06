package net.eps.lonsbattletowers.entity;

import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemArmPart;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemEntity;
import net.eps.lonsbattletowers.entity.custom.TowerMimicEntity;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemPart;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<TowerMimicEntity> TOWER_MIMIC = register("tower_mimic", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TowerMimicEntity::new).dimensions(EntityDimensions.fixed(0.9f, 2.2f)));

    public static final EntityType<TowerGolemEntity> TOWER_GOLEM = register("tower_golem", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TowerGolemEntity::new).fireImmune().dimensions(EntityDimensions.fixed(3.9F, 4.0F))/*.trackedUpdateRate(2).trackRangeChunks(10)*/);
    //public static final EntityType<TowerGolemPart> TOWER_GOLEM_PART = register("tower_golem_part", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, (type, world) -> new TowerGolemPart(type, null, "", 0, 0)).fireImmune());
    // Temporarily signed on TowerGolemPart, but not TowerGolemArmPart \/ \/ \/
    public static final EntityType<TowerGolemArmPart> TOWER_GOLEM_ARM_PART = register("tower_golem_arm_part", FabricEntityTypeBuilder.<TowerGolemArmPart>create(SpawnGroup.MISC, TowerGolemArmPart::new).dimensions(EntityDimensions.changing(1.0f, 1.0f)).fireImmune());
    //public static final EntityType<TowerGolemArmPart> TOWER_GOLEM_BODY_PART = register("tower_golem_body_part", FabricEntityTypeBuilder.<TowerGolemArmPart>create(SpawnGroup.MISC, TowerGolemArmPart::new).dimensions(EntityDimensions.changing(4.0f, 4.0f)).fireImmune());

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(LonsBattleTowers.MOD_ID, id), type.build());
    }
}
