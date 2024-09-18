package net.eps.lonsbattletowers.block.custom.vault;

import net.eps.lonsbattletowers.block.entity.TowerVaultBlockEntity;
import net.eps.lonsbattletowers.entity.ModEntities;
import net.eps.lonsbattletowers.entity.custom.TowerMimicEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldEvents;

import java.util.Objects;

public enum TowerVaultState implements StringIdentifiable {
    INACTIVE("inactive", TowerVaultState.Light.HALF_LIT) {
        @Override
        protected void onChangedTo(ServerWorld world, BlockPos pos, TowerVaultConfig config, TowerVaultSharedData sharedData) {
            //sharedData.setDisplayItem(ItemStack.EMPTY);
            //world.syncWorldEvent(/*WorldEvents.VAULT_DEACTIVATES*/ WorldEvents.TRIAL_SPAWNER_EJECTS_ITEM, pos, 0);

            sharedData.setDeactivated(true);
        }
    },
    ACTIVE("active", TowerVaultState.Light.LIT) {
        @Override
        protected void onChangedTo(ServerWorld world, BlockPos pos, TowerVaultConfig config, TowerVaultSharedData sharedData) {
            /*if (!sharedData.hasDisplayItem()) {
                TowerVaultBlockEntity.Server.updateDisplayItem(world, this, config, sharedData, pos);
            }*/
            //world.syncWorldEvent(/*WorldEvents.VAULT_ACTIVATES*/ WorldEvents.TRIAL_SPAWNER_DETECTS_PLAYER, pos, 0);

            sharedData.setActivated(true);
        }
    },
    UNLOCKING("unlocking", TowerVaultState.Light.LIT) {
        @Override
        protected void onChangedTo(ServerWorld world, BlockPos pos, TowerVaultConfig config, TowerVaultSharedData sharedData) {
            world.playSound(null, pos, /*SoundEvents.BLOCK_VAULT_INSERT_ITEM*/ SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS);
        }
    },
    EJECTING("ejecting", TowerVaultState.Light.LIT) {
        @Override
        protected void onChangedTo(ServerWorld world, BlockPos pos, TowerVaultConfig config, TowerVaultSharedData sharedData) {
            world.playSound(null, pos, /*SoundEvents.BLOCK_VAULT_OPEN_SHUTTER*/ SoundEvents.BLOCK_ANVIL_BREAK, SoundCategory.BLOCKS);
        }

        @Override
        protected void onChangedFrom(ServerWorld world, BlockPos pos, TowerVaultConfig config, TowerVaultSharedData sharedData) {
            world.playSound(null, pos, /*SoundEvents.BLOCK_VAULT_CLOSE_SHUTTER*/ SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.BLOCKS);
        }
    };

    private final String id;
    private final TowerVaultState.Light light;

    TowerVaultState(final String id, final TowerVaultState.Light light) {
        this.id = id;
        this.light = light;
    }

    @Override
    public String asString() {
        return this.id;
    }

    public int getLuminance() {
        return this.light.luminance;
    }

    public TowerVaultState update(ServerWorld world, BlockPos pos, TowerVaultConfig config, TowerVaultServerData serverData, TowerVaultSharedData sharedData) {
        return switch (this) {
            case INACTIVE -> updateActiveState(world, pos, config, serverData, sharedData, config.activationRange());
            case ACTIVE -> updateActiveState(world, pos, config, serverData, sharedData, config.deactivationRange());
            case UNLOCKING -> {
                //System.out.println("Unlocking started at " + world.getTime());
                serverData.setStateUpdatingResumeTime(world.getTime() + 20L);
                yield EJECTING;
            }
            case EJECTING -> {
                if (serverData.getItemsToEject().isEmpty()) {
                    serverData.finishEjecting();
                    yield updateActiveState(world, pos, config, serverData, sharedData, config.deactivationRange());
                } else {
                    //System.out.println("Item ejected at " + world.getTime());
                    float f = serverData.getEjectSoundPitchModifier();
                    this.ejectItem(world, pos, Objects.equals(serverData.getSpawnedMimicTarget(), "null") ? serverData.getItemToEject() : Items.AIR.getDefaultStack(), f);
                    //sharedData.setDisplayItem(serverData.getItemToDisplay());
                    serverData.setStateUpdatingResumeTime(world.getTime() + 20L);
                    yield EJECTING;
                }
            }
        };
    }

    private static TowerVaultState updateActiveState(ServerWorld world, BlockPos pos, TowerVaultConfig config, TowerVaultServerData serverData, TowerVaultSharedData sharedData, double radius) {
        sharedData.updateConnectedPlayers(world, pos, serverData, config, radius);
        serverData.setStateUpdatingResumeTime(world.getTime() + 20L);
        return sharedData.hasConnectedPlayers() ? ACTIVE : INACTIVE;
    }

    public void onStateChange(ServerWorld world, BlockPos pos, TowerVaultState newState, TowerVaultConfig config, TowerVaultSharedData sharedData) {
        this.onChangedFrom(world, pos, config, sharedData);
        newState.onChangedTo(world, pos, config, sharedData);
    }

    protected void onChangedTo(ServerWorld world, BlockPos pos, TowerVaultConfig config, TowerVaultSharedData sharedData) {
    }

    protected void onChangedFrom(ServerWorld world, BlockPos pos, TowerVaultConfig config, TowerVaultSharedData sharedData) {
    }

    private void ejectItem(ServerWorld world, BlockPos pos, ItemStack stack, float pitchModifier) {
        ItemDispenserBehavior.spawnItem(world, stack, 2, Direction.UP, Vec3d.ofBottomCenter(pos).offset(Direction.UP, 1.2));
        world.syncWorldEvent(/*WorldEvents.VAULT_EJECTS_ITEM*/ WorldEvents.DISPENSER_DISPENSES, pos, 0);
        world.playSound(null, pos, /*SoundEvents.BLOCK_VAULT_EJECT_ITEM*/ SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0F, 0.8F + 0.4F * pitchModifier);
    }

    static enum Light {
        HALF_LIT(6),
        LIT(12);

        final int luminance;

        private Light(final int luminance) {
            this.luminance = luminance;
        }
    }
}
