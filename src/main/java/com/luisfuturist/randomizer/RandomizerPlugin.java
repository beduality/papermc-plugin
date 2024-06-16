package com.luisfuturist.randomizer;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.luisfuturist.core.models.Game;
import com.luisfuturist.randomizer.managers.ItemManager;
import com.luisfuturist.randomizer.phases.GlobalPhase;
import com.luisfuturist.randomizer.phases.GracePhase;
import com.luisfuturist.randomizer.phases.LobbyPhase;

public class RandomizerPlugin extends JavaPlugin implements Listener {

    public static JavaPlugin plugin;
    public static Random random;

    public static ItemManager itemManager;
    public static Game game;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        disableAdvancements();
        
        plugin = this;
        random = new Random();
        
        itemManager = new ItemManager();
        
        game = new Game(this, new GlobalPhase());
        game.onEnable();
        initGame(game);

        Bukkit.getPluginManager().registerEvents(this, this);

        Bukkit.getOnlinePlayers().forEach(game::play);
    }

    @Override
    public void onDisable() {
        game.onDisable();
    }

    private void initGame(Game game) {
        var gracePhase = game.createPhase(new GracePhase());
        gracePhase.setDuration(20 * 5);

        var lobbyPhase = game.createPhase(new LobbyPhase());
        lobbyPhase.setNextPhase(gracePhase);
        lobbyPhase.setDuration(20 * 10);

        game.start(lobbyPhase);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(game != null) {
            game.play(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(game != null) {
            game.leave(event.getPlayer());
        }
    }

    private void disableAdvancements() {
        if (plugin == null)
            return;

        plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "gamerule announceAdvancements false");
    }
}