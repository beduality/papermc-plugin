package com.luisfuturist.core.models;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.luisfuturist.core.utils.StringUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class Feature implements Listener, Handler, Joinable {

    @Setter
    private String name;

    private @Getter @Setter(value = AccessLevel.PROTECTED) Phase phase;

    public Feature() {
    }

    public String getName() {
        if(name == null) {
            name = StringUtils.removeSuffix(getClass().getName(), "Feature");
        }

        return name;
    }

    public void onCreate() {
        
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
        return phase.getGame()
                .getPlayers()
                .stream()
                .filter(u -> u.getPlayer().getUniqueId().equals(player.getUniqueId()))
                .findFirst()
                .orElse(null);
    }
}
