package net.eps.lonsbattletowers.worldgen.structure.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.eps.lonsbattletowers.worldgen.structure.ModStructures;
import net.minecraft.block.*;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.block.enums.StairShape;
import net.minecraft.block.enums.WallShape;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.*;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TowerAgeProcessor extends StructureProcessor {
    public static final Codec<TowerAgeProcessor> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(Codec.FLOAT.fieldOf("mossiness").forGetter((processor) -> processor.mossiness),
                                       Codec.FLOAT.fieldOf("crackness").forGetter(processor -> processor.crackness),
                                       Codec.BOOL.fieldOf("stairRemove").forGetter(processor -> processor.stairRemove)).apply(instance, TowerAgeProcessor::new));
    private final float mossiness;
    private final float crackness;
    private final boolean stairRemove;


    public TowerAgeProcessor(float mossiness, float crackness, boolean stairRemove) {
        this.mossiness = mossiness;
        this.crackness = crackness;
        this.stairRemove = stairRemove;
    }

    private static BlockState randomStairProperties(Random random, Block stairs, boolean anyStairsAllowed) {
        BlockState state = stairs.getDefaultState();

        if (anyStairsAllowed) {
            return state.with(StairsBlock.FACING, Direction.Type.HORIZONTAL.random(random))
                    .with(StairsBlock.HALF, BlockHalf.BOTTOM)
                    .with(StairsBlock.SHAPE, StairShape.values()[random.nextBetween(0, 4)]);
        } else {
            return state.with(StairsBlock.FACING, Direction.Type.HORIZONTAL.random(random))
                    .with(StairsBlock.HALF, Util.getRandom(BlockHalf.values(), random))
                    .with(StairsBlock.SHAPE, StairShape.values()[random.nextBetween(0, 2)]);
        }
        /*
        Direction direction = null;
        BlockHalf blockHalf = null;
        StairShape stairShape = null;

        boolean upStateBoolean = world.getBlockState(pos.up()).isOf(Blocks.AIR);
        boolean downStateBoolean = world.getBlockState(pos.down()).isOf(Blocks.AIR);
        boolean eastStateBoolean = world.getBlockState(pos.east()).isOf(Blocks.AIR);
        boolean westStateBoolean = world.getBlockState(pos.west()).isOf(Blocks.AIR);
        boolean northStateBoolean = world.getBlockState(pos.north()).isOf(Blocks.AIR);
        boolean southStateBoolean = world.getBlockState(pos.south()).isOf(Blocks.AIR);
        boolean southNorthBlockBoolean = !southStateBoolean && !northStateBoolean;
        boolean eastWestBlockBoolean = !westStateBoolean && !eastStateBoolean;

        System.out.println("Stairs on pos " + pos
                + " upStateBoolean is " + upStateBoolean
                + " downStateBoolean is " + downStateBoolean
                + " eastStateBoolean is " + eastStateBoolean
                + " westStateBoolean is " + westStateBoolean
                + " northStateBoolean is " + northStateBoolean
                + " southStateBoolean is " + southStateBoolean);

        if (upStateBoolean) {
            direction = Direction.Type.HORIZONTAL.random(random);
            stairShape = Util.getRandom(StairShape.values(), random);

            blockHalf = BlockHalf.TOP;
        }
        if (downStateBoolean) {
            if (blockHalf == BlockHalf.TOP) {
                blockHalf = random.nextFloat() >= 0.5F ? BlockHalf.BOTTOM : blockHalf;
            } else {
                blockHalf = BlockHalf.BOTTOM;
            }

            direction = Direction.Type.HORIZONTAL.random(random);
            stairShape = Util.getRandom(StairShape.values(), random);
        }

        if (blockHalf == null) {
            if (southNorthBlockBoolean || eastWestBlockBoolean) {
                if (southNorthBlockBoolean) {
                    stairShape = StairShape.values()[random.nextBetween(0, 2)];
                    if (eastStateBoolean) {
                        direction = Direction.EAST;
                    }
                    if (westStateBoolean) {
                        if (direction == Direction.EAST) {
                            direction = random.nextFloat() >= 0.5F ? Direction.WEST : direction;
                        } else {
                            direction = Direction.WEST;
                        }
                    }
                }
                if (eastWestBlockBoolean) {
                    stairShape = StairShape.values()[random.nextBetween(0, 2)];
                    if (northStateBoolean) {
                        direction = Direction.NORTH;
                    }
                    if (southStateBoolean) {
                        if (direction == Direction.NORTH) {
                            direction = random.nextFloat() >= 0.5F ? Direction.SOUTH : direction;
                        } else {
                            direction = Direction.SOUTH;
                        }
                    }
                }
            } else {
                stairShape = Util.getRandom(StairShape.values(), random);
                if (eastStateBoolean) {
                    if (northStateBoolean) {
                        direction = random.nextFloat() >= 0.5F ? Direction.EAST : Direction.NORTH;
                    } else {
                        direction = random.nextFloat() >= 0.5F ? Direction.EAST : Direction.SOUTH;
                    }
                } else {
                    if (northStateBoolean) {
                        direction = random.nextFloat() >= 0.5F ? Direction.WEST : Direction.NORTH;
                    } else {
                        direction = random.nextFloat() >= 0.5F ? Direction.WEST : Direction.SOUTH;
                    }
                }
            }

            blockHalf = Util.getRandom(BlockHalf.values(), random);
        }

        if (direction != null && stairShape != null && blockHalf != null) {
            return state
                    .with(StairsBlock.FACING, direction)
                    .with(StairsBlock.HALF, blockHalf)
                    .with(StairsBlock.SHAPE, stairShape);
        } else {
            System.out.println("Stairs not setupped :(");
            return state
                    .with(StairsBlock.FACING, Direction.Type.HORIZONTAL.random(random))
                    .with(StairsBlock.HALF, Util.getRandom(BlockHalf.values(), random))
                    .with(StairsBlock.SHAPE, Util.getRandom(StairShape.values(), random));
        }

        //return state.with(StairsBlock.FACING, Direction.Type.HORIZONTAL.random(random)).with(StairsBlock.HALF, Util.getRandom(BlockHalf.values(), random)).with(StairsBlock.SHAPE, StairShape.values()[random.nextBetween(0, 2)]);

         */
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos pivot, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo currentBlockInfo, StructurePlacementData data) {
        Random random = data.getRandom(currentBlockInfo.pos());
        BlockState blockState = currentBlockInfo.state();
        BlockPos blockPos = currentBlockInfo.pos();
        BlockState blockState2 = null;

        boolean bl = false;
        if (blockState.isOf(Blocks.RED_CONCRETE)) {
            blockState = this.processStoneSetup(random, true);
            bl = true;
        } else if (blockState.isOf(Blocks.YELLOW_CONCRETE)) {
            blockState = this.processStoneSetup(random, false);
            bl = true;
        }

        if (blockState.isIn(BlockTags.STAIRS)) {
            if (blockState.isOf(Blocks.STONE_BRICK_STAIRS)) {
                blockState2 = this.processBrickStairs(random, blockState);
            } else if (blockState.isOf(Blocks.STONE_STAIRS)) {
                blockState2 = this.processStoneStairs(random, blockState);
            }

        } else if (blockState.isIn(BlockTags.SLABS)) {
            if (blockState.isOf(Blocks.STONE_BRICK_SLAB)) {
                blockState2 = this.processBrickSlabs(random, blockState);
            } else if (blockState.isOf(Blocks.STONE_SLAB)) {
                blockState2 = this.processStoneSlabs(random, blockState);
            } else if (blockState.isOf(Blocks.SMOOTH_STONE_SLAB) || blockState.isOf(Blocks.SMOOTH_STONE)) {
                blockState2 = this.processRoofSlabs(random, blockState, pos);
            }

        } else if (blockState.isIn(BlockTags.WALLS)) {
            if (blockState.isOf(Blocks.STONE_BRICK_WALL)) {
                blockState2 = this.processBrickWalls(random, blockState);
            } else if (blockState.isOf(Blocks.COBBLESTONE_WALL)) {
                blockState2 = this.processStoneWalls(random, blockState);
            }

        } else {
            if (blockState.isOf(Blocks.STONE_BRICKS)) {
                blockState2 = this.processBricks(random);
            } else if (blockState.isOf(Blocks.STONE)) {
                blockState2 = this.processStone(random, bl);
            }

        }

        /*if (blockState.isOf(Blocks.BARREL)) {
            int i = random.nextInt(3);
            Identifier lootId = new Identifier("lonsbattletowers", "chests/supply_barrel/wood_supply");
            switch (i) {
                case 0 -> {}
                case 1 -> lootId = new Identifier("lonsbattletowers", "chests/supply_barrel/fish_supply");
                case 2 -> lootId = new Identifier("lonsbattletowers", "chests/supply_barrel/material_supply");
            }
            LootableContainerBlockEntity.setLootTable(world, random, blockPos, lootId);

        }*/
        /*else if (blockState.isOf(Blocks.CHEST)) {
            Identifier lootId = new Identifier("lonsbattletowers", "chests/supply_barrel/wood_supply");

            MinecraftServer server = ((World) world).getServer();
            LootManager lootManager = server.getLootManager();
            LootTable lootTable = lootManager.getLootTable(lootId);
            LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder((ServerWorld) world)
                    .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(blockPos))
                    .luck(blockPos.getY())
                    .build(LootContextTypes.CHEST);
            List<ItemStack> list = lootTable.generateLoot(lootContextParameterSet);

            LootableContainerBlockEntity chest = ((LootableContainerBlockEntity) world.getBlockEntity(blockPos));
            for (int n = 0; n < chest.size(); n++) {
                chest.setStack(n, list.get(list.size()));
            }
            System.out.println("Это ГОООЛ");
        }

         */

        if (bl && blockState2 == null) {
            return new StructureTemplate.StructureBlockInfo(blockPos, blockState, currentBlockInfo.nbt());
        }

        return blockState2 != null ? new StructureTemplate.StructureBlockInfo(blockPos, blockState2, currentBlockInfo.nbt()) : currentBlockInfo;
    }

    @Nullable
    private BlockState processStoneSetup(Random random, boolean bl) {
        BlockState blockState;
        if (random.nextFloat() >= this.crackness) {
            blockState = Blocks.STONE.getDefaultState();
        } else {
            blockState = randomStairProperties(random, Blocks.STONE_STAIRS, bl);
        }
        return blockState;
    }

    @Nullable
    private BlockState processBricks(Random random) {
        if (random.nextFloat() >= 0.4F) {
            return null;
        } else {
            BlockState[] blockStates = new BlockState[]{Blocks.CRACKED_STONE_BRICKS.getDefaultState(), randomStairProperties(random, Blocks.STONE_BRICK_STAIRS, false)};
            BlockState[] blockStates2 = new BlockState[]{Blocks.MOSSY_STONE_BRICKS.getDefaultState(), randomStairProperties(random, Blocks.MOSSY_STONE_BRICK_STAIRS, false)};
            return this.processChancesBricks(random, blockStates, blockStates2);
        }
    }
    @Nullable
    private BlockState processStone(Random random, boolean bl) {
        if (random.nextFloat() >= 0.4F) {
            return null;
        } else {
            BlockState[] blockStates = new BlockState[]{Blocks.COBBLESTONE.getDefaultState(), randomStairProperties(random, Blocks.COBBLESTONE_STAIRS, false)};
            BlockState[] blockStates2 = new BlockState[]{Blocks.MOSSY_COBBLESTONE.getDefaultState(), randomStairProperties(random, Blocks.MOSSY_COBBLESTONE_STAIRS, false)};
            return this.processChancesStone(random, blockStates, blockStates2, bl);
        }
    }

    @Nullable
    private BlockState processBrickStairs(Random random, BlockState state) {
        Direction direction = state.get(StairsBlock.FACING);
        BlockHalf blockHalf = state.get(StairsBlock.HALF);
        StairShape stairShape = state.get(StairsBlock.SHAPE);
        if (random.nextFloat() >= 0.4F) {
            return null;
        } else {
            BlockState[] regularStates = new BlockState[]{null, Blocks.STONE_BRICK_SLAB.getDefaultState().with(SlabBlock.TYPE, blockHalf == BlockHalf.TOP ? SlabType.TOP : SlabType.BOTTOM)};
            BlockState[] mossyStates = new BlockState[]{(Blocks.MOSSY_STONE_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, direction)).with(StairsBlock.HALF, blockHalf).with(StairsBlock.SHAPE, stairShape), Blocks.MOSSY_STONE_BRICK_SLAB.getDefaultState().with(SlabBlock.TYPE, blockHalf == BlockHalf.TOP ? SlabType.TOP : SlabType.BOTTOM)};
            return random.nextFloat() < this.mossiness ? randomState(random, mossyStates, this.crackness/2) : randomState(random, regularStates, this.crackness/2);
        }
    }
    @Nullable
    private BlockState processStoneStairs(Random random, BlockState state) {
        Direction direction = state.get(StairsBlock.FACING);
        BlockHalf blockHalf = state.get(StairsBlock.HALF);
        StairShape stairShape = state.get(StairsBlock.SHAPE);
        if (random.nextFloat() >= 0.4F) {
            return null;
        } else {
            BlockState[] regularStates = new BlockState[]{Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, direction).with(StairsBlock.HALF, blockHalf).with(StairsBlock.SHAPE, stairShape), Blocks.COBBLESTONE_SLAB.getDefaultState().with(SlabBlock.TYPE, blockHalf == BlockHalf.TOP ? SlabType.TOP : SlabType.BOTTOM)};
            BlockState[] mossyStates = new BlockState[]{Blocks.MOSSY_COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, direction).with(StairsBlock.HALF, blockHalf).with(StairsBlock.SHAPE, stairShape), Blocks.MOSSY_COBBLESTONE_SLAB.getDefaultState().with(SlabBlock.TYPE, blockHalf == BlockHalf.TOP ? SlabType.TOP : SlabType.BOTTOM)};
            return processChancesStone(random, regularStates, mossyStates, false);
            //return random.nextFloat() < this.mossiness ? mossyStates[random.nextInt(mossyStates.length)] : regularStates[random.nextInt(regularStates.length)];
        }
    }

    @Nullable
    private BlockState processBrickSlabs(Random random, BlockState state) {
        SlabType type = state.get(SlabBlock.TYPE);
        if (random.nextFloat() >= 0.4F) {
            return null;
        } else {
            BlockState[] regularStates = new BlockState[]{Blocks.STONE_BRICK_SLAB.getDefaultState().with(SlabBlock.TYPE, type)};
            BlockState[] mossyStates = new BlockState[]{Blocks.MOSSY_STONE_BRICK_SLAB.getDefaultState().with(SlabBlock.TYPE, type)};
            return random.nextFloat() < this.mossiness ? randomState(random, mossyStates, this.crackness/2) : randomState(random, regularStates, this.crackness/2);
        }
    }
    @Nullable
    private BlockState processStoneSlabs(Random random, BlockState state) {
        SlabType type = state.get(SlabBlock.TYPE);
        if (random.nextFloat() >= 0.4F) {
            return null;
        } else {
            BlockState[] regularStates = new BlockState[]{Blocks.COBBLESTONE_SLAB.getDefaultState().with(SlabBlock.TYPE, type)};
            BlockState[] mossyStates = new BlockState[]{Blocks.MOSSY_COBBLESTONE_SLAB.getDefaultState().with(SlabBlock.TYPE, type)};
            return processChancesStone(random, regularStates, mossyStates, false);
        }
    }
    @Nullable
    private BlockState processRoofSlabs(Random random, BlockState state, BlockPos pos) {
        if (this.stairRemove && random.nextFloat() < 0.0008 * pos.getY()) {
            return Blocks.AIR.getDefaultState();
        }

        if (random.nextFloat() >= 0.1F) {
            return null;
        } else {
            if (state.isIn(BlockTags.SLABS)) {
                SlabType type = state.get(SlabBlock.TYPE);

                BlockState[] regularStates = new BlockState[]{Blocks.STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, type)};
                return regularStates[random.nextInt(regularStates.length)];
            } else {
                BlockState[] regularStates = new BlockState[]{Blocks.STONE.getDefaultState()};
                return regularStates[random.nextInt(regularStates.length)];
            }
        }
    }

    @Nullable
    private BlockState processBrickWalls(Random random, BlockState state) {
        WallShape eastShape = state.get(WallBlock.EAST_SHAPE);
        WallShape westShape = state.get(WallBlock.WEST_SHAPE);
        WallShape northShape = state.get(WallBlock.NORTH_SHAPE);
        WallShape southShape = state.get(WallBlock.SOUTH_SHAPE);
        if (random.nextFloat() >= 0.4F) {
            return null;
        } else {
            BlockState[] regularStates = new BlockState[]{null};
            BlockState[] mossyStates = new BlockState[]{Blocks.MOSSY_STONE_BRICK_WALL.getDefaultState().with(WallBlock.EAST_SHAPE, eastShape).with(WallBlock.WEST_SHAPE, westShape).with(WallBlock.NORTH_SHAPE, northShape).with(WallBlock.SOUTH_SHAPE, southShape)};
            return random.nextFloat() < this.mossiness ? randomState(random, mossyStates, this.crackness/2) : randomState(random, regularStates, this.crackness/2);
        }
    }
    @Nullable
    private BlockState processStoneWalls(Random random, BlockState state) {
        WallShape eastShape = state.get(WallBlock.EAST_SHAPE);
        WallShape westShape = state.get(WallBlock.WEST_SHAPE);
        WallShape northShape = state.get(WallBlock.NORTH_SHAPE);
        WallShape southShape = state.get(WallBlock.SOUTH_SHAPE);
        if (random.nextFloat() >= 0.4F) {
            return null;
        } else {
            BlockState[] regularStates = new BlockState[]{null};
            BlockState[] mossyStates = new BlockState[]{Blocks.MOSSY_COBBLESTONE_WALL.getDefaultState().with(WallBlock.EAST_SHAPE, eastShape).with(WallBlock.WEST_SHAPE, westShape).with(WallBlock.NORTH_SHAPE, northShape).with(WallBlock.SOUTH_SHAPE, southShape)};
            return random.nextFloat() < this.mossiness ? randomState(random, mossyStates, this.crackness/2) : randomState(random, regularStates, this.crackness/2);
        }
    }

    private BlockState processChancesBricks(Random random, BlockState[] regularStates, BlockState[] mossyStates) {
        return random.nextFloat() < this.mossiness ? randomState(random, mossyStates, this.crackness) : randomState(random, regularStates, this.crackness);
    }
    private BlockState processChancesStone(Random random, BlockState[] regularStates, BlockState[] mossyStates, boolean isBoulder) {
        float crackChance = isBoulder ? 0 : this.crackness/2;
        return random.nextFloat() < this.mossiness/2 ? randomState(random, mossyStates, crackChance) : randomState(random, regularStates, crackChance);
    }

    private static BlockState randomState(Random random, BlockState[] states, float crackChance) {
        if (random.nextFloat() < crackChance) {
            return states[random.nextInt(states.length)];
        } else {
            return states[0];
        }
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModStructures.TOWER_AGE_PROCESSOR;
    }
}
