package net.eps.lonsbattletowers.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.MathHelper;

public class TowerSpawnerDetectionParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    private static final int field_47460 = 8;

    protected TowerSpawnerDetectionParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, float scale, SpriteProvider spriteProvider) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.spriteProvider = spriteProvider;
        this.velocityMultiplier = 0.96f;
        this.gravityStrength = -0.1f;
        this.ascending = true;
        this.velocityX *= 0.0;
        this.velocityY *= 0.9;
        this.velocityZ *= 0.0;
        this.velocityX += velocityX;
        this.velocityY += velocityY;
        this.velocityZ += velocityZ;
        this.scale *= 0.75f * scale;
        this.maxAge = (int)(8.0f / MathHelper.nextBetween(this.random, 0.5f, 1.0f) * scale);
        this.maxAge = Math.max(this.maxAge, 1);
        this.setSpriteForAge(spriteProvider);
        this.collidesWithWorld = true;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getBrightness(float tint) {
        return 240;
    }

    @Override
    public BillboardParticle.Rotator getRotator() {
        return BillboardParticle.Rotator.Y_AND_W_ONLY;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(this.spriteProvider);
    }

    @Override
    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0f, 0.0f, 1.0f);
    }

    @Environment(value= EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new TowerSpawnerDetectionParticle(clientWorld, d, e, f, g, h, i, 1.5f, this.spriteProvider);
        }
    }
}
