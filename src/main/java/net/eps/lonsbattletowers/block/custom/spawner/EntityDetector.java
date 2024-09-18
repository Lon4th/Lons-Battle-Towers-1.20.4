package net.eps.lonsbattletowers.block.custom.spawner;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;
import java.util.UUID;

public interface EntityDetector {
    EntityDetector SURVIVAL_PLAYER = (world, center, radius) -> {
        return world.getPlayers((player) -> {
            return player.getBlockPos().isWithinDistance(center, (double)radius) && !player.isCreative() && !player.isSpectator();
        }).stream().map(Entity::getUuid).toList();
    };

    List<UUID> detect(ServerWorld world, BlockPos center, int radius);
}
