package net.eps.lonsbattletowers.mixins.optional;

import net.eps.lonsbattletowers.mixins.BlockEntityAccessor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LootableContainerBlockEntity.class)
public abstract class LootableContainerBlockEntityAccessor extends BlockEntityAccessor {

    @Shadow @Nullable protected Identifier lootTableId;
}
