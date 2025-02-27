package net.eps.lonsbattletowers.mixins;

import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.loot.ModLootContextParameters;
import net.eps.lonsbattletowers.loot.ModLootContextTypes;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LootableContainerBlockEntity.class)
public abstract class LootableContainerBlockEntityMixin extends BlockEntityAccessor {

    @Shadow @Nullable protected Identifier lootTableId;

    @Shadow protected long lootTableSeed;

    @Inject(method = "checkLootInteraction", at = @At(value = "HEAD"))
    private void checkLootInteraction(PlayerEntity player, CallbackInfo ci) {
        if (this.lootTableId != null && this.lootTableId.getNamespace().contains(LonsBattleTowers.MOD_ID) && this.world.getServer() != null) {
             if (this.world.getBlockEntity(this.pos) instanceof ChestBlockEntity) {
                LootTable lootTable = this.world.getServer().getLootManager().getLootTable(this.lootTableId);
                if (player instanceof ServerPlayerEntity) {
                    Criteria.PLAYER_GENERATES_CONTAINER_LOOT.trigger((ServerPlayerEntity) player, this.lootTableId);
                }
                this.lootTableId = null;
                float rewardChance = Math.min((Math.max((this.pos.getY() - 80), 1.0f) / 1000.0f) * 8.0f, 0.9f);
                LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder((ServerWorld) this.world)
                        .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(this.pos))
                        .add(ModLootContextParameters.BATTLE_TOWER_REWARD_CHANCE, rewardChance);
                if (player != null) {
                    builder.luck(player.getLuck()).add(LootContextParameters.THIS_ENTITY, player);
                }
                lootTable.supplyInventory((Inventory) this, builder.build(ModLootContextTypes.TOWER_CHEST), this.lootTableSeed);
            }
        }
    }

    /*@Unique private boolean isLootFromLBT;

    @Redirect(method = "checkLootInteraction(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/block/entity/LootableContainerBlockEntity;lootTableId:Lnet/minecraft/util/Identifier;", opcode = Opcodes.PUTFIELD))
    public void checkLootInteraction(LootableContainerBlockEntity instance, Identifier value) {
        if (instance instanceof ChestBlockEntity && this.lootTableId.getNamespace().contains(LonsBattleTowers.MOD_ID)) {
            this.isLootFromLBT = true;
        }

        this.lootTableId = null;
    }

    @ModifyVariable(method = "checkLootInteraction(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At("STORE"), ordinal = 0)
    public LootContextParameterSet.Builder checkLootInteractionMixin(LootContextParameterSet.Builder builder) {
        if (this.isLootFromLBT) {
            float rewardChance = Math.min((Math.max((this.pos.getY() - 80), 1.0f) / 1000.0f) * 8.0f, 0.9f);

            builder.add(ModLootContextParameters.BATTLE_TOWER_REWARD_CHANCE, rewardChance);
        }

        return builder;
    }

    @Redirect(method = "checkLootInteraction", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/context/LootContextParameterSet$Builder;build(Lnet/minecraft/loot/context/LootContextType;)Lnet/minecraft/loot/context/LootContextParameterSet;"))
    public LootContextParameterSet checkLootInteraction(LootContextParameterSet.Builder instance, LootContextType contextType) {
        if (this.isLootFromLBT) {
            contextType = ModLootContextTypes.TOWER_CHEST;
        }

        return instance.build(contextType);
    }*/
}
