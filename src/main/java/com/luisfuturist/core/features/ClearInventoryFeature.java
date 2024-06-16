package com.luisfuturist.core.features;

import org.bukkit.entity.Player;

import com.luisfuturist.core.models.Feature;

public class ClearInventoryFeature extends Feature {

    private void clearInventory(Player player) {
        player.getInventory().clear();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        getPhase().getGame().getPlayers().forEach(this::clearInventory);
    }

    @Override
    public void onJoin(Player player) {
        clearInventory(player);
    }
}
