package net.eps.lonsbattletowers.block.custom.spawner;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface TowerSpawnerEvents {

    public void syncSpawnerEvent(@Nullable PlayerEntity var1, String var2, BlockPos var3, int var4);

    default public void syncSpawnerEvent(String eventId, BlockPos pos, int data) {
        this.syncSpawnerEvent(null, eventId, pos, data);
    }

}
