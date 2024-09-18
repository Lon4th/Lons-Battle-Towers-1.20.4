package net.eps.lonsbattletowers.block.entity;

import com.mojang.logging.LogUtils;
import net.eps.lonsbattletowers.block.custom.*;
import net.eps.lonsbattletowers.block.custom.spawner.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.slf4j.Logger;

public class TowerSpawnerBlockEntity extends BlockEntity implements TowerSpawner, TowerSpawnerLogic.TowerSpawner {
    private static final Logger LOGGER = LogUtils.getLogger();
    private TowerSpawnerLogic spawner;

    public TowerSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TOWER_SPAWNER_BLOCK_ENTITY, pos, state);
        EntityDetector entityDetector = EntityDetector.SURVIVAL_PLAYER;
        this.spawner = new TowerSpawnerLogic(this, entityDetector);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.spawner.codec().parse(NbtOps.INSTANCE, nbt).resultOrPartial(LOGGER::error).ifPresent(towerSpawnerLogic -> {
            this.spawner = towerSpawnerLogic;
        });
        if (this.world != null) {
            this.updateListeners();
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        this.spawner.codec().encodeStart(NbtOps.INSTANCE, this.spawner).get().ifLeft(nbtElement -> nbt.copyFrom((NbtCompound)nbtElement)).ifRight(partialResult -> LOGGER.warn("Failed to encode TowerSpawner {}", (Object)partialResult.message()));
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.spawner.getData().getSpawnDataNbt(this.getCachedState().get(TowerSpawnerBlock.TOWER_SPAWNER_STATE));
    }

    @Override
    public boolean copyItemDataRequiresOperator() {
        return true;
    }

    @Override
    public void setEntityType(EntityType<?> type, Random random) {
        this.spawner.getData().setEntityType(this.spawner, random, type);
        this.markDirty();
    }

    public TowerSpawnerLogic getSpawner() {
        return this.spawner;
    }

    @Override
    public TowerSpawnerState getSpawnerState() {
        if (!this.getCachedState().contains(TowerSpawnerBlock.TOWER_SPAWNER_STATE)) {
            return TowerSpawnerState.INACTIVE;
        }
        return this.getCachedState().get(TowerSpawnerBlock.TOWER_SPAWNER_STATE);
    }

    @Override
    public void setSpawnerEvent(World world, TowerSpawnerEvent spawnerEvent) {
        this.markDirty();
        world.setBlockState(this.pos, this.getCachedState().with(TowerSpawnerBlock.TOWER_SPAWNER_EVENT, spawnerEvent));
    }

    @Override
    public TowerSpawnerEvent getSpawnerEvent() {
        if (!this.getCachedState().contains(TowerSpawnerBlock.TOWER_SPAWNER_EVENT)) {
            return TowerSpawnerEvent.NOTHING;
        }
        return this.getCachedState().get(TowerSpawnerBlock.TOWER_SPAWNER_EVENT);
    }

    @Override
    public void setSpawnerState(World world, TowerSpawnerState spawnerState) {
        this.markDirty();
        world.setBlockState(this.pos, this.getCachedState().with(TowerSpawnerBlock.TOWER_SPAWNER_STATE, spawnerState));
    }

    @Override
    public void updateListeners() {
        this.markDirty();
        if (this.world != null) {
            this.world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
        }
    }
}
