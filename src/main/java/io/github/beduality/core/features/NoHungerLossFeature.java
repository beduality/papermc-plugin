package io.github.beduality.core.features;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import io.github.beduality.core.models.Feature;
import io.github.beduality.core.models.User;

public class NoHungerLossFeature extends Feature {

    private void resetFoodLevel(Player player) {
        player.setFoodLevel(20);
    }

    @Override
    public void onEnable() {
        getPhase().getGame().getPlayers().forEach(user -> {
            resetFoodLevel(user.getPlayer());
        });
    }

    @Override
    public void onJoin(User user) {
        resetFoodLevel(user.getPlayer());
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!isPlaying((Player) event.getEntity()))
            return;

        event.setCancelled(true);
    }
}
