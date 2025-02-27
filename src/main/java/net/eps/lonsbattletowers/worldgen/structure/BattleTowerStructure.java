package net.eps.lonsbattletowers.worldgen.structure;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.eps.lonsbattletowers.LonsBattleTowers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.*;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.HeightContext;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.RuinedPortalStructure;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BattleTowerStructure extends Structure {

    public static final Codec<BattleTowerStructure> CODEC = RecordCodecBuilder.<BattleTowerStructure>mapCodec
            (instance -> instance.group(BattleTowerStructure.configCodecBuilder(instance),
                            StructurePool.REGISTRY_CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                            StructurePool.REGISTRY_CODEC.fieldOf("finish_pool").forGetter(structure -> structure.finishPool),
                            StructurePool.REGISTRY_CODEC.fieldOf("floors_pool").forGetter(structure -> structure.floorsPool),
                            StructurePool.REGISTRY_CODEC.fieldOf("floor_bases_pool").forGetter(structure -> structure.floorBasesPool),
                            StructurePool.REGISTRY_CODEC.fieldOf("bridge_pool").forGetter(structure -> structure.bridgePool),
                            StructurePool.REGISTRY_CODEC.fieldOf("tower_pool_bottom").forGetter(structure -> structure.towerPoolBottom),
                            StructurePool.REGISTRY_CODEC.fieldOf("tower_pool_transitional").forGetter(structure -> structure.towerPoolTransitional),
                            StructurePool.REGISTRY_CODEC.fieldOf("tower_pool_end").forGetter(structure -> structure.towerPoolEnd),
                            Identifier.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                            Codec.intRange(0, 60).fieldOf("size").forGetter(structure -> structure.size),
                            HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                            Codec.BOOL.fieldOf("use_expansion_hack").forGetter(structure -> structure.useExpansionHack),
                            Heightmap.Type.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                            Codec.intRange(1, 256).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter))
                            .apply(instance, BattleTowerStructure::new)).codec();

    private final RegistryEntry<StructurePool> startPool;
    private final RegistryEntry<StructurePool> finishPool;

    private final RegistryEntry<StructurePool> floorsPool;
    private final RegistryEntry<StructurePool> floorBasesPool;

    private final RegistryEntry<StructurePool> bridgePool;
    private final RegistryEntry<StructurePool> towerPoolBottom;
    private final RegistryEntry<StructurePool> towerPoolTransitional;
    private final RegistryEntry<StructurePool> towerPoolEnd;

    private final Optional<Identifier> startJigsawName;
    private final int size;
    private final HeightProvider startHeight;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Type> projectStartToHeightmap;
    private final int maxDistanceFromCenter;

    public BattleTowerStructure(Structure.Config config, RegistryEntry<StructurePool> startPool, RegistryEntry<StructurePool> finishPool, RegistryEntry<StructurePool> floorsPool, RegistryEntry<StructurePool> floorBasesPool, RegistryEntry<StructurePool> bridgePool, RegistryEntry<StructurePool> towerPoolBottom, RegistryEntry<StructurePool> towerPoolTransitional, RegistryEntry<StructurePool> towerPoolEnd, Optional<Identifier> startJigsawName, int size, HeightProvider startHeight, boolean useExpansionHack, Optional<Heightmap.Type> projectStartToHeightmap, int maxDistanceFromCenter) {
        super(config);

        this.startPool = startPool;
        this.finishPool = finishPool;

        this.floorsPool = floorsPool;
        this.floorBasesPool = floorBasesPool;

        this.bridgePool = bridgePool;
        this.towerPoolBottom = towerPoolBottom;
        this.towerPoolTransitional = towerPoolTransitional;
        this.towerPoolEnd = towerPoolEnd;

        this.startJigsawName = startJigsawName;

        this.size = size;
        this.startHeight = startHeight;
        this.useExpansionHack = useExpansionHack;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    @Override
    public Optional<Structure.StructurePosition> getStructurePosition(Structure.Context context) {
        /* Structure randomness */
        BlockRotation blockRotation = BlockRotation.random(context.random());
        BlockMirror blockMirror = context.random().nextFloat() < 0.5f ? BlockMirror.NONE : BlockMirror.FRONT_BACK;

        /* Start piece position */
        ChunkPos chunkPos = context.chunkPos();
        int i = this.startHeight.get(context.random(), new HeightContext(context.chunkGenerator(), context.world()));
        BlockPos blockPos = new BlockPos(chunkPos.getStartX(), i, chunkPos.getStartZ());

        /* Generation */
        return Optional.of(new Structure.StructurePosition(blockPos,
                collector -> this.addPieces(collector, context, blockPos, blockRotation, blockMirror)
        ));

    }

    private void addPieces(StructurePiecesCollector collector, Structure.Context context, BlockPos pos, BlockRotation rotation, BlockMirror mirror) {
        ArrayList<PoolStructurePiece> list = Lists.newArrayList();
        BattleTowerGenerator.addPieces(context, pos, list, this.startPool, this.finishPool, this.floorsPool, this.floorBasesPool, this.bridgePool, this.towerPoolBottom, this.towerPoolTransitional, this.towerPoolEnd, rotation, mirror, this.projectStartToHeightmap);
        list.forEach(collector::addPiece);
    }

    @Override
    public StructureType<?> getType() {
        return ModStructures.BATTLE_TOWER_STRUCTURE_TYPE;
    }
}
