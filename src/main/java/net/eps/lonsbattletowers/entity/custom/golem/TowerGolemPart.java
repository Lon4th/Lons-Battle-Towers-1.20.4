package net.eps.lonsbattletowers.entity.custom.golem;

import net.eps.lonsbattletowers.entity.custom.TowerMimicEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class TowerGolemPart extends Entity {
    private static final TrackedData<Optional<UUID>> OWNER = DataTracker.registerData(TowerGolemPart.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<Float> PART_WIDTH = DataTracker.registerData(TowerGolemPart.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> PART_HEIGHT = DataTracker.registerData(TowerGolemPart.class, TrackedDataHandlerRegistry.FLOAT);
    public final TowerGolemEntity owner;
    public final String name;
    private final EntityDimensions partDimensions;

    public TowerGolemPart(TowerGolemEntity owner, String name, float width, float height) {
        super(owner.getType(), owner.getWorld());
        this.partDimensions = EntityDimensions.fixed(width, height);
        this.calculateDimensions();
        this.owner = owner;
        this.name = name;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(OWNER, Optional.empty());
        this.dataTracker.startTracking(PART_WIDTH, 0.5f);
        this.dataTracker.startTracking(PART_HEIGHT, 0.5f);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    @Nullable
    public ItemStack getPickBlockStack() {
        return this.owner.getPickBlockStack();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return this.isInvulnerableTo(source) ? false : this.owner.damagePart(this, source, amount);
    }

    @Override
    public boolean isPartOf(Entity entity) {
        return this == entity || this.owner == entity;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        //super.getDimensions(pose);
        return new EntityDimensions(this.dataTracker.get(PART_WIDTH), this.dataTracker.get(PART_HEIGHT), false);
    }

    @Override
    public boolean shouldSave() {
        return false;
    }
}
