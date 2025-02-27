package net.eps.lonsbattletowers.entity.client.golem;


import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class TowerGolemModel<T extends TowerGolemEntity> extends EntityModel<T> {
	private final ModelPart bb_main;

	public TowerGolemModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 81).cuboid(-24.0F, -36.0F, 0.0F, 24.0F, 24.0F, 23.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(TowerGolemEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
	}
}