package net.eps.lonsbattletowers.entity.client.golem;


import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemArmPart;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemEntity;
import net.eps.lonsbattletowers.entity.custom.golem.TowerGolemPart;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class TowerGolemArmModel<T extends /*TowerGolemArmPart*/TowerGolemPart> extends EntityModel<T> {
	private final ModelPart bb_main;

	public TowerGolemArmModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(/*TowerGolemArmPart*/TowerGolemPart entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
	}
}