package net.eps.lonsbattletowers.item;

import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.entity.ModEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item TOWER_MIMIC_SPAWN_EGG = registerItem("tower_mimic_spawn_egg",
            new SpawnEggItem(ModEntities.TOWER_MIMIC, 0xffffff, 0xffffff, new FabricItemSettings()));
    public static final Item TOWER_KEY = registerItem("tower_key", new Item((new Item.Settings())));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(LonsBattleTowers.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LonsBattleTowers.LOGGER.info("Registering Mod Items for " + LonsBattleTowers.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> entries.add(TOWER_MIMIC_SPAWN_EGG));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.addAfter(Items.PHANTOM_MEMBRANE, TOWER_KEY));
    }
}
