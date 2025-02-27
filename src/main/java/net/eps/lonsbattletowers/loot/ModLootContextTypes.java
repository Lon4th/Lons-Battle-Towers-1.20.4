package net.eps.lonsbattletowers.loot;

import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModLootContextTypes extends LootContextTypes {

    public static final LootContextType TOWER_CHEST = register("tower_chest", (builder) -> {
        builder.require(LootContextParameters.ORIGIN).allow(LootContextParameters.THIS_ENTITY).allow(ModLootContextParameters.BATTLE_TOWER_REWARD_CHANCE);
    });

    public static void registerLootTypes() {
    }
}
