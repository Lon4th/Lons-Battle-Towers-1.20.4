package net.eps.lonsbattletowers.block.entity;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.eps.lonsbattletowers.block.custom.TowerVaultBlock;
import net.eps.lonsbattletowers.block.custom.vault.TowerVaultConfig;
import net.eps.lonsbattletowers.block.custom.vault.TowerVaultServerData;
import net.eps.lonsbattletowers.block.custom.vault.TowerVaultSharedData;
import net.eps.lonsbattletowers.block.custom.vault.TowerVaultState;
import net.eps.lonsbattletowers.particle.ModParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public class TowerVaultBlockEntity extends BlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final TowerVaultServerData serverData = new TowerVaultServerData();
    private final TowerVaultSharedData sharedData = new TowerVaultSharedData();
    private TowerVaultConfig config = TowerVaultConfig.DEFAULT;

    public TowerVaultBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TOWER_VAULT_BLOCK_ENTITY, pos, state);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return Util.make(new NbtCompound(), nbt -> nbt.put("shared_data", encodeValue(TowerVaultSharedData.codec, this.sharedData, nbt)));
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("config", encodeValue(TowerVaultConfig.codec, this.config, nbt));
        nbt.put("shared_data", encodeValue(TowerVaultSharedData.codec, this.sharedData, nbt));
        nbt.put("server_data", encodeValue(TowerVaultServerData.codec, this.serverData, nbt));
    }

    private static <T> NbtElement encodeValue(Codec<T> codec, T value, NbtCompound nbt) {
        return codec.encodeStart(NbtOps.INSTANCE, value).get()
                .ifLeft(nbtElement -> nbt.copyFrom((NbtCompound)nbtElement))
                .ifRight(partialResult -> LOGGER.warn("Failed to encode TowerSpawner {}", (Object)partialResult.message())).orThrow();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        DynamicOps<NbtElement> dynamicOps = NbtOps.INSTANCE;
        if (nbt.contains("server_data")) {
            TowerVaultServerData.codec.parse(dynamicOps, nbt.get("server_data")).resultOrPartial(LOGGER::error).ifPresent(this.serverData::copyFrom);
        }

        if (nbt.contains("config")) {
            TowerVaultConfig.codec.parse(dynamicOps, nbt.get("config")).resultOrPartial(LOGGER::error).ifPresent(config -> this.config = config);
        }

        if (nbt.contains("shared_data")) {
            TowerVaultSharedData.codec.parse(dynamicOps, nbt.get("shared_data")).resultOrPartial(LOGGER::error).ifPresent(this.sharedData::copyFrom);
        }
    }

    @Nullable
    public TowerVaultServerData getServerData() {
        return this.world != null && !this.world.isClient ? this.serverData : null;
    }

    public TowerVaultSharedData getSharedData() {
        return this.sharedData;
    }

    public TowerVaultConfig getConfig() {
        return this.config;
    }

    @VisibleForTesting
    public void setConfig(TowerVaultConfig config) {
        this.config = config;
    }

    public static final class Client {
        private static final int field_48870 = 20;
        private static final float field_48871 = 0.5F;
        private static final float field_48872 = 0.02F;
        private static final int field_48873 = 20;
        private static final int field_48874 = 20;

        public static void tick(World world, BlockPos pos, BlockState state, TowerVaultSharedData sharedData) {
            //clientData.rotateDisplay();
            if (world.getTime() % 20L == 0L) {
                spawnConnectedParticles(world, pos, state, sharedData);
            }

            spawnAmbientParticles(world, pos, state.get(TowerVaultBlock.TOWER_VAULT_STATE), ParticleTypes.SOUL_FIRE_FLAME);
            playAmbientSound(world, pos, state.get(TowerVaultBlock.TOWER_VAULT_STATE));
        }

        public static void spawnActivateParticles(World world, BlockPos pos, BlockState state, TowerVaultSharedData sharedData, ParticleEffect particle) {
            spawnConnectedParticles(world, pos, state, sharedData);
            Random random = world.random;

            for (int i = 0; i < 20; i++) {
                Vec3d vec3d = getRegularParticlesPos(pos, random);
                world.addParticle(ParticleTypes.SMOKE, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0.0, 0.0, 0.0);
                world.addParticle(particle, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0.0, 0.0, 0.0);
            }
        }

        public static void spawnDeactivateParticles(World world, BlockPos pos, ParticleEffect particle) {
            Random random = world.random;

            for (int i = 0; i < 20; i++) {
                Vec3d vec3d = getDeactivateParticlesPos(pos, random);
                Vec3d vec3d2 = new Vec3d(random.nextGaussian() * 0.02, random.nextGaussian() * 0.02, random.nextGaussian() * 0.02);
                world.addParticle(particle, vec3d.getX(), vec3d.getY(), vec3d.getZ(), vec3d2.getX(), vec3d2.getY(), vec3d2.getZ());
            }
        }

        private static void spawnAmbientParticles(World world, BlockPos pos, TowerVaultState state, ParticleEffect particle) {
            Random random = world.getRandom();
            if (random.nextFloat() <= 0.5F) {
                Vec3d vec3d = getRegularParticlesPos(pos, random);
                world.addParticle(ParticleTypes.SMOKE, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0.0, 0.0, 0.0);
                if (state.equals(TowerVaultState.ACTIVE)) {
                    world.addParticle(particle, vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0.0, 0.0, 0.0);
                }
            }
        }

        private static void spawnConnectedParticlesFor(World world, Vec3d pos, PlayerEntity player) {
            Random random = world.random;
            Vec3d vec3d = pos.relativize(player.getPos().add(0.0, (double)(player.getHeight() / 2.0F), 0.0));
            int i = MathHelper.nextInt(random, 2, 5);

            for (int j = 0; j < i; j++) {
                Vec3d vec3d2 = vec3d.addRandom(random, 1.0F);
                world.addParticle(ModParticles.TOWER_VAULT_CONNECTION, pos.getX(), pos.getY(), pos.getZ(), vec3d2.getX(), vec3d2.getY(), vec3d2.getZ());
            }
        }

        private static void spawnConnectedParticles(World world, BlockPos pos, BlockState state, TowerVaultSharedData sharedData) {
            Set<UUID> set = sharedData.getConnectedPlayers();
            if (!set.isEmpty()) {
                Vec3d vec3d = getConnectedParticlesOrigin(pos, state.get(TowerVaultBlock.FACING));

                for (UUID uUID : set) {
                    PlayerEntity playerEntity = world.getPlayerByUuid(uUID);
                    if (playerEntity != null && isPlayerWithinConnectedParticlesRange(pos, sharedData, playerEntity)) {
                        spawnConnectedParticlesFor(world, vec3d, playerEntity);
                    }
                }
            }
        }

        private static boolean isPlayerWithinConnectedParticlesRange(BlockPos pos, TowerVaultSharedData sharedData, PlayerEntity player) {
            return player.getBlockPos().getSquaredDistance(pos) <= MathHelper.square(sharedData.getConnectedParticlesRange());
        }

        private static void playAmbientSound(World world, BlockPos pos, TowerVaultState state) {
            if (state.equals(TowerVaultState.ACTIVE)) {
                Random random = world.getRandom();
                if (random.nextFloat() <= 0.02F) {
                    world.playSoundAtBlockCenter(
                            pos, /*SoundEvents.BLOCK_VAULT_AMBIENT*/ SoundEvents.AMBIENT_UNDERWATER_LOOP, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false
                    );
                }
            }
        }

        /*public static boolean hasDisplayItem(TowerVaultSharedData sharedData) {
            return sharedData.hasDisplayItem();
        }*/

        private static Vec3d getDeactivateParticlesPos(BlockPos pos, Random random) {
            return Vec3d.of(pos).add(MathHelper.nextDouble(random, 0.4, 0.6), MathHelper.nextDouble(random, 0.4, 0.6), MathHelper.nextDouble(random, 0.4, 0.6));
        }

        private static Vec3d getRegularParticlesPos(BlockPos pos, Random random) {
            return Vec3d.of(pos).add(MathHelper.nextDouble(random, 0.1, 0.9), MathHelper.nextDouble(random, 0.25, 0.75), MathHelper.nextDouble(random, 0.1, 0.9));
        }

        private static Vec3d getConnectedParticlesOrigin(BlockPos pos, Direction direction) {
            return Vec3d.ofBottomCenter(pos).add((double)direction.getOffsetX() * 0.5, 1.75, (double)direction.getOffsetZ() * 0.5);
        }
    }

    public static final class Server {
        private static final int UNLOCK_TIME = 14;
        private static final int DISPLAY_UPDATE_INTERVAL = 20;
        private static final int FAILED_UNLOCK_COOLDOWN = 15;

        public static void tick(ServerWorld world, BlockPos pos, BlockState state, TowerVaultConfig config, TowerVaultServerData serverData, TowerVaultSharedData sharedData) {
            TowerVaultState vaultState = state.get(TowerVaultBlock.TOWER_VAULT_STATE);

            /*if (shouldUpdateDisplayItem(world.getTime(), vaultState)) {
                updateDisplayItem(world, vaultState, config, sharedData, pos);
            }*/

            BlockState blockState = state;

            if (world.getTime() >= serverData.getStateUpdatingResumeTime()) {
                blockState = state.with(TowerVaultBlock.TOWER_VAULT_STATE, vaultState.update(world, pos, config, serverData, sharedData));
                if (!state.equals(blockState)) {
                    changeVaultState(world, pos, state, blockState, config, sharedData);
                }
            }

            if (serverData.dirty || sharedData.dirty) {
                TowerVaultBlockEntity.markDirty(world, pos, state);
                if (sharedData.dirty) {
                    world.updateListeners(pos, state, blockState, Block.NOTIFY_LISTENERS);
                }

                serverData.dirty = false;
                sharedData.dirty = false;
            }
        }

        public static void tryUnlock(ServerWorld world, BlockPos pos, BlockState state, TowerVaultConfig config, TowerVaultServerData serverData, TowerVaultSharedData sharedData, PlayerEntity player, ItemStack stack) {
            TowerVaultState vaultState = state.get(TowerVaultBlock.TOWER_VAULT_STATE);
            if (canBeUnlocked(config, vaultState)) {
                if (!isValidKey(config, stack)) {
                    playFailedUnlockSound(world, serverData, pos, /*SoundEvents.BLOCK_VAULT_INSERT_ITEM_FAIL*/ SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME);
                } else if (serverData.hasRewardedPlayer(player)) {
                    playFailedUnlockSound(world, serverData, pos, /*SoundEvents.BLOCK_VAULT_REJECT_REWARDED_PLAYER*/ SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK);
                } else {
                    List<ItemStack> list = generateLoot(world, config, pos, player);
                    if (!list.isEmpty()) {
                        player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                        if (!player.isCreative()) {
                            stack.decrement(config.keyItem().getCount());
                        }
                        unlock(world, state, pos, config, serverData, sharedData, list);
                        serverData.markPlayerAsRewarded(player);
                        sharedData.updateConnectedPlayers(world, pos, serverData, config, config.deactivationRange());
                    }
                }
            }
        }

        static void changeVaultState(ServerWorld world, BlockPos pos, BlockState oldState, BlockState newState, TowerVaultConfig config, TowerVaultSharedData sharedData) {
            TowerVaultState vaultState = oldState.get(TowerVaultBlock.TOWER_VAULT_STATE);
            TowerVaultState vaultState2 = newState.get(TowerVaultBlock.TOWER_VAULT_STATE);
            world.setBlockState(pos, newState, Block.NOTIFY_ALL);
            vaultState.onStateChange(world, pos, vaultState2, config, sharedData);
        }

        /*public static void updateDisplayItem(ServerWorld world, TowerVaultState state, TowerVaultConfig config, TowerVaultSharedData sharedData, BlockPos pos) {
            if (!canBeUnlocked(config, state)) {
                sharedData.setDisplayItem(ItemStack.EMPTY);
            } else {
                ItemStack itemStack = generateDisplayItem(world, pos, config.overrideLootTableToDisplay().orElse(config.lootTable()));
                sharedData.setDisplayItem(itemStack);
            }
        }*/

        /*private static ItemStack generateDisplayItem(ServerWorld world, BlockPos pos, Identifier lootTable) {
            LootTable lootTable2 = world.getServer().getLootManager().getLootTable(lootTable);
            LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder(world)
                    .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
                    .build(LootContextTypes.CHEST);
            List<ItemStack> list = lootTable2.generateLoot(lootContextParameterSet, world.getRandom().nextLong());

            return list.isEmpty() ? ItemStack.EMPTY : Util.getRandom(list, world.getRandom());
        }*/

        private static void unlock(ServerWorld world, BlockState state, BlockPos pos, TowerVaultConfig config, TowerVaultServerData serverData, TowerVaultSharedData sharedData, List<ItemStack> itemsToEject) {
            serverData.setItemsToEject(itemsToEject);
            //sharedData.setDisplayItem(serverData.getItemToDisplay());
            serverData.setStateUpdatingResumeTime(world.getTime() + 14L);
            changeVaultState(world, pos, state, state.with(TowerVaultBlock.TOWER_VAULT_STATE, TowerVaultState.UNLOCKING), config, sharedData);
        }

        private static List<ItemStack> generateLoot(ServerWorld world, TowerVaultConfig config, BlockPos pos, PlayerEntity player) {
            //List<ItemStack> list = new ObjectArrayList<>();
            LootTable lootTable = world.getServer().getLootManager().getLootTable(config.lootTable());
            LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder(world)
                    .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
                    .luck(player.getLuck())
                    .add(LootContextParameters.THIS_ENTITY, player)
                    .build(LootContextTypes.CHEST);

            /*for (int i = 0; i < world.getRandom().nextBetween(2, 3); i++) {
                list.addAll(lootTable.generateLoot(lootContextParameterSet, world.getRandom().nextLong()));
            }*/

            return lootTable.generateLoot(lootContextParameterSet);
        }

        private static boolean canBeUnlocked(TowerVaultConfig config, TowerVaultState state) {
            return config.lootTable() != LootTables.EMPTY && !config.keyItem().isEmpty() && state != TowerVaultState.INACTIVE;
        }

        private static boolean isValidKey(TowerVaultConfig config, ItemStack stack) {
            ItemStack otherStack = config.keyItem();

            return TowerVaultBlockEntity.areItemsAndNbtEqual(stack, otherStack) && stack.getCount() >= otherStack.getCount();
        }

        /*private static boolean shouldUpdateDisplayItem(long time, TowerVaultState state) {
            return time % 20L == 0L && state == TowerVaultState.ACTIVE;
        }*/

        private static void playFailedUnlockSound(ServerWorld world, TowerVaultServerData serverData, BlockPos pos, SoundEvent sound) {
            if (world.getTime() >= serverData.getLastFailedUnlockTime() + 15L) {
                world.playSound(null, pos, sound, SoundCategory.BLOCKS);
                serverData.setLastFailedUnlockTime(world.getTime());
            }
        }
    }

    public static boolean areItemsAndNbtEqual(ItemStack stack, ItemStack otherStack) {
        if (!stack.isOf(otherStack.getItem())) {
            return false;
        } else {
            return stack.isEmpty() && otherStack.isEmpty() ? true : Objects.equals(stack.getNbt(), otherStack.getNbt());
        }
    }

}
