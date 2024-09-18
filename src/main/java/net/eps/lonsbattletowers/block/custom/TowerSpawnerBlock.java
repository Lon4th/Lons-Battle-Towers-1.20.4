package net.eps.lonsbattletowers.block.custom;

import com.mojang.serialization.MapCodec;
import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.block.custom.spawner.TowerSpawner;
import net.eps.lonsbattletowers.block.custom.spawner.TowerSpawnerEvent;
import net.eps.lonsbattletowers.block.custom.spawner.TowerSpawnerState;
import net.eps.lonsbattletowers.block.entity.ModBlockEntities;
import net.eps.lonsbattletowers.block.entity.TowerSpawnerBlockEntity;
import net.eps.lonsbattletowers.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TowerSpawnerBlock extends BlockWithEntity {
    //public static final MapCodec<TowerSpawnerBlock> CODEC = TowerSpawnerBlock.createCodec(TowerSpawnerBlock::new);
    public static final EnumProperty<TowerSpawnerState> TOWER_SPAWNER_STATE = EnumProperty.of("tower_spawner_state", TowerSpawnerState.class);
    public static final EnumProperty<TowerSpawnerEvent> TOWER_SPAWNER_EVENT = EnumProperty.of("tower_spawner_event", TowerSpawnerEvent.class);

    //public MapCodec<TowerSpawnerBlock> getCodec() {
    //    return CODEC;
    //}

    public TowerSpawnerBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState())
                .with(TOWER_SPAWNER_STATE, TowerSpawnerState.INACTIVE)
                .with(TOWER_SPAWNER_EVENT, TowerSpawnerEvent.NOTHING));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TOWER_SPAWNER_STATE, TOWER_SPAWNER_EVENT);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient() && !player.isCreative()) {
            ServerWorld serverWorld = (ServerWorld) world;

            this.ejectItems(serverWorld, pos, generateLoot(serverWorld, pos, player));
        }

        super.onBreak(world, pos, state, player);
    }

    private static List<ItemStack> generateLoot(ServerWorld world, BlockPos pos, PlayerEntity player) {
        LootTable lootTable = world.getServer().getLootManager().getLootTable(new Identifier(LonsBattleTowers.MOD_ID, "chests/tower_spawner"));
        LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder(world).add(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos)).luck(player.getLuck()).add(LootContextParameters.THIS_ENTITY, player).build(LootContextTypes.CHEST);

        return lootTable.generateLoot(lootContextParameterSet);
    }

    private void ejectItems(ServerWorld world, BlockPos pos, List<ItemStack> items) {
        for (ItemStack stack : items) {
            ItemDispenserBehavior.spawnItem(world, stack, 2, Direction.UP, Vec3d.ofBottomCenter(pos).offset(Direction.UP, 1.2));
        }
        if (items.size() < 2 && world.getRandom().nextBoolean() || items.isEmpty()) {
            ItemDispenserBehavior.spawnItem(world, ModItems.TOWER_KEY.getDefaultStack(), 2, Direction.UP, Vec3d.ofBottomCenter(pos).offset(Direction.UP, 1.2));
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TowerSpawnerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        BlockEntityTicker<T> blockEntityTicker;
        if (world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)world;
            blockEntityTicker = TowerSpawnerBlock.checkType(type, ModBlockEntities.TOWER_SPAWNER_BLOCK_ENTITY, (world2, pos, state2, blockEntity) -> blockEntity.getSpawner().tickServer(serverWorld, pos));
        } else {
            blockEntityTicker = TowerSpawnerBlock.checkType(type, ModBlockEntities.TOWER_SPAWNER_BLOCK_ENTITY, (world2, pos, state2, blockEntity) -> blockEntity.getSpawner().tickClient(world, pos));
        }
        return blockEntityTicker;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        TowerSpawner.appendSpawnDataToTooltip(stack, tooltip, "SpawnData");
    }
}
