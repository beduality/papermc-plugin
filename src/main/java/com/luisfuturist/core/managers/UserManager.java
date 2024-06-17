package com.luisfuturist.core.managers;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.luisfuturist.core.models.User;

public class UserManager {
    
    private HashMap<UUID, User> users = new HashMap<>();
    
    public User login(Player player) {
        var user = new User(player);
        users.put(player.getUniqueId(), user);

        return user;
    }

    public User getOrLogin(Player player) {
        var user = getUser(player);

        if(user != null) {
            return user;
        }

        return login(player);
    }

    public User logout(User user) {
        return users.remove(user.getPlayer().getUniqueId());
    }

    public User logout(Player player) {
        return users.remove(player.getUniqueId());
    }

    public User getUser(Player player) {
        return users.get(player.getUniqueId());
    }
}
