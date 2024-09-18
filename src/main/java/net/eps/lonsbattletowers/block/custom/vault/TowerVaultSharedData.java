package net.eps.lonsbattletowers.block.custom.vault;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.minecraft.util.Uuids.INT_STREAM_CODEC;

public class TowerVaultSharedData {
    static final String SHARED_DATA_KEY = "shared_data";
    public static final Codec<Set<UUID>> SET_CODEC = Codec.list(Uuids.INT_STREAM_CODEC).xmap(Sets::newHashSet, Lists::newArrayList);
    public static Codec<TowerVaultSharedData> codec = RecordCodecBuilder.create(
            instance -> instance.group(
                            /*ItemStack.CODEC.optionalFieldOf("display_item")
                                    .xmap(optional -> optional.orElse(ItemStack.EMPTY), stack -> stack.isEmpty() ? Optional.empty() : Optional.of(stack)).forGetter(data -> data.displayItem),*/
                            SET_CODEC.optionalFieldOf("connected_players", Sets.newHashSet()).forGetter(data -> data.connectedPlayers),
                            Codec.DOUBLE.optionalFieldOf("connected_particles_range", Double.valueOf(TowerVaultConfig.DEFAULT.deactivationRange())).forGetter(data -> data.connectedParticlesRange),
                            Codec.BOOL.optionalFieldOf("on_activated", false).forGetter(data -> data.on_activated),
                            Codec.BOOL.optionalFieldOf("on_deactivated", false).forGetter(data -> data.on_deactivated)
                    )
                    .apply(instance, TowerVaultSharedData::new)
    );
    //private ItemStack displayItem = ItemStack.EMPTY;
    private Set<UUID> connectedPlayers = new ObjectLinkedOpenHashSet<>();
    private double connectedParticlesRange = TowerVaultConfig.DEFAULT.deactivationRange();
    public boolean on_activated = false;
    public boolean on_deactivated = false;
    public boolean dirty;

    TowerVaultSharedData(Set<UUID> connectedPlayers, double connectedParticlesRange, boolean on_activated, boolean on_deactivated) {
        //this.displayItem = displayItem;
        this.connectedPlayers.addAll(connectedPlayers);
        this.connectedParticlesRange = connectedParticlesRange;
        this.on_activated = on_activated;
        this.on_deactivated = on_deactivated;
    }

    public TowerVaultSharedData() {
    }

    /*public ItemStack getDisplayItem() {
        return this.displayItem;
    }

    public boolean hasDisplayItem() {
        return !this.displayItem.isEmpty();
    }

    public void setDisplayItem(ItemStack stack) {
        if (!ItemStack.areEqual(this.displayItem, stack)) {
            this.displayItem = stack.copy();
            this.markDirty();
        }
    }*/

    boolean hasConnectedPlayers() {
        return !this.connectedPlayers.isEmpty();
    }

    public Set<UUID> getConnectedPlayers() {
        return this.connectedPlayers;
    }

    public double getConnectedParticlesRange() {
        return this.connectedParticlesRange;
    }

    public boolean getActivated() {
        return this.on_activated;
    }

    public boolean getDeactivated() {
        return this.on_deactivated;
    }

    public void setActivated(boolean activated) {
        this.on_activated = activated;
    }

    public void setDeactivated(boolean deactivated) {
        this.on_deactivated = deactivated;
    }

    public void updateConnectedPlayers(ServerWorld world, BlockPos pos, TowerVaultServerData serverData, TowerVaultConfig config, double radius) {
        Set<UUID> set = (Set<UUID>)config.playerDetector()
                .detect(world, pos, (int) radius)
                .stream()
                .filter(uuid -> !serverData.getRewardedPlayers().contains(uuid))
                .collect(Collectors.toSet());
        if (!this.connectedPlayers.equals(set)) {
            this.connectedPlayers = set;
            this.markDirty();
        }
    }

    private void markDirty() {
        this.dirty = true;
    }

    public void copyFrom(TowerVaultSharedData data) {
        //this.displayItem = data.displayItem;
        this.connectedPlayers = data.connectedPlayers;
        this.connectedParticlesRange = data.connectedParticlesRange;
    }
}
