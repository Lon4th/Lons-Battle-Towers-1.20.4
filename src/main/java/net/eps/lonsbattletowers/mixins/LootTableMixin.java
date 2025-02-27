package net.eps.lonsbattletowers.mixins;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LootTable.class)
public abstract class LootTableMixin {

    @Shadow @Final @Nullable private Identifier randomSequenceId;

    @Shadow protected abstract ObjectArrayList<ItemStack> generateLoot(LootContext context);

    @Inject(method = "supplyInventory", at = @At(value = "HEAD"), cancellable = true)
    public void supplyInventoryMixin(Inventory inventory, LootContextParameterSet parameters, long seed, CallbackInfo ci) {
        System.out.println("supplyInventory started");
        /*if (parameters.contains(ModLootContextParameters.BATTLE_TOWER_REWARD_CHANCE)) {
            System.out.println("BATTLE_TOWER_REWARD_CHANCE exists");
            float rewardChance = parameters.get(ModLootContextParameters.BATTLE_TOWER_REWARD_CHANCE);
            LootContext lootContext = (new LootContext.Builder(parameters)).random(seed).build(this.randomSequenceId);
            Random random = lootContext.getRandom();


            for (int n = 0; n < inventory.size(); n++) {
                //List<ItemStack> list = lootTable.generateLoot(lootContextParameterSet);
                //System.out.println("list is " + list);

                ItemStack item;
                if (random.nextFloat() > Math.max(rewardChance, 0.15f)) {
                    item = ItemStack.EMPTY;
                } else {
                    item = this.generateLoot(lootContext).get(0);
                    if (item.isOf(Items.ENCHANTED_BOOK)) {
                        item = EnchantmentHelper.enchant(random, Items.BOOK.getDefaultStack(), 5, false);
                    }
                }
                System.out.println("inventory setup on slot number " + n + ", item is " + item);
                inventory.setStack(n, item);
            }

            ci.cancel();


            ObjectArrayList<ItemStack> objectArrayList = this.generateLoot(lootContext);
            List<Integer> list = this.getFreeSlots(inventory, random);
            this.shuffle(objectArrayList, list.size(), random);

            for (ItemStack itemStack : objectArrayList) {
                if (list.isEmpty()) {
                    LOGGER.warn("Tried to over-fill a container");
                    return;
                }

                if (itemStack.isEmpty()) {
                    inventory.setStack(list.remove(list.size() - 1), ItemStack.EMPTY);
                } else {
                    inventory.setStack(list.remove(list.size() - 1), itemStack);
                }
            }
        }*/
    }
}
