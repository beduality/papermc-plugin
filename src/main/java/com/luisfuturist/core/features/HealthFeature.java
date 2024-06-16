package com.luisfuturist.core.features;

import org.bukkit.entity.Player;

import com.luisfuturist.core.models.Feature;
import com.luisfuturist.core.models.Phase;

public class HealthFeature extends Feature {

    public HealthFeature(Phase phase) {
        super(phase);
    }

    private void heal(Player player) {
        player.setHealth(20.0);
        player.setSaturation(20.0f);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        getPhase().getPlayers().forEach(this::heal);
    }
    
    @Override
    public void onJoin(Player event) {
        heal(event.getPlayer());
    }
}
