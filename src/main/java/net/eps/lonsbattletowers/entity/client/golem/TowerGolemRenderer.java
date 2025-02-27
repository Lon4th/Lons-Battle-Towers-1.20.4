package net.eps.lonsbattletowers.entity.client.golem;

import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.entity.client.ModModelLayers;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TowerGolemRenderer extends
        //EntityRenderer<TowerGolemEntity>
        MobEntityRenderer<TowerGolemEntity, TowerGolemModel<TowerGolemEntity>>
{
    private static final Identifier TEXTURE = new Identifier(LonsBattleTowers.MOD_ID, "textures/entity/temp_texture.png");
    private final TowerGolemModel<TowerGolemEntity> model;

    public TowerGolemRenderer(EntityRendererFactory.Context context) {
        super(context
                , new TowerGolemModel<>(context.getPart(ModModelLayers.TOWER_GOLEM)), 0.5F
        );
        this.shadowRadius = 0.5F;
        this.model = new TowerGolemModel<>(context.getPart(ModModelLayers.TOWER_GOLEM));
    }

    @Override
    public Identifier getTexture(TowerGolemEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(TowerGolemEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
