package com.luisfuturist.core.features;

import org.bukkit.entity.Player;

import com.luisfuturist.core.models.Feature;

public class HealthFeature extends Feature {

    private void heal(Player player) {
        player.setHealth(20.0);
        player.setSaturation(20.0f);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        getPhase().getGame().getPlayers().forEach(this::heal);
    }

    @Override
    public void onJoin(Player event) {
        heal(event.getPlayer());
    }
}
