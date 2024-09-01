package net.eps.lonsbattletowers.block.custom.spawner;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public enum TowerSpawnerEvent implements StringIdentifiable {
    NOTHING("nothing", 0, 0, 0),
    MOB_SPAWN("mob_spawn", 0, 0, 0);

    private final String id;
    private int posX;
    private int posY;
    private int posZ;

    private TowerSpawnerEvent(String id, int posx, int posy, int posz) {
        this.id = id;
        this.posX = posx;
        this.posY = posy;
        this.posZ = posz;
    }

    TowerSpawnerEvent syncMobSpawnParticles(BlockPos pos, String entityId) {
        if (Objects.equals(entityId, MOB_SPAWN.id)) {
            this.posX = pos.getX();
            this.posY = pos.getY();
            this.posZ = pos.getZ();
            return switch (this) {
                case NOTHING -> MOB_SPAWN;
                case MOB_SPAWN -> this;
            };
        }
        return NOTHING;
    }

    @Override
    public String asString() {
        return this.id;
    }

    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }
    public int getPosZ() {
        return posZ;
    }
}
