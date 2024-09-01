package net.eps.lonsbattletowers.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class TowerVaultConnectionParticle extends SpriteBillboardParticle {
    private final double startX;
    private final double startY;
    private final double startZ;
    private final boolean fullBrightness;
    private final TowerVaultConnectionParticle.DynamicAlpha dynamicAlpha;

    TowerVaultConnectionParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, boolean fullBrightness, TowerVaultConnectionParticle.DynamicAlpha dynamicAlpha) {
        super(world, x, y, z);
        this.fullBrightness = fullBrightness;
        this.dynamicAlpha = dynamicAlpha;
        this.setAlpha(dynamicAlpha.startAlpha());
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.startX = x;
        this.startY = y;
        this.startZ = z;
        this.prevPosX = x + velocityX;
        this.prevPosY = y + velocityY;
        this.prevPosZ = z + velocityZ;
        this.x = this.prevPosX;
        this.y = this.prevPosY;
        this.z = this.prevPosZ;
        this.scale = 0.1F * (this.random.nextFloat() * 0.5F + 0.2F);
        float f = this.random.nextFloat() * 0.6F + 0.4F;
        this.red = 0.9F * f;
        this.green = 0.9F * f;
        this.blue = f;
        this.collidesWithWorld = false;
        this.maxAge = (int)(Math.random() * 10.0) + 30;
    }

    @Environment(EnvType.CLIENT)
    public record DynamicAlpha(float startAlpha, float endAlpha, float startAtNormalizedAge, float endAtNormalizedAge) {
        public static final TowerVaultConnectionParticle.DynamicAlpha OPAQUE = new TowerVaultConnectionParticle.DynamicAlpha(1.0F, 1.0F, 0.0F, 1.0F);

        public boolean isOpaque() {
            return this.startAlpha >= 1.0F && this.endAlpha >= 1.0F;
        }

        public float getAlpha(int age, int maxAge, float tickDelta) {
            if (MathHelper.approximatelyEquals(this.startAlpha, this.endAlpha)) {
                return this.startAlpha;
            } else {
                float f = MathHelper.getLerpProgress(((float)age + tickDelta) / (float)maxAge, this.startAtNormalizedAge, this.endAtNormalizedAge);
                return MathHelper.clampedLerp(this.startAlpha, this.endAlpha, f);
            }
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return this.dynamicAlpha.isOpaque() ? ParticleTextureSheet.PARTICLE_SHEET_OPAQUE : ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
        this.repositionFromBoundingBox();
    }

    @Override
    public int getBrightness(float tint) {
        if (this.fullBrightness) {
            return 240;
        } else {
            int i = super.getBrightness(tint);
            float f = (float)this.age / (float)this.maxAge;
            f *= f;
            f *= f;
            int j = i & 0xFF;
            int k = i >> 16 & 0xFF;
            k += (int)(f * 15.0F * 16.0F);
            if (k > 240) {
                k = 240;
            }

            return j | k << 16;
        }
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            float f = (float)this.age / (float)this.maxAge;
            f = 1.0F - f;
            float g = 1.0F - f;
            g *= g;
            g *= g;
            this.x = this.startX + this.velocityX * (double)f;
            this.y = this.startY + this.velocityY * (double)f - (double)(g * 1.2F);
            this.z = this.startZ + this.velocityZ * (double)f;
        }
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        this.setAlpha(this.dynamicAlpha.getAlpha(this.age, this.maxAge, tickDelta));
        super.buildGeometry(vertexConsumer, camera, tickDelta);
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            TowerVaultConnectionParticle connectionParticle = new TowerVaultConnectionParticle(clientWorld, d, e, f, g, h, i, true, new TowerVaultConnectionParticle.DynamicAlpha(0.0F, 0.6F, 0.25F, 1.0F));
            connectionParticle.scale(0.45F);
            connectionParticle.setSprite(this.spriteProvider);
            return connectionParticle;
        }
    }

}
