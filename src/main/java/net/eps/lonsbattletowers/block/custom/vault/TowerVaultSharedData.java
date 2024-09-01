package net.eps.lonsbattletowers.block.custom.vault;

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

public class TowerVaultSharedData {
    static final String SHARED_DATA_KEY = "shared_data";
    public static Codec<TowerVaultSharedData> codec = RecordCodecBuilder.create(
            instance -> instance.group(
                            /*ItemStack.CODEC.optionalFieldOf("display_item")
                                    .xmap(optional -> optional.orElse(ItemStack.EMPTY), stack -> stack.isEmpty() ? Optional.empty() : Optional.of(stack)).forGetter(data -> data.displayItem),*/
                            Uuids.SET_CODEC.optionalFieldOf("connected_players", Sets.newHashSet()).forGetter(data -> data.connectedPlayers),
                            Codec.DOUBLE.optionalFieldOf("connected_particles_range", Double.valueOf(TowerVaultConfig.DEFAULT.deactivationRange())).forGetter(data -> data.connectedParticlesRange)
                    )
                    .apply(instance, TowerVaultSharedData::new)
    );
    //private ItemStack displayItem = ItemStack.EMPTY;
    private Set<UUID> connectedPlayers = new ObjectLinkedOpenHashSet<>();
    private double connectedParticlesRange = TowerVaultConfig.DEFAULT.deactivationRange();
    public boolean dirty;

    TowerVaultSharedData(Set<UUID> connectedPlayers, double connectedParticlesRange) {
        //this.displayItem = displayItem;
        this.connectedPlayers.addAll(connectedPlayers);
        this.connectedParticlesRange = connectedParticlesRange;
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
