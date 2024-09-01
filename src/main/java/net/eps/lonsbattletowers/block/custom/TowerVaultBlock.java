package net.eps.lonsbattletowers.block.custom;

import com.mojang.serialization.MapCodec;
import net.eps.lonsbattletowers.block.custom.spawner.TowerSpawnerState;
import net.eps.lonsbattletowers.block.custom.vault.TowerVaultState;
import net.eps.lonsbattletowers.block.entity.ModBlockEntities;
import net.eps.lonsbattletowers.block.entity.TowerVaultBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TowerVaultBlock extends BlockWithEntity {
    public static final MapCodec<TowerVaultBlock> CODEC = createCodec(TowerVaultBlock::new);
    public static final EnumProperty<TowerVaultState> TOWER_VAULT_STATE = EnumProperty.of("tower_vault_state", TowerVaultState.class);
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    @Override
    public MapCodec<TowerVaultBlock> getCodec() {
        return CODEC;
    }

    public TowerVaultBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(
                this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(TOWER_VAULT_STATE, TowerVaultState.INACTIVE)
        );
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);

        if (stack.isEmpty() || state.get(TOWER_VAULT_STATE) != TowerVaultState.ACTIVE) {
            return ActionResult.PASS;
        } else if (world instanceof ServerWorld serverWorld) {
            if (serverWorld.getBlockEntity(pos) instanceof TowerVaultBlockEntity vaultBlockEntity) {
                TowerVaultBlockEntity.Server.tryUnlock(
                        serverWorld, pos, state, vaultBlockEntity.getConfig(), vaultBlockEntity.getServerData(), vaultBlockEntity.getSharedData(), player, stack
                );
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.PASS;
            }
        } else {
            return ActionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TowerVaultBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, TOWER_VAULT_STATE);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world instanceof ServerWorld serverWorld ?
                validateTicker(type, ModBlockEntities.TOWER_VAULT_BLOCK_ENTITY, (worldx, pos, statex, blockEntity) -> TowerVaultBlockEntity.Server.tick(serverWorld, pos, statex, blockEntity.getConfig(), blockEntity.getServerData(), blockEntity.getSharedData())) :
                validateTicker(type, ModBlockEntities.TOWER_VAULT_BLOCK_ENTITY, (worldx, pos, statex, blockEntity) -> TowerVaultBlockEntity.Client.tick(worldx, pos, statex, blockEntity.getSharedData())
        );
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
