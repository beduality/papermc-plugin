package com.luisfuturist.core.features;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import com.luisfuturist.core.models.Feature;
import com.luisfuturist.core.models.User;

public class NoHungerLossFeature extends Feature {

    private void resetFoodLevel(Player player) {
        player.setFoodLevel(20);
    }

    @Override
    public void onEnable() {
        super.onEnable();

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
