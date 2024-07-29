package io.github.beduality.core.features;

import org.bukkit.entity.Player;

import io.github.beduality.core.models.Feature;
import io.github.beduality.core.models.User;

public class ClearInventoryFeature extends Feature {

    private void clearInventory(Player player) {
        player.getInventory().clear();
    }

    @Override
    public void onEnable() {
        getPhase().getGame().getPlayers().forEach(user -> {
            clearInventory(user.getPlayer());
        });
    }

    @Override
    public void onJoin(User user) {
        clearInventory(user.getPlayer());
    }
}
