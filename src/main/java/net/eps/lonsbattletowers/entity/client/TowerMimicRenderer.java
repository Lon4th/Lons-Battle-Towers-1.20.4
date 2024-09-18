package net.eps.lonsbattletowers.entity.client;

import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.entity.custom.TowerMimicEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class TowerMimicRenderer extends MobEntityRenderer<TowerMimicEntity, TowerMimicModel<TowerMimicEntity>> {
    private static final Identifier TEXTURE = new Identifier(LonsBattleTowers.MOD_ID, "textures/entity/tower_mimic.png");
    private static final Identifier TEXTURE_OPEN = new Identifier(LonsBattleTowers.MOD_ID, "textures/entity/tower_mimic_top_open.png");

    public TowerMimicRenderer(EntityRendererFactory.Context context) {
        super(context, new TowerMimicModel<>(context.getPart(ModModelLayers.TOWER_MIMIC)), 0.6f);
    }

    @Override
    public Identifier getTexture(TowerMimicEntity entity) {
        //if (entity.getTopOpen()) {
        //    return TEXTURE_OPEN;
        //}
        return TEXTURE;
    }

    @Override
    protected float getLyingAngle(TowerMimicEntity entity) {
        return 0F;
    }

    @Override
    public void render(TowerMimicEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
