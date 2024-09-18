package net.eps.lonsbattletowers.entity.client;


import net.eps.lonsbattletowers.entity.animation.TowerMimicAnimations;
import net.eps.lonsbattletowers.entity.custom.TowerMimicEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

import static net.eps.lonsbattletowers.entity.custom.TowerMimicEntity.*;

public class TowerMimicModel<T extends TowerMimicEntity> extends SinglePartEntityModel<T> {
	private boolean entityAlive = true;
	private boolean entitySpawn = false;

	private final ModelPart Body;
	private final ModelPart Head;
	public TowerMimicModel(ModelPart root) {
		this.Body = root.getChild("Body");
		this.Head = root.getChild("Body").getChild("Head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData Body = modelPartData.addChild("Body", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, 13.5F, -1.9F));
		ModelPartData Legs = Body.addChild("Legs", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 6.0F, -0.1745F, 0.0F, 0.0F));
		ModelPartData Leg_Pick = Legs.addChild("Leg_Pick", ModelPartBuilder.create(), ModelTransform.of(4.5F, -18.0F, -2.0F, -1.309F, 1.2217F, -1.5708F));
		ModelPartData Pick_Base = Leg_Pick.addChild("Pick_Base", ModelPartBuilder.create().uv(99, 9).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 27.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 29.0F, 0.0F, 0.0F, 0.5236F));
		ModelPartData Leg_Pick_Base_r1 = Pick_Base.addChild("Leg_Pick_Base_r1", ModelPartBuilder.create().uv(75, 0).cuboid(-9.0F, -9.0F, 0.0F, 17.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 27.0F, 0.0F, 3.1416F, 0.0F, 0.0F));
		ModelPartData Pick_Holder = Leg_Pick.addChild("Pick_Holder", ModelPartBuilder.create().uv(1, 0).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 29.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 29.0F, -1.5708F, 0.0F, -3.1416F));
		ModelPartData Pick_Connector = Leg_Pick.addChild("Pick_Connector", ModelPartBuilder.create().uv(4, 0).cuboid(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.2F, 28.8F, 0.7279F, -0.0506F, 0.4303F));
		ModelPartData Leg_Shovel = Legs.addChild("Leg_Shovel", ModelPartBuilder.create(), ModelTransform.of(3.5F, -17.0F, 1.0F, 0.5092F, 0.4155F, 0.175F));
		ModelPartData Shovel_Base = Leg_Shovel.addChild("Shovel_Base", ModelPartBuilder.create().uv(99, 9).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 27.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 29.0F));
		ModelPartData Leg_Pick_Base_r2 = Shovel_Base.addChild("Leg_Pick_Base_r2", ModelPartBuilder.create().uv(15, 0).cuboid(-6.0F, -9.0F, 0.0F, 11.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 27.0F, 0.0F, 3.1416F, 0.0F, 0.0F));
		ModelPartData Shovel_Holder = Leg_Shovel.addChild("Shovel_Holder", ModelPartBuilder.create().uv(1, 0).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 29.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 29.0F, -1.5708F, 0.0F, -3.1416F));
		ModelPartData Shovel_Connector = Leg_Shovel.addChild("Shovel_Connector", ModelPartBuilder.create().uv(4, 0).cuboid(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.2F, 28.8F, 0.7854F, 0.0F, 0.0F));
		ModelPartData Leg_Scythe = Legs.addChild("Leg_Scythe", ModelPartBuilder.create(), ModelTransform.of(-0.5F, -18.0F, -2.0F, -1.309F, -1.2217F, 1.5708F));
		ModelPartData Scythe_Base = Leg_Scythe.addChild("Scythe_Base", ModelPartBuilder.create().uv(99, 9).mirrored().cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 27.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 29.0F, 0.0F, 0.0F, -0.5236F));
		ModelPartData Leg_Pick_Base_r3 = Scythe_Base.addChild("Leg_Pick_Base_r3", ModelPartBuilder.create().uv(65, 0).mirrored().cuboid(-4.0F, -9.0F, 0.0F, 10.0F, 9.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.5F, 27.0F, 0.0F, 3.1416F, 0.0F, 0.0F));
		ModelPartData Scythe_Holder = Leg_Scythe.addChild("Scythe_Holder", ModelPartBuilder.create().uv(1, 0).mirrored().cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 29.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 29.0F, -1.5708F, 0.0F, 3.1416F));
		ModelPartData Scythe_Connector = Leg_Scythe.addChild("Scythe_Connector", ModelPartBuilder.create().uv(4, 0).mirrored().cuboid(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.2F, 28.8F, 0.7279F, 0.0506F, -0.4303F));
		ModelPartData Leg_Pitchfork = Legs.addChild("Leg_Pitchfork", ModelPartBuilder.create(), ModelTransform.of(0.5F, -17.0F, 1.0F, 0.5092F, -0.4155F, -0.175F));
		ModelPartData Pitchfork_Base = Leg_Pitchfork.addChild("Pitchfork_Base", ModelPartBuilder.create().uv(99, 9).mirrored().cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 27.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 29.0F));
		ModelPartData Leg_Pick_Base_r4 = Pitchfork_Base.addChild("Leg_Pick_Base_r4", ModelPartBuilder.create().uv(37, 0).mirrored().cuboid(-6.0F, -9.0F, 0.0F, 13.0F, 9.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-0.5F, 27.0F, 0.0F, 3.1416F, 0.0F, 0.0F));
		ModelPartData Pitchfork_Holder = Leg_Pitchfork.addChild("Pitchfork_Holder", ModelPartBuilder.create().uv(1, 0).mirrored().cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 29.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 29.0F, -1.5708F, 0.0F, 3.1416F));
		ModelPartData Pitchfork_Connector = Leg_Pitchfork.addChild("Pitchfork_Connector", ModelPartBuilder.create().uv(4, 0).mirrored().cuboid(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.2F, 28.8F, 0.7854F, 0.0F, 0.0F));
		ModelPartData Head = Body.addChild("Head", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -11.4F, 0.0F));
		ModelPartData Arms = Head.addChild("Arms", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, 11.4F, 0.0F));
		ModelPartData Right_Arm = Arms.addChild("Right_Arm", ModelPartBuilder.create(), ModelTransform.of(8.3171F, -12.0F, -3.3861F, 0.0F, -0.3491F, 0.0F));
		ModelPartData Right_Base = Right_Arm.addChild("Right_Base", ModelPartBuilder.create(), ModelTransform.pivot(-0.4548F, 0.8385F, 0.101F));
		ModelPartData Right_Base_r1 = Right_Base.addChild("Right_Base_r1", ModelPartBuilder.create().uv(-9, 0).mirrored().cuboid(-0.5F, 0.0F, -4.5F, 1.0F, 0.0F, 9.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.845F, -0.8385F, -3.899F, 0.0F, -0.5236F, 0.0F));
		ModelPartData Right_Blade = Right_Arm.addChild("Right_Blade", ModelPartBuilder.create(), ModelTransform.pivot(5.0522F, 0.0F, -7.4616F));
		ModelPartData Right_Blade_r1 = Right_Blade.addChild("Right_Blade_r1", ModelPartBuilder.create().uv(-13, 30).mirrored().cuboid(-1.9978F, 0.0F, -0.0272F, 2.0F, 0.0F, 13.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));
		ModelPartData Right_Connector = Right_Arm.addChild("Right_Connector", ModelPartBuilder.create(), ModelTransform.pivot(5.0903F, 0.0F, -8.0942F));
		ModelPartData Right_Connector_r1 = Right_Connector.addChild("Right_Connector_r1", ModelPartBuilder.create().uv(1, 0).mirrored().cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 1.5708F));
		ModelPartData Left_Arm = Arms.addChild("Left_Arm", ModelPartBuilder.create(), ModelTransform.of(-4.3171F, -12.0F, -3.3861F, 0.0F, 0.3491F, 0.0F));
		ModelPartData Left_Arm_Base = Left_Arm.addChild("Left_Arm_Base", ModelPartBuilder.create(), ModelTransform.pivot(0.4165F, 0.1835F, -0.1458F));
		ModelPartData Left_Arm_Base_r1 = Left_Arm_Base.addChild("Left_Arm_Base_r1", ModelPartBuilder.create().uv(-9, 0).cuboid(-0.5F, 0.0F, -4.5F, 1.0F, 0.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-2.8067F, -0.1835F, -3.6522F, 0.0F, 0.5236F, 0.0F));
		ModelPartData Left_Arm_Connector = Left_Arm.addChild("Left_Arm_Connector", ModelPartBuilder.create(), ModelTransform.pivot(-5.3394F, -0.2914F, -8.2654F));
		ModelPartData Left_Arm_Connector_r1 = Left_Arm_Connector.addChild("Left_Arm_Connector_r1", ModelPartBuilder.create().uv(1, 0).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.2491F, 0.2914F, 0.1713F, 0.0873F, 0.0F, -1.5708F));
		ModelPartData Left_Arm_Blade = Left_Arm.addChild("Left_Arm_Blade", ModelPartBuilder.create(), ModelTransform.pivot(-5.0903F, 0.0F, -7.498F));
		ModelPartData Left_Arm_Blade_r1 = Left_Arm_Blade.addChild("Left_Arm_Blade_r1", ModelPartBuilder.create().uv(-13, 30).cuboid(-0.9761F, 0.0F, -6.9404F, 2.0F, 0.0F, 13.0F, new Dilation(0.0F)), ModelTransform.of(5.5381F, 0.0F, -4.2636F, 0.0F, 2.0944F, 0.0F));
		ModelPartData Jaws = Head.addChild("Jaws", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, 11.4F, 0.0F));
		ModelPartData Lower_Jaw = Jaws.addChild("Lower_Jaw", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -8.0F, 10.0F));
		ModelPartData Lower_Jaw_Base = Lower_Jaw.addChild("Lower_Jaw_Base", ModelPartBuilder.create().uv(0, 125).cuboid(-8.0F, 5.0F, -8.0F, 16.0F, 3.0F, 0.0F, new Dilation(0.0F))
				.uv(48, 109).cuboid(8.0F, 5.0F, -8.0F, 0.0F, 3.0F, 16.0F, new Dilation(0.0F))
				.uv(48, 109).cuboid(-8.0F, 5.0F, -8.0F, 0.0F, 3.0F, 16.0F, new Dilation(0.0F))
				.uv(48, 125).cuboid(-8.0F, 5.0F, 8.0F, 16.0F, 3.0F, 0.0F, new Dilation(0.0F))
				.uv(0, 96).cuboid(-8.0F, 8.0F, -8.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F))
				.uv(16, 96).cuboid(-8.0F, 5.0F, -8.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -8.0F));
		ModelPartData Lower_Jaw_Teeth = Lower_Jaw.addChild("Lower_Jaw_Teeth", ModelPartBuilder.create().uv(90, 120).cuboid(-3.0F, 0.0F, -8.0F, 6.0F, 5.0F, 0.0F, new Dilation(0.0F))
				.uv(48, 58).cuboid(-8.0F, 0.0F, 8.0F, 16.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -8.0F));
		ModelPartData Lower_Jaw_Teeth_r1 = Lower_Jaw_Teeth.addChild("Lower_Jaw_Teeth_r1", ModelPartBuilder.create().uv(48, 55).cuboid(0.0F, -8.0F, -8.0F, 0.0F, 8.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, 5.0F, 0.0F, 0.0F, 0.0F, -0.2182F));
		ModelPartData Lower_Jaw_Teeth_r2 = Lower_Jaw_Teeth.addChild("Lower_Jaw_Teeth_r2", ModelPartBuilder.create().uv(80, 74).cuboid(0.0F, -5.0F, -8.0F, 0.0F, 5.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(8.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.2182F));
		ModelPartData Lower_Jaw_Teeth_r3 = Lower_Jaw_Teeth.addChild("Lower_Jaw_Teeth_r3", ModelPartBuilder.create().uv(64, 116).cuboid(-8.0F, -8.0F, 0.0F, 5.0F, 8.0F, 0.0F, new Dilation(0.0F))
				.uv(74, 116).cuboid(3.0F, -8.0F, 0.0F, 5.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 5.0F, -8.0F, 0.2182F, 0.0F, 0.0F));
		ModelPartData Upper_Jaw = Jaws.addChild("Upper_Jaw", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -8.0F, 10.0F));
		ModelPartData Upper_Jaw_Teeth = Upper_Jaw.addChild("Upper_Jaw_Teeth", ModelPartBuilder.create().uv(88, 114).cuboid(-5.0F, -1.2F, -8.0F, 10.0F, 6.0F, 0.0F, new Dilation(0.0F))
				.uv(64, 52).cuboid(-8.0F, -1.2F, 8.0F, 16.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.1F, -4.55F, -0.5236F, 0.0F, 0.0F));
		ModelPartData Upper_Jaw_Teeth_r1 = Upper_Jaw_Teeth.addChild("Upper_Jaw_Teeth_r1", ModelPartBuilder.create().uv(96, 38).cuboid(0.0F, 0.0F, -8.0F, 0.0F, 6.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, -1.2F, 0.0F, 0.0F, 0.0F, 0.2182F));
		ModelPartData Upper_Jaw_Teeth_r2 = Upper_Jaw_Teeth.addChild("Upper_Jaw_Teeth_r2", ModelPartBuilder.create().uv(96, 52).cuboid(0.0F, 0.0F, -8.0F, 0.0F, 8.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(8.0F, -1.2F, 0.0F, 0.0F, 0.0F, -0.2182F));
		ModelPartData Upper_Jaw_Teeth_r3 = Upper_Jaw_Teeth.addChild("Upper_Jaw_Teeth_r3", ModelPartBuilder.create().uv(116, 115).cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F, new Dilation(0.0F))
				.uv(116, 115).cuboid(11.5F, 0.0F, 0.0F, 3.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-6.5F, -1.2F, -8.0F, -0.2182F, 0.0F, 0.0F));
		ModelPartData Upper_Jaw_Base = Upper_Jaw.addChild("Upper_Jaw_Base", ModelPartBuilder.create().uv(16, 112).cuboid(-8.0F, -3.2F, -8.0F, 16.0F, 2.0F, 0.0F, new Dilation(0.0F))
				.uv(48, 96).cuboid(8.0F, -3.2F, -8.0F, 0.0F, 2.0F, 16.0F, new Dilation(0.0F))
				.uv(48, 96).cuboid(-8.0F, -3.2F, -8.0F, 0.0F, 2.0F, 16.0F, new Dilation(0.0F))
				.uv(48, 112).cuboid(-8.0F, -3.2F, 8.0F, 16.0F, 2.0F, 0.0F, new Dilation(0.0F))
				.uv(-16, 96).cuboid(-8.0F, -3.2F, -8.0F, 16.0F, 0.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.1F, -4.55F, -0.5236F, 0.0F, 0.0F));
		ModelPartData Barrel = Jaws.addChild("Barrel", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -13.2224F, 4.2846F));
		ModelPartData Barrel_r1 = Barrel.addChild("Barrel_r1", ModelPartBuilder.create().uv(0, 70).cuboid(-7.0F, -22.5F, 17.0F, 14.0F, 11.0F, 14.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.7224F, -29.2846F, -0.5236F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		int overlayUv = entityAlive ? overlay : OverlayTexture.DEFAULT_UV;
		Body.render(matrices, vertexConsumer, light, overlayUv, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return Body;
	}

	@Override
	public void setAngles(TowerMimicEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		entityAlive = entity.isAlive();
		entitySpawn = entity.spawnAnimationState.isRunning();

		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(headYaw, headPitch);
		//if (entitySpawn) {
		//	System.out.println("setSpawnAngles called");
		//	this.setSpawnAngles();
		//}

		this.animateMovement(TowerMimicAnimations.MIMIC_WALK, limbAngle, limbDistance, 2.2f, 2.5f);
		this.updateAnimation(entity.idleAnimationState, TowerMimicAnimations.MIMIC_IDLE, animationProgress);
		this.updateAnimation(entity.attackAnimationState, TowerMimicAnimations.MIMIC_ATTACK, animationProgress);
		this.updateAnimation(entity.landAnimationState, TowerMimicAnimations.MIMIC_LAND, animationProgress);

		this.updateAnimation(entity.jumpAnimationStartState, TowerMimicAnimations.MIMIC_JUMP_START, animationProgress);
		this.updateAnimation(entity.jumpAnimationLandState, TowerMimicAnimations.MIMIC_JUMP_LAND, animationProgress);

		this.updateAnimation(entity.shootAnimationStartState, TowerMimicAnimations.MIMIC_SHOOT_START, animationProgress);
		this.updateAnimation(entity.shootAnimationShootState, TowerMimicAnimations.MIMIC_SHOOT_SHOOT, animationProgress);
		this.updateAnimation(entity.shootAnimationEndState, TowerMimicAnimations.MIMIC_SHOOT_END, animationProgress);

		this.updateAnimation(entity.breakAnimationState, TowerMimicAnimations.BREAK, animationProgress, 1.4f);
		this.updateAnimation(entity.spawnAnimationState, TowerMimicAnimations.SPAWN, animationProgress);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -20.0F, 20.0F);

		this.Head.yaw = headYaw * 0.017453292F;
	}

	private void setSpawnAngles() {

		this.Head.setAngles(0,0,0);
		this.Body.setAngles(0,0,0);
	}
}