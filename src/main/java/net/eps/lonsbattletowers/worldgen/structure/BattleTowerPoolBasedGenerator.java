package net.eps.lonsbattletowers.worldgen.structure;

import com.google.common.collect.Lists;
import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.block.ModBlocks;
import net.eps.lonsbattletowers.block.custom.spawner.TowerSpawner;
import net.eps.lonsbattletowers.block.entity.TowerSpawnerBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.command.PlaceCommand;
import net.minecraft.structure.*;
import net.minecraft.structure.pool.EmptyPoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.Structure;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class BattleTowerPoolBasedGenerator {

    public static Optional<PoolStructurePiece> generate(Structure.Context context, RegistryEntry<StructurePool> structurePool, Optional<Identifier> id, int size,
                                                        BlockPos pos, boolean useExpansionHack, Optional<Heightmap.Type> projectStartToHeightmap, int maxDistanceFromCenter,
                                                        List<PoolStructurePiece> list, BlockRotation rotation, Random random) {

        DynamicRegistryManager dynamicRegistryManager = context.dynamicRegistryManager();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        StructureTemplateManager structureTemplateManager = context.structureTemplateManager();
        HeightLimitView heightLimitView = context.world();
        Registry<StructurePool> registry = dynamicRegistryManager.get(RegistryKeys.TEMPLATE_POOL);
        StructurePool structurePool2 = structurePool.value();
        StructurePoolElement structurePoolElement = structurePool2.getRandomElement(random);

        if (structurePoolElement == EmptyPoolElement.INSTANCE) {
            return Optional.empty();
        } else {

            BlockPos blockPos;
            if (id.isPresent()) {
                Identifier identifier = id.get();
                Optional<BlockPos> optional = findStartingJigsawPos(structurePoolElement, identifier, pos, rotation, structureTemplateManager, (ChunkRandom) random);
                if (optional.isEmpty()) {
                    LonsBattleTowers.LOGGER.error("No starting jigsaw {} found in start pool {}", identifier, structurePool.getKey().map((key) -> {
                        return key.getValue().toString();
                    }).orElse("<unregistered>"));
                    return Optional.empty();
                }

                blockPos = (BlockPos)optional.get();
            } else {
                blockPos = pos;
            }

            Vec3i vec3i = blockPos.subtract(pos);
            BlockPos blockPos2 = pos.subtract(vec3i);
            PoolStructurePiece poolStructurePiece = new PoolStructurePiece(structureTemplateManager, structurePoolElement, blockPos2, structurePoolElement.getGroundLevelDelta(), rotation, structurePoolElement.getBoundingBox(structureTemplateManager, blockPos2, rotation));
            BlockBox blockBox = poolStructurePiece.getBoundingBox();
            int i = (blockBox.getMaxX() + blockBox.getMinX()) / 2;
            int j = (blockBox.getMaxZ() + blockBox.getMinZ()) / 2;
            int k;
            if (projectStartToHeightmap.isPresent()) {
                k = pos.getY() + chunkGenerator.getHeightOnGround(i, j, (Heightmap.Type)projectStartToHeightmap.get(), heightLimitView, context.noiseConfig());
            } else {
                k = blockPos2.getY();
            }

            int l = blockBox.getMinY() + poolStructurePiece.getGroundLevelDelta();
            poolStructurePiece.translate(0, k - l, 0);
            int m = k + vec3i.getY();

            list.add(poolStructurePiece);
            if (size > 0) {
                Box box = new Box((i - maxDistanceFromCenter), (m - maxDistanceFromCenter), (j - maxDistanceFromCenter), (i + maxDistanceFromCenter + 1), (m + maxDistanceFromCenter + 1), (j + maxDistanceFromCenter + 1));
                VoxelShape voxelShape = VoxelShapes.combineAndSimplify(VoxelShapes.cuboid(box), VoxelShapes.cuboid(Box.from(blockBox)), BooleanBiFunction.ONLY_FIRST);
                StructurePoolBasedGenerator.generate(context.noiseConfig(), size, useExpansionHack, chunkGenerator, structureTemplateManager, heightLimitView, random, registry, poolStructurePiece, list, voxelShape);
            }

            return Optional.of(poolStructurePiece);
            /* return Optional.of(new Structure.StructurePosition(new BlockPos(i, m, j), (collector) -> {
                List<PoolStructurePiece> list = Lists.newArrayList();
                list.add(poolStructurePiece);
                if (size > 0) {
                    Box box = new Box((double)(i - maxDistanceFromCenter), (double)(m - maxDistanceFromCenter), (double)(j - maxDistanceFromCenter), (double)(i + maxDistanceFromCenter + 1), (double)(m + maxDistanceFromCenter + 1), (double)(j + maxDistanceFromCenter + 1));
                    VoxelShape voxelShape = VoxelShapes.combineAndSimplify(VoxelShapes.cuboid(box), VoxelShapes.cuboid(Box.from(blockBox)), BooleanBiFunction.ONLY_FIRST);
                    StructurePoolBasedGenerator.generate(context.noiseConfig(), size, useExpansionHack, chunkGenerator, structureTemplateManager, heightLimitView, chunkRandom, registry, poolStructurePiece, list, voxelShape);
                    Objects.requireNonNull(collector);
                    list.forEach(collector::addPiece);
                }
            })); */
        }
    }

    public static Optional<PoolStructurePiece> generatePiece(Structure.Context context, RegistryEntry<StructurePool> structurePool, Optional<Identifier> id, int size,
                                                        BlockPos pos, Optional<Heightmap.Type> projectStartToHeightmap, int maxDistanceFromCenter,
                                                        List<PoolStructurePiece> list, BlockRotation rotation, Random random) {

        DynamicRegistryManager dynamicRegistryManager = context.dynamicRegistryManager();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        StructureTemplateManager structureTemplateManager = context.structureTemplateManager();
        HeightLimitView heightLimitView = context.world();
        Registry<StructurePool> registry = dynamicRegistryManager.get(RegistryKeys.TEMPLATE_POOL);
        StructurePool structurePool2 = structurePool.value();
        StructurePoolElement structurePoolElement = structurePool2.getRandomElement(random);


        if (structurePoolElement == EmptyPoolElement.INSTANCE) {
            return Optional.empty();
        } else {

            BlockPos blockPos;
            if (id.isPresent()) {
                Identifier identifier = id.get();
                Optional<BlockPos> optional = findStartingJigsawPos(structurePoolElement, identifier, pos, rotation, structureTemplateManager, (ChunkRandom) random);
                if (optional.isEmpty()) {
                    LonsBattleTowers.LOGGER.error("No starting jigsaw {} found in start pool {}", identifier, structurePool.getKey().map((key) -> {
                        return key.getValue().toString();
                    }).orElse("<unregistered>"));
                    return Optional.empty();
                }

                blockPos = optional.get();
            } else {
                blockPos = pos;
            }


            Vec3i vec3i = blockPos.subtract(pos);
            BlockPos blockPos2 = pos.subtract(vec3i);
            PoolStructurePiece poolStructurePiece = new PoolStructurePiece(structureTemplateManager, structurePoolElement, blockPos2, structurePoolElement.getGroundLevelDelta(), rotation, structurePoolElement.getBoundingBox(structureTemplateManager, blockPos2, rotation));
            BlockBox blockBox = poolStructurePiece.getBoundingBox();
            int i = (blockBox.getMaxX() + blockBox.getMinX()) / 2;
            int j = (blockBox.getMaxZ() + blockBox.getMinZ()) / 2;
            int k = blockPos2.getY();

            int m = k + vec3i.getY();

            /*if (towerFloor.isPresent()) {
                int floor = towerFloor.get();

            }*/

            list.add(poolStructurePiece);
            if (size > 0) {
                Box box = new Box((i - maxDistanceFromCenter), (m - maxDistanceFromCenter), (j - maxDistanceFromCenter), (i + maxDistanceFromCenter + 1), (m + maxDistanceFromCenter + 1), (j + maxDistanceFromCenter + 1));
                VoxelShape voxelShape = VoxelShapes.combineAndSimplify(VoxelShapes.cuboid(box), VoxelShapes.cuboid(Box.from(blockBox)), BooleanBiFunction.ONLY_FIRST);
                StructurePoolBasedGenerator.generate(context.noiseConfig(), size, false, chunkGenerator, structureTemplateManager, heightLimitView, random, registry, poolStructurePiece, list, voxelShape);
            }

            return Optional.of(poolStructurePiece);
        }
    }

    public static BlockBox generateFloors(Structure.Context context, RegistryEntry<StructurePool> floorBasePool, RegistryEntry<StructurePool> floorPool, Optional<Identifier> id, int size,
                                                             BlockPos pos, Optional<Heightmap.Type> projectStartToHeightmap, int maxDistanceFromCenter,
                                                             List<PoolStructurePiece> list, BlockRotation rotation, Random random) {

        DynamicRegistryManager dynamicRegistryManager = context.dynamicRegistryManager();
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        StructureTemplateManager structureTemplateManager = context.structureTemplateManager();
        HeightLimitView heightLimitView = context.world();
        Registry<StructurePool> registry = dynamicRegistryManager.get(RegistryKeys.TEMPLATE_POOL);
        StructurePoolElement floorBaseElement = floorBasePool.value().getRandomElement(random);

        /*
        String floorNumber = floorBaseElement.toString().substring(61, 63);
        String floorStartNumber = floorBaseElement.toString().substring(0, 12);
        String floorEndNumber = floorBaseElement.toString().substring(63);
        String floor = floorStartNumber + "lonsbattletowers:battle_tower/floors/tower_floor_" + floorNumber + floorEndNumber;

        StructurePoolElement floorElement = floorPool.value().getRandomElement();

         */

        if (floorBaseElement == EmptyPoolElement.INSTANCE) {
            return null;
        } else {

            BlockPos blockPos;
            if (id.isPresent()) {
                Identifier identifier = id.get();
                Optional<BlockPos> optional = findStartingJigsawPos(floorBaseElement, identifier, pos, rotation, structureTemplateManager, (ChunkRandom) random);
                if (optional.isEmpty()) {
                    LonsBattleTowers.LOGGER.error("No starting jigsaw {} found in start pool {}", identifier, floorBasePool.getKey().map((key) -> {
                        return key.getValue().toString();
                    }).orElse("<unregistered>"));
                    return null;
                }

                blockPos = optional.get();
            } else {
                blockPos = pos;
            }


            Vec3i vec3i = blockPos.subtract(pos);
            BlockPos blockPos2 = pos.subtract(vec3i);
            BlockPos blockPos3 = blockPos2.add(0, 1, 0);
            PoolStructurePiece poolBaseStructurePiece = new PoolStructurePiece(structureTemplateManager, floorBaseElement, blockPos2, floorBaseElement.getGroundLevelDelta(), rotation, floorBaseElement.getBoundingBox(structureTemplateManager, blockPos2, rotation));
            //PoolStructurePiece poolFloorStructurePiece = new PoolStructurePiece(structureTemplateManager, floorElement, blockPos3, floorElement.getGroundLevelDelta(), rotation, floorElement.getBoundingBox(structureTemplateManager, blockPos3, rotation));

            BlockBox blockBox = poolBaseStructurePiece.getBoundingBox();
            //BlockBox blockBox2 = poolFloorStructurePiece.getBoundingBox();
            int i = (blockBox.getMaxX() + blockBox.getMinX()) / 2;
            int j = (blockBox.getMaxZ() + blockBox.getMinZ()) / 2;
            int k = blockPos2.getY();

            int m = k + vec3i.getY();

            list.add(poolBaseStructurePiece);
            //list.add(poolFloorStructurePiece);

            if (size > 0) {
                Box box = new Box((i - maxDistanceFromCenter), (m - maxDistanceFromCenter), (j - maxDistanceFromCenter), (i + maxDistanceFromCenter + 1), (m + maxDistanceFromCenter + 1), (j + maxDistanceFromCenter + 1));
                VoxelShape voxelShape = VoxelShapes.combineAndSimplify(VoxelShapes.cuboid(box), VoxelShapes.cuboid(Box.from(blockBox)), BooleanBiFunction.ONLY_FIRST);
                StructurePoolBasedGenerator.generate(context.noiseConfig(), size, false, chunkGenerator, structureTemplateManager, heightLimitView, random, registry, poolBaseStructurePiece, list, voxelShape);
            }

            //return new BlockBox(Math.min(blockBox.getMinX(), blockBox2.getMinX()), Math.min(blockBox.getMinY(), blockBox2.getMinY()), Math.min(blockBox.getMinZ(), blockBox2.getMinZ()), Math.min(blockBox.getMaxX(), blockBox2.getMaxX()), Math.min(blockBox.getMaxY(), blockBox2.getMaxY()), Math.min(blockBox.getMaxZ(), blockBox2.getMaxZ()));
            return blockBox;
        }
    }

    private static Optional<BlockPos> findStartingJigsawPos(StructurePoolElement pool, Identifier id, BlockPos pos, BlockRotation rotation, StructureTemplateManager structureManager, ChunkRandom random) {
        List<StructureTemplate.StructureBlockInfo> list = pool.getStructureBlockInfos(structureManager, pos, rotation, random);
        Optional<BlockPos> optional = Optional.empty();
        Iterator var8 = list.iterator();

        while(var8.hasNext()) {
            StructureTemplate.StructureBlockInfo structureBlockInfo = (StructureTemplate.StructureBlockInfo)var8.next();
            Identifier identifier = Identifier.tryParse(structureBlockInfo.nbt().getString("name"));
            if (id.equals(identifier)) {
                optional = Optional.of(structureBlockInfo.pos());
                break;
            }
        }

        return optional;
    }

}
