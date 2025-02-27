package net.eps.lonsbattletowers.entity.client.mimic;

import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.entity.custom.TowerMimicEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TowerMimicEyesFeatureRenderer<T extends TowerMimicEntity, M extends TowerMimicModel<T>> extends FeatureRenderer<T, M> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(LonsBattleTowers.MOD_ID, "textures/entity/tower_mimic_eyes.png"));

    public TowerMimicEyesFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        VertexConsumer vertex = vertexConsumers.getBuffer(SKIN);

        this.getContextModel().render(matrices, vertex, 15728640, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, 1F);
    }
}
