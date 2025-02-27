package net.eps.lonsbattletowers.mixins.optional;

import net.eps.lonsbattletowers.LonsBattleTowers;
import net.eps.lonsbattletowers.loot.ModLootContextParameters;
import net.eps.lonsbattletowers.loot.ModLootContextTypes;
import net.eps.lonsbattletowers.mixins.BlockEntityAccessor;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.zestyblaze.lootr.api.LootrAPI;
import net.zestyblaze.lootr.block.entities.LootrChestBlockEntity;
import net.zestyblaze.lootr.config.ConfigManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LootrChestBlockEntity.class, remap = false)
public abstract class LootrChestBlockEntityMixin extends LootableContainerBlockEntityAccessor {

	@Shadow protected Identifier savedLootTable;

	@Shadow protected long seed;

	@Inject(method = "unpackLootTable", at = @At(value = "HEAD"))
	private void unpackLootTableMixin(PlayerEntity player, Inventory inventory, Identifier overrideTable, long seed, CallbackInfo ci) {
		if (this.world != null && this.savedLootTable != null && this.savedLootTable.getNamespace().contains(LonsBattleTowers.MOD_ID) && this.world.getServer() != null) {
			LootTable loottable = this.world.getServer().getLootManager().getLootTable(overrideTable != null ? overrideTable : this.savedLootTable);
			float rewardChance = Math.min((Math.max((this.pos.getY() - 80), 1.0f) / 1000.0f) * 8.0f, 0.9f);
			if (loottable == LootTable.EMPTY) {
				Logger var10000 = LootrAPI.LOG;
				RegistryKey var10001 = this.world.getRegistryKey();
				var10000.error("Unable to fill loot chest in " + var10001 + " at " + this.pos + " as the loot table '" + (overrideTable != null ? overrideTable : this.savedLootTable) + "' couldn't be resolved! Please search the loot table in `latest.log` to see if there are errors in loading.");
				if (ConfigManager.get().debug.report_invalid_tables) {
					player.sendMessage(Text.translatable("lootr.message.invalid_table", new Object[]{(overrideTable != null ? overrideTable : this.savedLootTable).toString()}).setStyle(ConfigManager.get().notifications.disable_message_styles ? Style.EMPTY : Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.DARK_RED)).withBold(true)), false);
				}
			}

			if (player instanceof ServerPlayerEntity) {
				Criteria.PLAYER_GENERATES_CONTAINER_LOOT.trigger((ServerPlayerEntity)player, overrideTable != null ? overrideTable : this.lootTableId);
			}

			LootContextParameterSet.Builder builder = (new LootContextParameterSet.Builder((ServerWorld)this.world))
					.add(LootContextParameters.ORIGIN, Vec3d.ofCenter(this.pos))
					.add(ModLootContextParameters.BATTLE_TOWER_REWARD_CHANCE, rewardChance);
			if (player != null) {
				builder.luck(player.getLuck()).add(LootContextParameters.THIS_ENTITY, player);
			}

			loottable.supplyInventory(inventory, builder.build(ModLootContextTypes.TOWER_CHEST), LootrAPI.getLootSeed(seed == Long.MIN_VALUE ? this.seed : seed));
			this.savedLootTable = null;
		}
	}

/*

	@ModifyVariable(method = "unpackLootTable", at = @At(value = "STORE"), ordinal = 0)
	private LootContextParameterSet.Builder unpackLootTableMixin1(LootContextParameterSet.Builder builder) {
		if (this.savedLootTable.getNamespace().contains(LonsBattleTowers.MOD_ID)) {
			float rewardChance = Math.min((Math.max((this.pos.getY() - 80), 1.0f) / 1000.0f) * 8.0f, 0.9f);

			builder.add(ModLootContextParameters.BATTLE_TOWER_REWARD_CHANCE, rewardChance);
		}

		return builder;
	}

	@Redirect(method = "unpackLootTable", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/context/LootContextParameterSet$Builder;build(Lnet/minecraft/loot/context/LootContextType;)Lnet/minecraft/loot/context/LootContextParameterSet;"))
	private LootContextParameterSet unpackLootTableMixin2(LootContextParameterSet.Builder instance, LootContextType contextType) {
		return this.savedLootTable.getNamespace().contains(LonsBattleTowers.MOD_ID) ? instance.build(ModLootContextTypes.TOWER_CHEST) : instance.build(contextType);
	}

 */
}
