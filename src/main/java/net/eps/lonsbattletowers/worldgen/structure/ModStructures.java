package net.eps.lonsbattletowers.worldgen.structure;

import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.worldgen.structure.processor.TowerAgeProcessor;
import net.eps.lonsbattletowers.worldgen.structure.processor.TowerFloorAgeProcessor;
import net.eps.lonsbattletowers.worldgen.structure.processor.TowerSpecialBlocksSetupProcessor;
import net.minecraft.registry.*;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.StructureType;

public class ModStructures {

    public static StructureType<BattleTowerStructure> BATTLE_TOWER_STRUCTURE_TYPE;

    public static StructureProcessorType<TowerAgeProcessor> TOWER_AGE_PROCESSOR = Registry.register(Registries.STRUCTURE_PROCESSOR, new Identifier(LonsBattleTowers.MOD_ID, "tower_age_processor"), () -> {return TowerAgeProcessor.CODEC;});
    public static StructureProcessorType<TowerFloorAgeProcessor> TOWER_FLOOR_AGE_PROCESSOR = Registry.register(Registries.STRUCTURE_PROCESSOR, new Identifier(LonsBattleTowers.MOD_ID, "tower_floor_base_age_processor"), () -> {return TowerFloorAgeProcessor.CODEC;});
    public static StructureProcessorType<TowerSpecialBlocksSetupProcessor> TOWER_SPAWNER_SETUP_PROCESSOR = Registry.register(Registries.STRUCTURE_PROCESSOR, new Identifier(LonsBattleTowers.MOD_ID, "tower_special_blocks_setup_processor"), () -> {return TowerSpecialBlocksSetupProcessor.CODEC;});

    public static void registerStructures() {
        BATTLE_TOWER_STRUCTURE_TYPE = Registry.register(Registries.STRUCTURE_TYPE, new Identifier(LonsBattleTowers.MOD_ID, "battle_tower_type"), () -> BattleTowerStructure.CODEC);
    }
}
