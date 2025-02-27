package net.eps.lonsbattletowers.loot;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.eps.lonsbattletowers.LonsBattleTowers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.slf4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class BattleTowerRewardGenFunction extends ConditionalLootFunction {

    protected BattleTowerRewardGenFunction(LootCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context) {
        if (context.hasParameter(ModLootContextParameters.BATTLE_TOWER_REWARD_CHANCE)) {
            MinecraftServer server = context.getWorld().getServer();
            Random random = context.getRandom();
            float rewardChance = context.get(ModLootContextParameters.BATTLE_TOWER_REWARD_CHANCE);
            //System.out.println("FUNCTION's reward chance is " + rewardChance);


            /* Get The Type Of The Loot */
            Identifier lootId;
            if (random.nextFloat() < rewardChance) {
                lootId = new Identifier("lonsbattletowers", "chests/tower_chest/reward");
            } else {
                if (random.nextFloat() <= 0.8f) {
                    if (random.nextFloat() <= 0.75f) {
                        lootId = new Identifier("lonsbattletowers", "chests/tower_chest/junk");
                    } else {
                        lootId = new Identifier("chests/simple_dungeon");
                    }
                } else {
                    lootId = new Identifier("lonsbattletowers", "chests/tower_chest/food");
                }
            }
            LootTable lootTable = server.getLootManager().getLootTable(lootId);


            /* Set The Parameters */
            LootContextParameterSet.Builder builder = (new LootContextParameterSet.Builder(context.getWorld()))
                    .add(LootContextParameters.ORIGIN, context.get(LootContextParameters.ORIGIN));

            if (context.hasParameter(LootContextParameters.THIS_ENTITY)) {
                builder.add(LootContextParameters.THIS_ENTITY, context.get(LootContextParameters.THIS_ENTITY)).luck(context.getLuck());
            }

            LootContextParameterSet parameterSet = builder.build(ModLootContextTypes.CHEST);


            /* Set The Empty Slots */
            ItemStack item;
            if (random.nextFloat() > Math.max(rewardChance, 0.15f)) {
                item = ItemStack.EMPTY;
            } else {
                item = lootTable.generateLoot(parameterSet).get(0);
                if (item.isOf(Items.ENCHANTED_BOOK)) {
                    boolean bl = item.isOf(Items.ENCHANTED_BOOK);
                    ItemStack finalItem = item;
                    List<Enchantment> list = Registries.ENCHANTMENT.stream().filter(Enchantment::isAvailableForRandomSelection).filter((enchantmentx) -> {
                        return bl || enchantmentx.isAcceptableItem(finalItem);
                    }).toList();

                    if (list.isEmpty()) {
                        LonsBattleTowers.LOGGER.warn("Couldn't find a compatible enchantment for {}", finalItem);
                        return finalItem;
                    }

                    Enchantment enchantment = list.get(random.nextInt(list.size()));

                    //item = EnchantmentHelper.enchant(random, Items.BOOK.getDefaultStack(), 5, false);
                    item = addEnchantmentToStack(finalItem, enchantment, random);
                }
            }

            stack = item;
        }

        return stack;
    }

    private static ItemStack addEnchantmentToStack(ItemStack stack, Enchantment enchantment, Random random) {
        int i = MathHelper.nextInt(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
        if (stack.isOf(Items.ENCHANTED_BOOK)) {
            stack = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantedBookItem.addEnchantment(stack, new EnchantmentLevelEntry(enchantment, i));
        } else {
            stack.addEnchantment(enchantment, i);
        }

        return stack;
    }

    @Override
    public LootFunctionType getType() {
        return ModLootFunctionTypes.BATTLE_TOWER_REWARD_GEN;
    }

    public static ConditionalLootFunction.Builder<?> builder() {
        return builder(BattleTowerRewardGenFunction::new);
    }

    public static class Serializer extends ConditionalLootFunction.Serializer<BattleTowerRewardGenFunction> {
        public Serializer() {
        }

        public BattleTowerRewardGenFunction fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootCondition[] lootConditions) {
            return new BattleTowerRewardGenFunction(lootConditions);
        }
    }
}
