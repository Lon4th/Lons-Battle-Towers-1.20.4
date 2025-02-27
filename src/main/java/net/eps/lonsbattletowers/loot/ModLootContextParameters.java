package net.eps.lonsbattletowers.loot;

import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.Identifier;

public class ModLootContextParameters {

    public final static LootContextParameter<Float> BATTLE_TOWER_REWARD_CHANCE = register("battle_tower_reward_chance");

    public static void registerLootContextParameters() {
    }

    public static <T> LootContextParameter<T> register(String name) {
        return new LootContextParameter<>(new Identifier(name));
    }

}
