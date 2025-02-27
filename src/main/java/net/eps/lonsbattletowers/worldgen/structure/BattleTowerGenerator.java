package net.eps.lonsbattletowers.worldgen.structure;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.eps.lonsbattletowers.LonsBattleTowers;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.*;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.*;
import net.minecraft.world.gen.structure.Structure;

import java.util.*;

public class BattleTowerGenerator {

    public static void addPieces(Structure.Context context, BlockPos pos, List<PoolStructurePiece> pieces, RegistryEntry<StructurePool> startPool, RegistryEntry<StructurePool> finishPool, RegistryEntry<StructurePool> floorsPool, RegistryEntry<StructurePool> floorBasesPool, RegistryEntry<StructurePool> firstBridgePool, RegistryEntry<StructurePool> towerPoolBottom, RegistryEntry<StructurePool> towerPoolTransitional, RegistryEntry<StructurePool> towerPoolEnd, BlockRotation rotation, BlockMirror mirror, Optional<Heightmap.Type> projectStartToHeightmap) {
        final int FLOOR_BLOCK_COUNT = 11;
        ChunkRandom random = context.random();
        Map<BlockRotation, Integer[]> additionFloorsMap = new HashMap<>();
        Map<List<Object>, PoolStructurePiece> additionFirstTowersMap = new HashMap<>();
        //PoolStructurePiece piece = BattleTowerPoolBasedGenerator.generate(context, startPool, Optional.empty(), 20, pos, false, projectStartToHeightmap,  256, pieces, rotation, random).orElse(null);
        PoolStructurePiece startPiece = BattleTowerPoolBasedGenerator.generate(context, startPool, Optional.empty(), 20, pos, false, projectStartToHeightmap,  256, pieces, rotation, random).orElse(null);

        if (startPiece == null) {
            System.out.println("Start piece is empty");
            return;
        }
        //BlockBox piece = startPiece.getBoundingBox();

        PoolStructurePiece piece = startPiece;
        for (int i = 1; i < 10; i++) {
            BlockBox pieceBox = new BlockBox(piece.getBoundingBox().getMinX(), piece.getBoundingBox().getMinY(), piece.getBoundingBox().getMinZ(),
                                             piece.getBoundingBox().getMaxX(), piece.getBoundingBox().getMaxY() + (piece == startPiece ? 0 : FLOOR_BLOCK_COUNT), piece.getBoundingBox().getMaxZ());
            /*//System.out.println("Floor is " + i + ", box is " + pieceBox.getMaxY());
            StructurePoolElement testElement = floorsPool.value().getRandomElement(random);
            PoolStructurePiece heightPiece = new PoolStructurePiece(context.structureTemplateManager(), testElement, pos, testElement.getGroundLevelDelta(), rotation, testElement.getBoundingBox(context.structureTemplateManager(), pos, rotation));

            String string = heightPiece.getPoolElement().toString().substring(61, 63);
            //
            // Single[Left[lonsbattletowers:battle_tower/floors/tower_floor_06]]
            System.out.println("piece name " + string + ", or " + floorsPool.value().toString());


            //int yCountBase = box.getBlockCountY();
            //int yCount = heightPiece.getBoundingBox().getBlockCountY();
            //BlockPos blockPos = new BlockPos(pos.getX(), piece.getPos().getY(), pos.getZ()).add(0, yCount + yCountBase, 0);
            //System.out.println("Y count base " + yCountBase + ", Y count " + yCount + ", block pos " + blockPos);*/
            BlockPos blockPos = new BlockPos(pos.getX(), pieceBox.getMaxY(), pos.getZ()).add(0, 1, 0);

            while (!pieceBox.intersects(new BlockBox(blockPos.down()))) {
                blockPos = blockPos.down();
            }
            while (pieceBox.intersects(new BlockBox(blockPos))) {
                blockPos = blockPos.up();
            }

            //piece = BattleTowerPoolBasedGenerator.generatePiece(context, floorBasesPool, Optional.empty(), 20, blockPos.add(0, yCount, 0), Optional.empty(), 256, pieces, rotation, random).orElse(null);
            piece = BattleTowerPoolBasedGenerator.generatePiece(context, floorBasesPool, Optional.empty(), 20, blockPos, Optional.empty(), 256, pieces, rotation, random).orElse(null);


            if (piece == null) {
                System.out.println("Floor #" + i + " piece is empty");
                return;
            }

            List<Float> chances = List.of(0.1F, 0.3F, 0.5F, 0.9F);
            if (i >= 5) {
                //BlockPos additionPos = new BlockPos(piece.getCenter().getX(), pieceBox.getMinY(), piece.getCenter().getZ());
                //BlockRotation blockRotation = BlockRotation.random(context.random());
                ArrayList<Float> additionChances = new ArrayList<>();
                for (BlockRotation blockRotation : BlockRotation.values()) {
                    if (additionChances.isEmpty()) {
                        additionChances.addAll(chances);
                    }
                    int chosenChance = random.nextInt(additionChances.size());
                    if (random.nextFloat() > additionChances.get(chosenChance)) {
                        additionChances.remove(chosenChance);
                        continue;
                    }
                    additionChances.remove(chosenChance);

                    //PoolStructurePiece additionTowerPiece = null;
                    BlockPos additionPos = new BlockPos(piece.getCenter().getX(), pieceBox.getMinY(), piece.getCenter().getZ());

                    Vec3i offset = new Vec3i(0, 0, 0);
                    Vec3i moveTo = new Vec3i(0, 0, 0);
                    if (blockRotation.equals(BlockRotation.NONE)) {
                        while (pieceBox.intersects(new BlockBox(additionPos.add(-1, 0, 0)))) {
                            additionPos = additionPos.add(-1, 0, 0);
                        }

                        offset = new Vec3i(2, 0, 0);
                        moveTo = new Vec3i(-1, 0, 0);
                    } else if (blockRotation.equals(BlockRotation.CLOCKWISE_180)) {
                        while (pieceBox.intersects(new BlockBox(additionPos.add(1, 0, 0)))) {
                            additionPos = additionPos.add(1, 0, 0);
                        }

                        offset = new Vec3i(-2, 0, 0);
                        moveTo = new Vec3i(1, 0, 0);
                    } else if (blockRotation.equals(BlockRotation.CLOCKWISE_90)) {
                        while (pieceBox.intersects(new BlockBox(additionPos.add(0, 0, -1)))) {
                            additionPos = additionPos.add(0, 0, -1);
                        }

                        offset = new Vec3i(0, 0, 2);
                        moveTo = new Vec3i(0, 0, -1);
                    } else if (blockRotation.equals(BlockRotation.COUNTERCLOCKWISE_90)) {
                        while (pieceBox.intersects(new BlockBox(additionPos.add(0, 0, 1)))) {
                            additionPos = additionPos.add(0, 0, 1);
                        }

                        offset = new Vec3i(0, 0, -2);
                        moveTo = new Vec3i(0, 0, 1);
                    }

                    adjustFloors(additionFloorsMap, blockRotation, i);

                    PoolStructurePiece additionBridgePiece = BattleTowerPoolBasedGenerator.generatePiece(context, firstBridgePool, Optional.of(new Identifier(LonsBattleTowers.MOD_ID, "bridge_first")), 10, additionPos, projectStartToHeightmap, 256, pieces, blockRotation, random).orElse(null);
                    if (additionBridgePiece == null) return;

                    PoolStructurePiece additionTowerPiece = generateAdditionTower(additionFloorsMap, additionFirstTowersMap, additionBridgePiece, context, towerPoolBottom, towerPoolTransitional, towerPoolEnd, projectStartToHeightmap, pieces, blockRotation, random, offset, moveTo, i);

                    BattleTowerGenerator.add(pieces, additionBridgePiece);
                }

                if (i == 9) {
                    for (BlockRotation rot : BlockRotation.values()) {
                        if (additionFloorsMap.get(rot) != null) {
                            List<Integer> floors = new ArrayList<>();
                            for (Integer floor : additionFloorsMap.get(rot)) {
                                if (floor != null) {
                                    floors.add(floor);
                                } else {
                                    break;
                                }
                            }
                            while (floors.size() > 1) {
                                floors.remove(0);
                            }
                            int finalFloor = floors.get(0);
                            PoolStructurePiece towerPiece = additionFirstTowersMap.get(List.of(finalFloor, rot));

                            int floorsAmount = random.nextInt(2);
                            for (int n = 0; n < floorsAmount; n++) {
                                BlockPos additionTowerPos = new BlockPos(towerPiece.getCenter().getX(), towerPiece.getBoundingBox().getMaxY(), towerPiece.getCenter().getZ());
                                additionTowerPos = calculatePos(towerPiece, additionTowerPos, new Vec3i(0, 0, 0), new Vec3i(0, 1, 0));
                                towerPiece = BattleTowerPoolBasedGenerator.generatePiece(context, towerPoolTransitional, Optional.of(new Identifier("lonsbattletowers:room_transitional")), 10, additionTowerPos, projectStartToHeightmap, 256, pieces, rot, random).orElse(null);
                            }
                            BlockPos additionTowerPos = new BlockPos(towerPiece.getCenter().getX(), towerPiece.getBoundingBox().getMaxY(), towerPiece.getCenter().getZ());
                            additionTowerPos = calculatePos(towerPiece, additionTowerPos, new Vec3i(0, 0, 0), new Vec3i(0, 1, 0));
                            towerPiece = BattleTowerPoolBasedGenerator.generatePiece(context, towerPoolEnd, Optional.of(new Identifier("lonsbattletowers:room_end")), 10, additionTowerPos, projectStartToHeightmap, 256, pieces, rot, random).orElse(null);
                        }
                    }



                    //int yCount = piece.getBoundingBox().getBlockCountY();
                    BlockPos finalPieceBlockPos = new BlockPos(pieceBox.getCenter().getX(), blockPos.getY(), pieceBox.getCenter().getZ()).add(0, FLOOR_BLOCK_COUNT + 1, 0);
                    System.out.println("finalPieceBlockPos " + finalPieceBlockPos);
                    piece = BattleTowerPoolBasedGenerator.generatePiece(context, finishPool, Optional.of(new Identifier(LonsBattleTowers.MOD_ID, "tower_floor_end")), 20, finalPieceBlockPos, Optional.empty(), 256, pieces, rotation, random).orElse(null);
                }
            }
        }

        /*
        System.out.println("Map final stage is " + additionFloorsMap);
        System.out.println("Map for BlockRotation.NONE (west; -X) is " + Arrays.toString(additionFloorsMap.get(BlockRotation.NONE)));
        System.out.println("Map for BlockRotation.CLOCKWISE_90 (north; -Z) is " + Arrays.toString(additionFloorsMap.get(BlockRotation.CLOCKWISE_90)));
        System.out.println("Map for BlockRotation.COUNTERCLOCKWISE_90 (south; +Z) is " + Arrays.toString(additionFloorsMap.get(BlockRotation.COUNTERCLOCKWISE_90)));
        System.out.println("Map for BlockRotation.CLOCKWISE_180 (east; +X) is " + Arrays.toString(additionFloorsMap.get(BlockRotation.CLOCKWISE_180)));*/
    }




    static PoolStructurePiece generateAdditionTower(Map<BlockRotation, Integer[]> floorsMap, Map<List<Object>, PoolStructurePiece> firstTowersMap,
                                                    PoolStructurePiece bridgePiece,
                                                    Structure.Context context,
                                                    RegistryEntry<StructurePool> towerPoolBottom,
                                                    RegistryEntry<StructurePool> towerPoolTransitional,
                                                    RegistryEntry<StructurePool> towerPoolEnd,
                                                    Optional<Heightmap.Type> projectStartToHeightmap,
                                                    List<PoolStructurePiece> pieces, BlockRotation blockRotation, ChunkRandom random,
                                                    Vec3i towerOffset, Vec3i movingTo, int mainFloor) {

        Optional<Identifier> bottomId = Optional.of(new Identifier("lonsbattletowers:room_bottom"));
        Optional<Identifier> transitionalId = Optional.of(new Identifier("lonsbattletowers:room_transitional"));
        Optional<Identifier> endId = Optional.of(new Identifier("lonsbattletowers:room_end"));

        List<Integer> floors = new ArrayList<>();
        for (Integer floor : floorsMap.get(blockRotation)) {
            if (floor != null) {
                floors.add(floor);
            }
        }
        while (floors.size() > 2) {
            floors.remove(0);
        }


        BlockPos additionTowerPos = new BlockPos(bridgePiece.getCenter().getX(), bridgePiece.getBoundingBox().getMinY(), bridgePiece.getCenter().getZ());
        additionTowerPos = calculatePos(bridgePiece, additionTowerPos, towerOffset, movingTo).add(0, 2, 0);
        PoolStructurePiece towerPiece;
        Optional<Identifier> towerId;
        RegistryEntry<StructurePool> towerPool;
        if (floors.size() == 2) {
            towerId = transitionalId;
            towerPool = towerPoolTransitional;


            int minFloor = floors.get(0);
            int maxFloor = floors.get(1);
            int distance = (maxFloor - minFloor) - 1;
            Vec3i moveUp = new Vec3i(0, 1, 0);
            PoolStructurePiece mainTowerPiece = firstTowersMap.get(List.of(minFloor, blockRotation));

            if (distance == 1) {
                BlockPos towersPos = new BlockPos(mainTowerPiece.getCenter().getX(), mainTowerPiece.getBoundingBox().getMaxY(), mainTowerPiece.getCenter().getZ());
                towersPos = calculatePos(mainTowerPiece, towersPos, new Vec3i(0, 0, 0), moveUp);
                mainTowerPiece = BattleTowerPoolBasedGenerator.generatePiece(context, towerPoolTransitional, transitionalId, 10, towersPos, projectStartToHeightmap,  256, pieces, blockRotation, random).orElse(null);

            } else if (distance > 1) {
                boolean bl = distance == 4 || random.nextBetween(0, 10) < 7;
                if (!bl || distance > 2) {
                    for (int i = 0; bl ? i < random.nextInt(distance - 2) : i < distance; i++) {
                        BlockPos towersPos = new BlockPos(mainTowerPiece.getCenter().getX(), mainTowerPiece.getBoundingBox().getMaxY(), mainTowerPiece.getCenter().getZ());
                        towersPos = calculatePos(mainTowerPiece, towersPos, new Vec3i(0, 0, 0), moveUp);
                        mainTowerPiece = BattleTowerPoolBasedGenerator.generatePiece(context, towerPoolTransitional, transitionalId, 10, towersPos, projectStartToHeightmap, 256, pieces, blockRotation, random).orElse(null);
                    }
                }
                if (bl) {
                    towerId = bottomId;
                    towerPool = towerPoolBottom;

                    BlockPos towersPos = new BlockPos(mainTowerPiece.getCenter().getX(), mainTowerPiece.getBoundingBox().getMaxY(), mainTowerPiece.getCenter().getZ());
                    towersPos = calculatePos(mainTowerPiece, towersPos, new Vec3i(0, 0, 0), moveUp);
                    mainTowerPiece = BattleTowerPoolBasedGenerator.generatePiece(context, towerPoolEnd, endId, 10, towersPos, projectStartToHeightmap,  256, pieces, blockRotation, random).orElse(null);
                    //floorsMap.put(blockRotation, new Integer[9]);
                }
            }
        } else {
            towerId = bottomId;
            towerPool = towerPoolBottom;
        }


        towerPiece = BattleTowerPoolBasedGenerator.generatePiece(context, towerPool, towerId, 10, additionTowerPos, projectStartToHeightmap,  256, pieces, blockRotation, random).orElse(null);
        firstTowersMap.put(List.of(mainFloor, blockRotation), towerPiece);

        System.out.println("returned value is " + towerPiece);
        return towerPiece;
    }

    static BlockPos calculatePos(PoolStructurePiece piece, BlockPos firstPos, Vec3i offset, Vec3i moveTo) {
        while (piece.getBoundingBox().intersects(new BlockBox(firstPos.add(offset)))) {
            firstPos = firstPos.add(moveTo);
        }

        return firstPos;
    }

    static void adjustFloors(Map<BlockRotation, Integer[]> additionedFloorsMap, BlockRotation blockRotation, int floor) {
        if (additionedFloorsMap.get(blockRotation) == null) {
            Integer[] integers = new Integer[9];
            integers[0] = floor;
            additionedFloorsMap.put(blockRotation, integers);
        } else {
            Integer[] integers = additionedFloorsMap.get(blockRotation);
            int stage = 0;
            for (Integer integer : integers) {
                if (integer != null && integer == floor) {
                    return;
                }
                if (integer == null) {
                    integers[stage] = floor;
                    break;
                }
                stage += 1;
            }
            additionedFloorsMap.put(blockRotation, integers);
        }
    }

    static PoolStructurePiece add(List<PoolStructurePiece> pieces, PoolStructurePiece piece) {
        pieces.add(piece);
        return piece;
    }
}
