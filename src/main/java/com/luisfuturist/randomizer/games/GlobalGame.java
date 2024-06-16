package com.luisfuturist.randomizer.games;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.luisfuturist.core.models.Game;
import com.luisfuturist.core.models.User;
import com.luisfuturist.randomizer.RandomizerPlugin;
import com.luisfuturist.randomizer.phases.GlobalPhase;

import lombok.Getter;

public class GlobalGame extends Game {

    private JavaPlugin plugin;

    @Getter
    private HubGame hub;

    public GlobalGame(JavaPlugin plugin, HubGame hub) {
        super("Global", plugin);
        this.plugin = plugin;
        this.hub = hub;

        var globalPhase = createPhase(new GlobalPhase());
        setGlobalPhase(globalPhase);
    }

    private void disableAdvancements() {
        if (plugin == null)
            return;

        plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "gamerule announceAdvancements false");
    }

    @Override
    public void onEnable() {
        super.onEnable();

        disableAdvancements();

        Bukkit.getOnlinePlayers().forEach(player -> {
            addUser(player);
            play(getUser(player));
        });
    }

    @Override
    public void play(User user) {
        joinGlobal(user);
        super.play(user);

        if (hub != null) {
            hub.play(user);
        }
    }

    @Override
    public void leave(User user) {
        leaveGlobal(user);
        super.leave(user);

        RandomizerPlugin.plugin.getLogger().info(getName() + " | leave with players size: " + getPlayers().size());

        if (hub != null) {
            hub.play(user);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();

        addUser(player);
        play(getUser(player));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent event) {
        var user = getUser(event.getPlayer());
        
        leave(user);
        removeUser(user);
    }
}
