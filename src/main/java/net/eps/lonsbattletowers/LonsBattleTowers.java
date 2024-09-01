package net.eps.lonsbattletowers;

import net.eps.lonsbattletowers.block.ModBlocks;
import net.eps.lonsbattletowers.block.entity.ModBlockEntities;
import net.eps.lonsbattletowers.entity.ModEntities;
import net.eps.lonsbattletowers.entity.custom.TowerMimicEntity;
import net.eps.lonsbattletowers.particle.ModParticles;
import net.eps.lonsbattletowers.render.block.TowerSpawnerBlockEntityRenderer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LonsBattleTowers implements ModInitializer {
	public static final String MOD_ID = "lonsbattletowers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		BlockEntityRendererFactories.register(ModBlockEntities.TOWER_SPAWNER_BLOCK_ENTITY, TowerSpawnerBlockEntityRenderer::new);
		//BlockEntityRendererFactories.register(ModBlockEntities.TOWER_VAULT_BLOCK_ENTITY, VaultBlockEntityRenderer::new);

		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();

		ModParticles.registerParticles();

		FabricDefaultAttributeRegistry.register(ModEntities.TOWER_MIMIC, TowerMimicEntity.createTowerMimicAttributes() );
	}
}