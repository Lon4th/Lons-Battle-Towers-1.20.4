package net.eps.lonsbattletowers.block.custom.spawner;

import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface TowerSpawner {
    void setEntityType(EntityType<?> type, Random random);

    static void appendSpawnDataToTooltip(ItemStack stack, List<Text> tooltip, String spawnDataKey) {
        Text text = getSpawnedEntityText(stack, spawnDataKey);
        if (text != null) {
            tooltip.add(text);
        } else {
            tooltip.add(ScreenTexts.EMPTY);
            tooltip.add(Text.translatable("block.minecraft.spawner.desc1").formatted(Formatting.GRAY));
            tooltip.add(ScreenTexts.space().append(Text.translatable("block.minecraft.spawner.desc2").formatted(Formatting.BLUE)));
        }

    }

    @Nullable
    static Text getSpawnedEntityText(ItemStack stack, String spawnDataKey) {
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null) {
            Identifier identifier = getSpawnedEntityId(nbtCompound, spawnDataKey);
            if (identifier != null) {
                return (Text) Registries.ENTITY_TYPE.getOrEmpty(identifier).map((entityType) -> {
                    return Text.translatable(entityType.getTranslationKey()).formatted(Formatting.GRAY);
                }).orElse((MutableText) null);
            }
        }

        return null;
    }

    @Nullable
    private static Identifier getSpawnedEntityId(NbtCompound nbt, String spawnDataKey) {
        if (nbt.contains(spawnDataKey, 10)) {
            String string = nbt.getCompound(spawnDataKey).getCompound("entity").getString("id");
            return Identifier.tryParse(string);
        } else {
            return null;
        }
    }
}
