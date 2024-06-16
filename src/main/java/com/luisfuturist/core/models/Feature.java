package com.luisfuturist.core.models;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import lombok.Getter;

public abstract class Feature implements Listener {

    @Getter Phase phase;

    public Feature(Phase phase) {
        this.phase = phase;
    }

    public void onEnable() {}
    public void onDisable() {}

    public void onJoin(Player player) {}
    public void onLeave(Player player) {}

    public boolean hasPlayer(Player player) {
        return phase.getGame().getPlayers().contains(player);
    }
}
