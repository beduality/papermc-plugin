package com.luisfuturist.core.models;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Getter;
import lombok.Setter;

public class Game implements Listener {

    @Getter
    private final List<Player> players = new ArrayList<>();

    @Getter
    private Phase currentPhase;
    private Phase globalPhase;
    @Getter
    @Setter
    private Phase firstPhase;

    private JavaPlugin plugin;

    @Getter
    private boolean isRunning;
    @Getter
    private String name;

    public Game(String name, JavaPlugin plugin) {
        this.name = name;
        this.plugin = plugin;
    }

    public Game(String name, JavaPlugin plugin, Phase globalPhase) {
        this.name = name;
        this.plugin = plugin;
        this.globalPhase = globalPhase;
    }

    public void onEnable() {
        this.currentPhase = firstPhase;

        Bukkit.getPluginManager().registerEvents(this, plugin);

        if (globalPhase != null) {
            Bukkit.getPluginManager().registerEvents(globalPhase, plugin);
        }
    }

    public void onDisable() {
        currentPhase = null;

        HandlerList.unregisterAll(this);

        if (globalPhase != null) {
            HandlerList.unregisterAll(globalPhase);
        }
    }

    public void onStart() {
        isRunning = true;

        plugin.getLogger().info("[RandomizerCore] " + getName() + " | Game started.");
    }

    public void onFinish() {
        isRunning = false;

        finishCurrentPhase();

        plugin.getLogger().info("[RandomizerCore] " + getName() + " | Game has ended.");
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

        for (var feature : phase.getFeatures()) {
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

        plugin.getLogger().info("[RandomizerCore] " + getName() + " | Phase " + phase.getName() + " has begun.");

        if (!phase.isTimed()) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                phase.setRunning(false);
                plugin.getLogger()
                        .info("[RandomizerCore] " + getName() + " | Phase " + phase.getName() + " has ended.");

                if (phase.getNextPhase() != null) {
                    runPhase(phase.getNextPhase());
                } else {
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
        if (currentPhase != null) {
            if(currentPhase.isAllowJoin()) {
                players.add(player);
    
                currentPhase.onJoin(player);
    
                for (var feature : currentPhase.getFeatures()) {
                    feature.onJoin(player);
                }
            } else {
                plugin.getLogger().warning(getName() + " | It's not allowed to join in phase " + currentPhase.getName());
            }
        }
    }

    public void leave(Player player) {
        if (currentPhase != null) {
            players.remove(player);

            currentPhase.onLeave(player);

            for (var feature : currentPhase.getFeatures()) {
                feature.onLeave(player);
            }
        }
    }
}
