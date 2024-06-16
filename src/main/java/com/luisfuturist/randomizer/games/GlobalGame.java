package com.luisfuturist.randomizer.games;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.luisfuturist.core.models.Game;
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
        setFirstPhase(globalPhase);
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

        Bukkit.getOnlinePlayers().forEach(this::play);
    }

    @Override
    public void play(Player player) {
        super.play(player);

        if (hub != null) {
            hub.play(player);
        }
    }

    @Override
    public void leave(Player player) {
        super.leave(player);

        if (hub != null) {
            hub.play(player);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        play(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        leave(event.getPlayer());
    }
}
