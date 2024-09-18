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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
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
            render(f, matrixStack, vertexConsumerProvider, i, entity, this.entityRenderDispatcher, towerSpawnerData.getLastDisplayEntityRotation(), towerSpawnerData.getDisplayEntityRotation());
        }
    }

    public static void render(float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Entity entity, EntityRenderDispatcher entityRenderDispatcher, double lastRotation, double rotation) {
        matrices.push();
        matrices.translate(0.5F, 0.0F, 0.5F);
        float f = 0.53125F;
        float g = Math.max(entity.getWidth(), entity.getHeight());
        if ((double)g > 1.0) {
            f /= g;
        }

        matrices.translate(0.0F, 0.4F, 0.0F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) MathHelper.lerp((double)tickDelta, lastRotation, rotation) * 10.0F));
        matrices.translate(0.0F, -0.2F, 0.0F);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-30.0F));
        matrices.scale(f, f, f);
        entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, tickDelta, matrices, vertexConsumers, light);
        matrices.pop();
    }
}
