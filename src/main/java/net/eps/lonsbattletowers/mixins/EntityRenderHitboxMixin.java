package net.eps.lonsbattletowers.mixins;

import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemArmPart;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemEntity;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderHitboxMixin {

    @Inject(method = "renderHitbox", at = @At(value = "HEAD"), cancellable = true)
    private static void renderHitboxMixin(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta, CallbackInfo ci) {
        if (entity instanceof TowerGolemPart part) {
            ci.cancel();
            Box box = part.getBoundingBox().offset(-part.getX(), -part.getY(), -part.getZ());
            if (part instanceof TowerGolemArmPart armPart && armPart.getSpawnedDegree() == TowerGolemEntity.FRONT_LEFT_ARM_DEGREE){
                WorldRenderer.drawBox(matrices, vertices, box/*.offset(towerGolemPart.getX() - towerGolemPart.prevX, towerGolemPart.getY() - towerGolemPart.prevY, towerGolemPart.getZ() - towerGolemPart.prevZ)*/, 0.25F, 0.1F, 1.0F, 1.0F);
            } else {
                WorldRenderer.drawBox(matrices, vertices, box/*.offset(towerGolemPart.getX() - towerGolemPart.prevX, towerGolemPart.getY() - towerGolemPart.prevY, towerGolemPart.getZ() - towerGolemPart.prevZ)*/, 0.25F, 1.0F, 0.0F, 1.0F);
            }

            ci.cancel();
        }
    }

}