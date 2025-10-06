package net.eps.lonsbattletowers.entity.client.golem;

import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.entity.client.ModModelLayers;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemArmPart;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemEntity;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemPart;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TowerGolemArmRenderer extends EntityRenderer</*TowerGolemArmPart*/TowerGolemPart> {
    private static final Identifier TEXTURE = new Identifier(LonsBattleTowers.MOD_ID, "textures/entity/temp_texture.png");
    private final TowerGolemArmModel</*TowerGolemArmPart*/TowerGolemPart> model;

    public TowerGolemArmRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
        this.model = new TowerGolemArmModel<>(context.getPart(ModModelLayers.TOWER_GOLEM));
    }

    @Override
    public Identifier getTexture(/*TowerGolemArmPart*/TowerGolemPart entity) {
        return TEXTURE;
    }

    @Override
    public void render(/*TowerGolemArmPart*/TowerGolemPart mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
