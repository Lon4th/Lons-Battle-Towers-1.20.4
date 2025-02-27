package net.eps.lonsbattletowers.mixins;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockEntity.class)
public abstract class BlockEntityAccessor {

    @Shadow @Nullable protected World world;

    @Shadow @Final protected BlockPos pos;


    /*@Unique
    @Mutable
    @Final
    private float battleTowerRewardChance;

    public LootContextParametersMixin(float battleTowerRewardChance) {
        this.battleTowerRewardChance = battleTowerRewardChance;
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void initLoot(ServerWorld world, Map parameters, Map dynamicDrops, float luck, CallbackInfo ci) {
        this.battleTowerRewardChance = luck;
    }

    @Unique
    public float getBattleTowerRewardChance() {
        return battleTowerRewardChance;
    }

    @Mixin(targets = "Lnet/minecraft/loot/context/LootContextParameterSet$Builder;")
    public static class BuilderMixin {
        private float battleTowerRewardChance = 0.0f;

        public BuilderMixin battleTowerRewardChance(float battleTowerRewardChance) {
            this.battleTowerRewardChance = battleTowerRewardChance;
            return this;
        }
    }*/
}
