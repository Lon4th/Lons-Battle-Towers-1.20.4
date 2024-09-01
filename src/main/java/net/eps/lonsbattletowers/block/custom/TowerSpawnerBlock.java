package net.eps.lonsbattletowers.block.custom;

import com.mojang.serialization.MapCodec;
import net.eps.lonsbattletowers.block.custom.spawner.TowerSpawnerEvent;
import net.eps.lonsbattletowers.block.custom.spawner.TowerSpawnerState;
import net.eps.lonsbattletowers.block.entity.ModBlockEntities;
import net.eps.lonsbattletowers.block.entity.TowerSpawnerBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TowerSpawnerBlock extends BlockWithEntity {
    public static final MapCodec<TowerSpawnerBlock> CODEC = TowerSpawnerBlock.createCodec(TowerSpawnerBlock::new);
    public static final EnumProperty<TowerSpawnerState> TOWER_SPAWNER_STATE = EnumProperty.of("tower_spawner_state", TowerSpawnerState.class);
    public static final EnumProperty<TowerSpawnerEvent> TOWER_SPAWNER_EVENT = EnumProperty.of("tower_spawner_event", TowerSpawnerEvent.class);

    public MapCodec<TowerSpawnerBlock> getCodec() {
        return CODEC;
    }

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
            blockEntityTicker = TowerSpawnerBlock.validateTicker(type, ModBlockEntities.TOWER_SPAWNER_BLOCK_ENTITY, (world2, pos, state2, blockEntity) -> blockEntity.getSpawner().tickServer(serverWorld, pos));
        } else {
            blockEntityTicker = TowerSpawnerBlock.validateTicker(type, ModBlockEntities.TOWER_SPAWNER_BLOCK_ENTITY, (world2, pos, state2, blockEntity) -> blockEntity.getSpawner().tickClient(world, pos));
        }
        return blockEntityTicker;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        Spawner.appendSpawnDataToTooltip(stack, tooltip, "SpawnData");
    }
}
