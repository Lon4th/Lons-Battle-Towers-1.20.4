package net.eps.lonsbattletowers.render.block;

import net.eps.lonsbattletowers.block.custom.spawner.TowerSpawnerData;
import net.eps.lonsbattletowers.block.custom.spawner.TowerSpawnerLogic;
import net.eps.lonsbattletowers.block.entity.TowerSpawnerBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.MobSpawnerBlockEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class TowerSpawnerBlockEntityRenderer implements BlockEntityRenderer<TowerSpawnerBlockEntity> {
    private final EntityRenderDispatcher entityRenderDispatcher;

    public TowerSpawnerBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.entityRenderDispatcher = context.getEntityRenderDispatcher();
    }

    @Override
    public void render(TowerSpawnerBlockEntity towerSpawnerBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        World world = towerSpawnerBlockEntity.getWorld();
        if (world == null) {
            return;
        }
        TowerSpawnerLogic towerSpawnerLogic = towerSpawnerBlockEntity.getSpawner();
        TowerSpawnerData towerSpawnerData = towerSpawnerLogic.getData();
        Entity entity = towerSpawnerData.setDisplayEntity(towerSpawnerLogic, world, towerSpawnerLogic.getSpawnerState());
        if (entity != null) {
            MobSpawnerBlockEntityRenderer.render(f, matrixStack, vertexConsumerProvider, i, entity, this.entityRenderDispatcher, towerSpawnerData.getLastDisplayEntityRotation(), towerSpawnerData.getDisplayEntityRotation());
        }
    }
}
