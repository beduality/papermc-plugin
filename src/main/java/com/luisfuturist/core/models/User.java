package com.luisfuturist.core.models;

import org.bukkit.entity.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class User {
    
    private Player player;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return player.getUniqueId().equals(user.getPlayer().getUniqueId());
    }

    @Override
    public int hashCode() {
        return getPlayer().getUniqueId().hashCode();
    }
}
