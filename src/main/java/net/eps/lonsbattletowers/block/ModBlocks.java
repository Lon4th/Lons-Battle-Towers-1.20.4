package net.eps.lonsbattletowers.block;

import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.block.custom.TowerSpawnerBlock;
import net.eps.lonsbattletowers.block.custom.TowerVaultBlock;
import net.eps.lonsbattletowers.block.custom.vault.TowerVaultState;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import static net.minecraft.block.Blocks.*;

public class ModBlocks {

    /* New Blocks */

    public static final Block TOWER_SPAWNER = registerBlockWithItem("tower_spawner", new TowerSpawnerBlock(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(Instrument.BASEDRUM).strength(6f).requiresTool().strength(1.0f, 1200.0f).luminance(state -> state.get(TowerSpawnerBlock.TOWER_SPAWNER_STATE).getLuminance()).sounds(BlockSoundGroup.TRIAL_SPAWNER).blockVision(Blocks::never).nonOpaque()));
    public static final Block TOWER_VAULT = registerBlockWithItem("tower_vault", new TowerVaultBlock(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(Instrument.BASEDRUM).nonOpaque().sounds(BlockSoundGroup.TRIAL_SPAWNER)
            .luminance(state -> (state.get(TowerVaultBlock.TOWER_VAULT_STATE)).getLuminance()).strength(50.0F).blockVision(Blocks::never)));

    private static Block registerBlockWithItem(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(LonsBattleTowers.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, new Identifier(LonsBattleTowers.MOD_ID, name), new BlockItem(block, new Item.Settings()));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> entries.addAfter(Items.SPAWNER, item));
        return item;
    }

    public static void registerModBlocks() {
        LonsBattleTowers.LOGGER.info("Registering ModBlocks for " + LonsBattleTowers.MOD_ID);
    }
    
}
