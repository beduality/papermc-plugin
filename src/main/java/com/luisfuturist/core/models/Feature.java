package com.luisfuturist.core.models;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class Feature implements Listener {

    private @Getter @Setter(value = AccessLevel.PROTECTED) Phase phase;

    public Feature() {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onJoin(Player player) {
    }

    public void onLeave(Player player) {
    }

    public boolean hasPlayer(Player player) {
        return phase.getGame().getPlayers().contains(player);
    }
}
