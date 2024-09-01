package net.eps.lonsbattletowers.block.entity;

import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.block.ModBlocks;
import net.eps.lonsbattletowers.block.custom.TowerSpawnerBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.eps.lonsbattletowers.block.ModBlocks.*;

public class ModBlockEntities {
    public static final BlockEntityType<TowerSpawnerBlockEntity> TOWER_SPAWNER_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(LonsBattleTowers.MOD_ID, "tower_spawner_block_entity"), BlockEntityType.Builder.create(TowerSpawnerBlockEntity::new, TOWER_SPAWNER).build(null));
    public static final BlockEntityType<TowerVaultBlockEntity> TOWER_VAULT_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(LonsBattleTowers.MOD_ID, "tower_vault_block_entity"), BlockEntityType.Builder.create(TowerVaultBlockEntity::new, TOWER_VAULT).build(null));

    public static void registerBlockEntities() {
        LonsBattleTowers.LOGGER.info("Registering Block Entities");
    }
}
