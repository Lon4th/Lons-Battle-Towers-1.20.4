package net.eps.lonsbattletowers;

import net.eps.lonsbattletowers.block.entity.ModBlockEntities;
import net.eps.lonsbattletowers.block.entity.TowerSpawnerBlockEntity;
import net.eps.lonsbattletowers.entity.ModEntities;
import net.eps.lonsbattletowers.entity.client.ModModelLayers;
import net.eps.lonsbattletowers.entity.client.TowerMimicModel;
import net.eps.lonsbattletowers.entity.client.TowerMimicRenderer;
import net.eps.lonsbattletowers.particle.ModParticles;
import net.eps.lonsbattletowers.particle.custom.TowerSpawnerDetectionParticle;
import net.eps.lonsbattletowers.particle.custom.TowerVaultConnectionParticle;
import net.eps.lonsbattletowers.render.block.TowerSpawnerBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.MobSpawnerBlockEntityRenderer;
import net.minecraft.client.render.block.entity.TrialSpawnerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;

import static net.eps.lonsbattletowers.block.ModBlocks.*;

public class LonsBattleTowersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), TOWER_SPAWNER);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), TOWER_VAULT);

        ParticleFactoryRegistry.getInstance().register(ModParticles.TOWER_SPAWNER_DETECTION, TowerSpawnerDetectionParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.TOWER_VAULT_CONNECTION, TowerVaultConnectionParticle.Factory::new);

        EntityRendererRegistry.register(ModEntities.TOWER_MIMIC, TowerMimicRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.TOWER_MIMIC, TowerMimicModel::getTexturedModelData);
    }
}
