package com.luisfuturist.core.models;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class Feature implements Listener, Handler, Joinable {

    private @Getter @Setter(value = AccessLevel.PROTECTED) Phase phase;

    public Feature() {
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onJoin(User user) {
    }

    @Override
    public void onLeave(User user) {
    }

    public boolean isPlaying(Player player) {
        return phase.getGame()
                .getPlayers()
                .stream()
                .anyMatch(user -> user.getPlayer().getUniqueId()
                        .equals(player.getUniqueId()));
    }

    public User getUser(Player player) {
        return getPhase()
                .getGame()
                .getPlayers()
                .stream()
                .filter(u -> u.getPlayer().getUniqueId().equals(player.getUniqueId()))
                .findAny()
                .orElse(null);
    }
}
