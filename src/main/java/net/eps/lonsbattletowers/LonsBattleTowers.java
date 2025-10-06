package net.eps.lonsbattletowers;

import net.eps.lonsbattletowers.block.ModBlocks;
import net.eps.lonsbattletowers.block.entity.ModBlockEntities;
import net.eps.lonsbattletowers.entity.ModEntities;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemEntity;
import net.eps.lonsbattletowers.entity.custom.TowerMimicEntity;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemPart;
import net.eps.lonsbattletowers.item.ModItems;
import net.eps.lonsbattletowers.loot.ModLootContextParameters;
import net.eps.lonsbattletowers.loot.ModLootContextTypes;
import net.eps.lonsbattletowers.loot.ModLootFunctionTypes;
import net.eps.lonsbattletowers.particle.ModParticles;
import net.eps.lonsbattletowers.render.block.TowerSpawnerBlockEntityRenderer;
import net.eps.lonsbattletowers.sounds.ModSounds;
import net.eps.lonsbattletowers.worldgen.structure.ModStructures;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LonsBattleTowers implements ModInitializer {
	public static final String MOD_ID = "lonsbattletowers";
	public static final boolean IS_OPTIONAL_MOD_LOADED = FabricLoader.getInstance().isModLoaded("lootr");
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		BlockEntityRendererFactories.register(ModBlockEntities.TOWER_SPAWNER_BLOCK_ENTITY, TowerSpawnerBlockEntityRenderer::new);
		//BlockEntityRendererFactories.register(ModBlockEntities.TOWER_VAULT_BLOCK_ENTITY, VaultBlockEntityRenderer::new);

		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();

		ModItems.registerModItems();

		ModParticles.registerParticles();
		ModSounds.registerModSounds();

		ModStructures.registerStructures();

		ModLootContextParameters.registerLootContextParameters();
		ModLootContextTypes.registerLootTypes();
		ModLootFunctionTypes.registerLootFunctions();

		FabricDefaultAttributeRegistry.register(ModEntities.TOWER_MIMIC, TowerMimicEntity.createTowerMimicAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.TOWER_GOLEM, TowerGolemEntity.createTowerGolemAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.TOWER_GOLEM_ARM_PART, TowerGolemEntity.createTowerGolemAttributes());
		//FabricDefaultAttributeRegistry.register(ModEntities.TOWER_GOLEM_BODY_PART, TowerGolemPart.createTowerGolemAttributes());
	}
}