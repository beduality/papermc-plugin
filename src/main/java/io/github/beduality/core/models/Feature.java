package io.github.beduality.core.models;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import io.github.beduality.core.utils.ClassUtils;
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
            name = ClassUtils.getCleanName(this, "Feature");
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
        return phase.getGame().getUser(player);
    }
}
