package net.eps.lonsbattletowers.sounds;

import net.eps.lonsbattletowers.LonsBattleTowers;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent MIMIC_AMBIENT = register("mimic_ambient");

    public static final SoundEvent MIMIC_SHOOT_SHOOT = register("mimic_shoot_shoot");
    public static final SoundEvent MIMIC_SHOOT_CREAK = register("mimic_shoot_creak");

    public static final SoundEvent MIMIC_BITE_BLADE = register("mimic_bite_blade");
    public static final SoundEvent MIMIC_BITE_BREATHE = register("mimic_bite_breathe");
    public static final SoundEvent MIMIC_BITE_SHAKE = register("mimic_bite_shake");
    public static final SoundEvent MIMIC_BITE_SHAKE_METALLIC = register("mimic_bite_shake_metallic");

    public static final SoundEvent MIMIC_SPAWN_HIT = register("mimic_spawn_hit");
    public static final SoundEvent MIMIC_SPAWN_BREAK = register("mimic_spawn_break");
    public static final SoundEvent MIMIC_SPAWN_SCREAM = register("mimic_spawn_scream");

    public static final SoundEvent MIMIC_DEATH_BREAK = register("mimic_death_break");
    public static final SoundEvent MIMIC_DEATH_FALL = register("mimic_death_fall");
    public static final SoundEvent MIMIC_DEATH_SHAKE = register("mimic_death_shake");

    public static final SoundEvent MIMIC_HURT_HIT = register("mimic_hurt_hit");
    public static final SoundEvent MIMIC_HURT_BREATHE = register("mimic_hurt_breathe");
    public static final SoundEvent MIMIC_HURT_SHAKE = register("mimic_hurt_shake");

    public static final SoundEvent MIMIC_STEP = register("mimic_step");

    public static final SoundEvent VAULT_ACTIVATE = register("vault_activate");
    public static final SoundEvent VAULT_DEACTIVATE = register("vault_deactivate");

    public static final SoundEvent VAULT_AMBIENT = register("vault_ambient");

    public static final SoundEvent VAULT_INSERT_FAIL = register("vault_insert_fail");
    public static final SoundEvent VAULT_REJECT_REWARDED_PLAYERS = register("vault_reject_rewarded_player");

    public static final SoundEvent VAULT_OPEN_SHUTTER = register("vault_open_shutter");
    public static final SoundEvent VAULT_CLOSE_SHUTTER = register("vault_close_shutter");

    public static final SoundEvent VAULT_INSERT_ITEM = register("vault_insert");
    public static final SoundEvent VAULT_EJECT_ITEM = register("vault_eject");

    private static SoundEvent register(String name) {
        Identifier id = new Identifier(LonsBattleTowers.MOD_ID, name);
        return (SoundEvent) Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSounds() {
        LonsBattleTowers.LOGGER.info("Registering ModSoundEvents for " + LonsBattleTowers.MOD_ID);
    }
}
