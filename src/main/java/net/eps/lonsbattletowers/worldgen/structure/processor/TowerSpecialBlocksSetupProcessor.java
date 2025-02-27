package net.eps.lonsbattletowers.worldgen.structure.processor;

import com.mojang.serialization.Codec;
import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.block.ModBlocks;
import net.eps.lonsbattletowers.block.custom.TowerSpawnerBlock;
import net.eps.lonsbattletowers.block.custom.spawner.TowerSpawner;
import net.eps.lonsbattletowers.worldgen.structure.ModStructures;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChiseledBookshelfBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtType;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

public class TowerSpecialBlocksSetupProcessor extends StructureProcessor {
    public static final Codec<TowerSpecialBlocksSetupProcessor> CODEC = Codec.unit(TowerSpecialBlocksSetupProcessor::new);

    public TowerSpecialBlocksSetupProcessor() {
    }

    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(WorldView world, BlockPos pos, BlockPos pivot, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo currentBlockInfo, StructurePlacementData data) {
        Random random = data.getRandom(currentBlockInfo.pos());
        BlockState blockState = currentBlockInfo.state();
        BlockPos blockPos = currentBlockInfo.pos();
        NbtCompound nbt = currentBlockInfo.nbt();

        if (blockState.isOf(ModBlocks.TOWER_SPAWNER)) {
            BlockEntity blockEntity = ((TowerSpawnerBlock) blockState.getBlock()).createBlockEntity(blockPos, blockState);

            if (blockEntity instanceof TowerSpawner towerSpawner) {
                int i = random.nextInt(6);
                EntityType entityType = EntityType.ZOMBIE;
                switch (i) {
                    case 0 -> {}
                    case 1 -> entityType = EntityType.SKELETON;
                    case 2 -> entityType = EntityType.SPIDER;
                    case 3 -> entityType = EntityType.CAVE_SPIDER;
                    case 4 -> entityType = EntityType.VINDICATOR;
                    case 5 -> entityType = EntityType.PILLAGER;
                }
                towerSpawner.setEntityType(entityType, random);
                nbt = blockEntity.createNbt().copyFrom(currentBlockInfo.nbt());
            }

            return new StructureTemplate.StructureBlockInfo(blockPos, blockState, nbt);
        } else if (blockState.isOf(Blocks.BARREL)) {
            int i = random.nextInt(3);
            Identifier lootId = new Identifier(LonsBattleTowers.MOD_ID, "chests/supply_barrel/wood_supply");
            switch (i) {
                case 0 -> {}
                case 1 -> lootId = new Identifier(LonsBattleTowers.MOD_ID, "chests/supply_barrel/fish_supply");
                case 2 -> lootId = new Identifier(LonsBattleTowers.MOD_ID, "chests/supply_barrel/material_supply");
            }
            if (world.getBlockEntity(blockPos) != null) {
                LootableContainerBlockEntity.setLootTable(world, random, blockPos, lootId);
            } else {
                BlockEntity blockEntity = ((BarrelBlock) blockState.getBlock()).createBlockEntity(blockPos, blockState);

                if (blockEntity instanceof LootableContainerBlockEntity containerBlockEntity) {
                    containerBlockEntity.setLootTable(lootId, random.nextLong());
                    NbtCompound nbtCompound = containerBlockEntity.createNbt();

                    return new StructureTemplate.StructureBlockInfo(blockPos, blockState, nbtCompound);
                }
            }

            return new StructureTemplate.StructureBlockInfo(blockPos, blockState, nbt);
        } else if (blockState.isOf(Blocks.CHISELED_BOOKSHELF)) {
            BlockEntity blockEntity = BlockEntity.createFromNbt(blockPos, blockState, currentBlockInfo.nbt());

            if (blockEntity instanceof ChiseledBookshelfBlockEntity bookshelfBlockEntity) {
                NbtList nbtList = new NbtList();

                for (int i = 0; i < bookshelfBlockEntity.size(); i++) {
                    if (random.nextFloat() <= 0.5F) {
                        continue;
                    }
                    int r = random.nextInt(8);

                    ItemStack stack = Items.BOOK.getDefaultStack();
                    String id = new Identifier("minecraft", "book").toString();
                    switch (r) {
                        case 0, 1, 2, 3 -> {}
                        case 4 -> id = new Identifier("minecraft", "writable_book").toString();
                        case 5, 6, 7 -> {
                            id = new Identifier("minecraft", "enchanted_book").toString();
                            stack = EnchantmentHelper.enchant(random, stack, 5, false);
                        }
                    }

                    NbtCompound nbtCompound = new NbtCompound();
                    nbtCompound.putInt("Slot", i);
                    nbtCompound.putString("id", id);
                    nbtCompound.putInt("Count", 1);
                    if (stack.hasNbt()) {
                        nbtCompound.put("tag", stack.getNbt());
                    }

                    nbtList.add(nbtCompound);

                    BooleanProperty booleanProperty = ChiseledBookshelfBlock.SLOT_OCCUPIED_PROPERTIES.get(i);
                    blockState = blockState.with(booleanProperty, true);
                }

                NbtCompound bookshelfNbt = bookshelfBlockEntity.createNbt();
                bookshelfNbt.put("Items", nbtList);

                return new StructureTemplate.StructureBlockInfo(blockPos, blockState, bookshelfNbt);
            }
        }
        /*else if (blockState.isOf(Blocks.CHEST)) {

            //Identifier lootId = new Identifier("lonsbattletowers", "chests/tower_chest");

            //LootableContainerBlockEntity.setLootTable(world, random, blockPos, lootId);
            MinecraftServer server = null;
            if (world instanceof ChunkRegion chunkRegion) {
                server = chunkRegion.getServer();
            } else if (world instanceof World world1) {
                server = world1.getServer();
            }

            if (LonsBattleTowers.IS_OPTIONAL_MOD_LOADED) {
                try {
                    Class<?> optionalClass = Class.forName("net.zestyblaze.lootr.block.entities.LootrChestBlockEntity");
                    Method optionalMethod = optionalClass.getMethod("unpackLootTable");
                    server.getPlayerManager().broadcast(Text.literal("optionalMethod is " + optionalMethod), false);
                    server.getPlayerManager().broadcast(Text.literal("optionalClass is " + optionalClass), false);
                } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    server.getPlayerManager().broadcast(Text.literal("Exception found. This - " + e), false);
                }
            }

            int y = blockPos.getY();
            float rewardChance = Math.min((Math.max((y - 80), 1.0f) / 1000.0f) * 8.0f, 0.9f);

            BlockEntity blockEntity = BlockEntity.createFromNbt(blockPos, blockState, currentBlockInfo.nbt());
            Identifier lootId = new Identifier("lonsbattletowers", "chests/tower_chest");
            MinecraftServer server = null;
            if (world instanceof ChunkRegion chunkRegion) {
                server = chunkRegion.getServer();
            } else if (world instanceof World world1) {
                server = world1.getServer();
            }

            if (server == null) return currentBlockInfo;

            if (blockEntity instanceof LootableContainerBlockEntity chest) {

                for (int n = 0; n < chest.size(); n++) {
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
                   LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder(server.getOverworld())
                            .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(blockPos))
                            .add(ModLootContextParameters.BATTLE_TOWER_REWARD_CHANCE, rewardChance)
                            .luck(rewardChance)
                            .build(LootContextTypes.CHEST);
                   //List<ItemStack> list = lootTable.generateLoot(lootContextParameterSet);
                   //System.out.println("list is " + list);

                   ItemStack item;
                   if (random.nextFloat() > Math.max(rewardChance, 0.15f)) {
                       item = ItemStack.EMPTY;
                   } else {
                       item = lootTable.generateLoot(lootContextParameterSet).get(0);
                       if (item.isOf(Items.ENCHANTED_BOOK)) {
                           item = EnchantmentHelper.enchant(random, Items.BOOK.getDefaultStack(), 5, false);
                       }
                   }
                   chest.setStack(n, item);
                }

                return new StructureTemplate.StructureBlockInfo(blockPos, blockState, chest.createNbt());
            }

        }*/
        return currentBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModStructures.TOWER_SPAWNER_SETUP_PROCESSOR;
    }
}
