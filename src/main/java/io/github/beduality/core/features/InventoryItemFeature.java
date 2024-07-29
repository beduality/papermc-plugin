package io.github.beduality.core.features;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import io.github.beduality.core.models.Feature;
import io.github.beduality.core.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemFeature extends Feature {

    @Getter @Setter
    private ItemStack itemStack;
    @Getter @Setter
    private int slotIndex = 0;

    public InventoryItemFeature(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    private void addItem(Player player) {
        player.getInventory().setItem(slotIndex, itemStack);
    }

    @Override
    public void onEnable() {
        getPhase().getGame().getPlayers().forEach(user -> {
            addItem(user.getPlayer());
        });
    }

    @Override
    public void onJoin(User user) {
        addItem(user.getPlayer());
    }

    public boolean hasClicked(PlayerInteractEvent event) {
        var player = event.getPlayer();

        if (!isPlaying(player)) {
            return false;
        }

        var item = event.getItem();

        if (item == null) {
            return false;
        }

        return item.isSimilar(itemStack);
    }
}
