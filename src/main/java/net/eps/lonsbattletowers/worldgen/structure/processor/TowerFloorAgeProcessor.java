package net.eps.lonsbattletowers.worldgen.structure.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.eps.lonsbattletowers.worldgen.structure.ModStructures;
import net.minecraft.block.*;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class TowerFloorAgeProcessor extends StructureProcessor {
    public static final Codec<TowerFloorAgeProcessor> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(Codec.FLOAT.fieldOf("removeChance").forGetter((processor) -> processor.removeChance)
            ).apply(instance, TowerFloorAgeProcessor::new));
    private final float removeChance;


    public TowerFloorAgeProcessor(float removeChance) {
        this.removeChance = removeChance;
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos pivot, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo currentBlockInfo, StructurePlacementData data) {
        Random random = data.getRandom(currentBlockInfo.pos());
        BlockState blockState = currentBlockInfo.state();
        BlockPos blockPos = currentBlockInfo.pos();
        BlockState blockState2 = null;

        if (!blockState.isOf(Blocks.AIR)) {
            blockState2 = this.processAir(random, pos);
        }

        return blockState2 != null ? new StructureTemplate.StructureBlockInfo(blockPos, blockState2, currentBlockInfo.nbt()) : currentBlockInfo;
    }

    @Nullable
    private BlockState processAir(Random random, BlockPos pos) {
        if (random.nextFloat() >= this.removeChance * pos.getY()) {
            return null;
        } else {
            return Blocks.AIR.getDefaultState();
        }
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModStructures.TOWER_FLOOR_AGE_PROCESSOR;
    }
}
