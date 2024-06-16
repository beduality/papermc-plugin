package com.luisfuturist.core.features;

import org.bukkit.entity.Player;

import com.luisfuturist.core.models.Feature;
import com.luisfuturist.core.models.Phase;

public class ClearInventoryFeature extends Feature {

    public ClearInventoryFeature(Phase phase) {
        super(phase);
    }

    private void clearInventory(Player player) {
        player.getInventory().clear();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        getPhase().getPlayers().forEach(this::clearInventory);
    }

    @Override
    public void onJoin(Player player) {
        clearInventory(player);
    }
}
