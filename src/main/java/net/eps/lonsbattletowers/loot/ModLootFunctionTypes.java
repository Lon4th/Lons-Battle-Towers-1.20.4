package net.eps.lonsbattletowers.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.eps.lonsbattletowers.LonsBattleTowers;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.function.SetDamageLootFunction;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonSerializer;

public class ModLootFunctionTypes {

    public static final LootFunctionType BATTLE_TOWER_REWARD_GEN = register("battle_tower_reward_gen", new BattleTowerRewardGenFunction.Serializer());

    private static LootFunctionType register(String id, JsonSerializer<? extends LootFunction> jsonSerializer) {
        return (LootFunctionType) Registry.register(Registries.LOOT_FUNCTION_TYPE, new Identifier(LonsBattleTowers.MOD_ID, id), new LootFunctionType(jsonSerializer));
    }

    public static void registerLootFunctions() {
    }

}
