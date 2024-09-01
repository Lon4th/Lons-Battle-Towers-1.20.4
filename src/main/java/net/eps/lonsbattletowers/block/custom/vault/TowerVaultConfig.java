package net.eps.lonsbattletowers.block.custom.vault;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.eps.lonsbattletowers.LonsBattleTowers;
import net.minecraft.block.spawner.EntityDetector;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.Optional;

public record TowerVaultConfig(Identifier lootTable, double activationRange, double deactivationRange, ItemStack keyItem, Optional<Identifier> overrideLootTableToDisplay, EntityDetector playerDetector
                               //,EntityDetector.Selector entitySelector
) {
    static final String CONFIG_KEY = "config";
    public static TowerVaultConfig DEFAULT = new TowerVaultConfig();
    public static Codec<TowerVaultConfig> codec = RecordCodecBuilder.<TowerVaultConfig>create(
                    instance -> instance.group(
                                    Identifier.CODEC.optionalFieldOf("loot_table", DEFAULT.lootTable()).forGetter(TowerVaultConfig::lootTable),
                                    Codec.DOUBLE.optionalFieldOf("activation_range", Double.valueOf(DEFAULT.activationRange())).forGetter(TowerVaultConfig::activationRange),
                                    Codec.DOUBLE.optionalFieldOf("deactivation_range", Double.valueOf(DEFAULT.deactivationRange())).forGetter(TowerVaultConfig::deactivationRange),
                                    ItemStack.CODEC.optionalFieldOf("key_item")
                                            .xmap(optional -> optional.orElse(ItemStack.EMPTY), stack -> stack.isEmpty() ? Optional.empty() : Optional.of(stack)).forGetter(TowerVaultConfig::keyItem),
                                    Identifier.CODEC.optionalFieldOf("override_loot_table_to_display").forGetter(TowerVaultConfig::overrideLootTableToDisplay)
                            )
                            .apply(instance, TowerVaultConfig::new)
            ).flatXmap(TowerVaultConfig::validate, TowerVaultConfig::validate);

    private TowerVaultConfig() {
        this(new Identifier(LonsBattleTowers.MOD_ID, "chests/tower_vault"),
             4.0,
             4.5,
             new ItemStack(Items.TRIAL_KEY),
             Optional.empty(),
             (world, center, radius) -> world.getPlayers(player -> player.getBlockPos().isWithinDistance(center, radius) && !player.isSpectator()).stream().map(Entity::getUuid).toList()
             //EntityDetector.Selector.IN_WORLD
        );
    }

    public TowerVaultConfig(Identifier lootTable, double activationRange, double deactivationRange, ItemStack keyItem, Optional<Identifier> overrideLootTableToDisplay) {
        this(lootTable, activationRange, deactivationRange, keyItem, overrideLootTableToDisplay, DEFAULT.playerDetector()
                //,DEFAULT.entitySelector()
        );
    }

    private DataResult<TowerVaultConfig> validate() {
        return this.activationRange > this.deactivationRange
                ? DataResult.error(() -> "Activation range must (" + this.activationRange + ") be less or equal to deactivation range (" + this.deactivationRange + ")")
                : DataResult.success(this);
    }
}
