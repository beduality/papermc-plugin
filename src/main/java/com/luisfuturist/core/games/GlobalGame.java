package com.luisfuturist.core.games;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.luisfuturist.core.CorePlugin;
import com.luisfuturist.core.models.Game;
import com.luisfuturist.core.models.User;
import com.luisfuturist.core.phases.GlobalPhase;

import lombok.Getter;

public class GlobalGame extends Game {

    private JavaPlugin plugin;

    @Getter
    private Game hub;

    public GlobalGame(JavaPlugin plugin, Game hub) {
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
            var user = CorePlugin.userManager.login(player);
            play(user);
        });
    }

    @Override
    public void onDisable() {
        super.onDisable();

        Bukkit.getOnlinePlayers().forEach(player -> {
            var user = CorePlugin.userManager.login(player);
            leave(user);
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

        CorePlugin.plugin.getLogger().info(getName() + " | leave with players size: " + getPlayers().size());

        if (hub != null) {
            hub.play(user);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();

        var user = CorePlugin.userManager.getOrLogin(player);
        play(user);
        
        event.joinMessage(null);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent event) {
        var user = CorePlugin.userManager.getUser(event.getPlayer());
        
        leave(user);
        CorePlugin.userManager.logout(user);

        event.quitMessage(null);
    }
}
