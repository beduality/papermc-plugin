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

    public void onJoin(User user) {
    }

    public void onLeave(User user) {
    }

    public boolean isPlaying(Player player) {
        return phase.getGame()
                .getPlayers()
                .stream()
                .anyMatch(user -> user.getId()
                        .equals(player.getUniqueId()));
    }

    public User getUser(Player player) {
        return getPhase().getGame().getUser(player);
    }
}
