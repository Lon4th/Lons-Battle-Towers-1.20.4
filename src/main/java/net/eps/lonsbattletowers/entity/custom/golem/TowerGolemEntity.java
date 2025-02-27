package net.eps.lonsbattletowers.entity.custom.golem;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;

public class TowerGolemEntity extends MobEntity implements Monster {
    private final TowerGolemPart[] parts;
    private final TowerGolemPart arm;
    private final TowerGolemPart body;

    public TowerGolemEntity(EntityType<? extends TowerGolemEntity> entityType, World world) {
        super(entityType, world);
        this.arm = new TowerGolemPart(this, "arm", 1.0F, 1.0F);
        this.body = new TowerGolemPart(this, "body", 3.0F, 2.0F);
        this.parts = new TowerGolemPart[]{this.body, this.arm};
        this.setStepHeight(1.0F);
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    public TowerGolemPart[] getBodyParts() {
        return this.parts;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        System.out.println("body pos is " + this.body.getPos());
        System.out.println("body dimensions is " + this.body.getBoundingBox());
        System.out.println("body visibility dimensions is " + this.body.getVisibilityBoundingBox());
        System.out.println("body dimensions is " + this.body.getDimensions(this.body.getPose()));
        System.out.println("arm pos is " + this.arm.getPos());
        System.out.println("this pos is " + this.getPos());
        this.body.setPosition(this.getX() + 1, this.getY() + 2, this.getZ() - 1);
        this.arm.setPosition(this.getX() + 2, this.getY() + 1, this.getZ() - 3);

    }

    private void movePart(TowerGolemPart towerGolemParts, double dx, double dy, double dz) {
        towerGolemParts.setPosition(this.getX() + dx, this.getY() + dy, this.getZ() + dz);
    }

    public boolean damagePart(TowerGolemPart part, DamageSource source, float amount) {
            if (amount < 0.01F) {
                return false;
            } else {
                if (source.getAttacker() instanceof PlayerEntity) {
                    this.parentDamage(source, amount);
                }

                return true;
            }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return !this.getWorld().isClient ? this.damagePart(this.body, source, amount) : false;
    }

    protected boolean parentDamage(DamageSource source, float amount) {
        return super.damage(source, amount);
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        TowerGolemPart[] towerGolemParts = this.getBodyParts();

        for(int i = 0; i < towerGolemParts.length; ++i) {
            towerGolemParts[i].setId(i + packet.getId());
        }

    }

    public static DefaultAttributeContainer.Builder createTowerGolemAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40)
                .add(EntityAttributes.GENERIC_ARMOR, 10)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1);
    }


}
