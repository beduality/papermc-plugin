package com.luisfuturist.core.models;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Getter;

public class Game {
    
    @Getter private final List<Player> players = new ArrayList<>();

    @Getter private Phase currentPhase;
    private Phase globalPhase;

    private JavaPlugin plugin;

    @Getter private boolean isRunning;

    public Game(JavaPlugin plugin, Phase globalPhase) {
        this.plugin = plugin;
        this.globalPhase = globalPhase;
    }

    public void onEnable() {
        if(globalPhase != null) {
            Bukkit.getPluginManager().registerEvents(globalPhase, plugin);
        }
    }

    public void onDisable() {
        if(globalPhase != null) {
            HandlerList.unregisterAll(globalPhase);
        }
    }

    public void onStart() {
        isRunning = true;

        plugin.getLogger().info("[RandomizerCore] Game has started.");
    }

    public void onFinish() {
        isRunning = false;

        plugin.getLogger().info("[RandomizerCore] Game has ended.");
    }

    private void finishCurrentPhase() {
        if (currentPhase != null) {
            currentPhase.onFinish();

            currentPhase.getFeatures().forEach(HandlerList::unregisterAll);

            HandlerList.unregisterAll(currentPhase);
        }
    }

    private void startNextPhase(Phase phase) {
        this.currentPhase = phase;

        for(var feature : phase.getFeatures()) {
            Bukkit.getPluginManager().registerEvents(feature, this.plugin);
        }

        phase.onStart();

        Bukkit.getPluginManager().registerEvents(phase, this.plugin);
    }

    private void nextPhase(Phase phase) {
        finishCurrentPhase();
        startNextPhase(phase);
    }

    private void runPhase(Phase phase) {
        nextPhase(phase);
        phase.setRunning(true);

        plugin.getLogger().info("[RandomizerCore] Phase " + phase.getName() + " has begun.");

        new BukkitRunnable() {
            @Override
            public void run() {
                phase.setRunning(false);
                plugin.getLogger().info("[RandomizerCore] Phase " + phase.getName() + " has ended.");

                if (phase.getNextPhase() != null) {
                    runPhase(phase.getNextPhase());
                } else {
                    finishCurrentPhase();
                    onFinish();
                }
            }
        }.runTaskLater(plugin, phase.getDuration());
    }

    public void start(Phase firstPhase) {
        onStart();

        runPhase(firstPhase);
    }

    public Phase createPhase(Phase phase) {
        phase.setGame(this);
        return phase;
    }

    public void play(Player player) {
        if(currentPhase.isAllowJoin()) {
            players.add(player);
            
            currentPhase.onJoin(player);
            
            for(var feature : currentPhase.getFeatures()) {
                feature.onJoin(player);
            }
        }
    }

    public void leave(Player player) {
        players.remove(player);
            
        currentPhase.onLeave(player);
        
        for(var feature : currentPhase.getFeatures()) {
            feature.onLeave(player);
        }
    }
}
