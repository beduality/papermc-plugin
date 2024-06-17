package com.luisfuturist.core.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Game implements Listener {

    private final Set<User> playerSet = new HashSet<>();

    @Getter
    private Phase currentPhase;
    @Getter
    @Setter
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

    public Set<User> getPlayers() {
        return new HashSet<>(playerSet); // Return a copy to prevent modification outside
    }

    public Phase createPhase(Phase phase) {
        phase.setGame(this);
        return phase;
    }

    private void enableFeatures(List<Feature> features) {
        for (var feature : features) {
            feature.onEnable();
            Bukkit.getPluginManager().registerEvents(feature, this.plugin);
        }
    }

    private void disableFeatures(List<Feature> features) {
        for (var feature : features) {
            feature.onDisable();
            HandlerList.unregisterAll(feature);
        }
    }

    public void onEnable() {
        currentPhase = firstPhase;

        Bukkit.getPluginManager().registerEvents(this, plugin);

        if (globalPhase != null) {
            enablePhase(globalPhase);
        }
    }

    public void onDisable() {
        currentPhase = null;

        HandlerList.unregisterAll(this);

        if (globalPhase != null) {
            disablePhase(globalPhase);
        }
    }

    public void onStart() {
        isRunning = true;

        plugin.getLogger().info("Core | " + getName() + " | Game started.");
    }

    public void onFinish() {
        isRunning = false;

        finishCurrentPhase();

        plugin.getLogger().info("Core | " + getName() + " | Game has ended.");
    }

    private void enablePhase(Phase phase) {
        Bukkit.getPluginManager().registerEvents(phase, this.plugin);
        enableFeatures(phase.getFeatures());
        phase.onStart();
    }

    private void disablePhase(Phase phase) {
        phase.onFinish();
        disableFeatures(phase.getFeatures());
        HandlerList.unregisterAll(phase);
    }

    private void finishCurrentPhase() {
        if (currentPhase != null) {
            disablePhase(currentPhase);
        }
    }

    private void startNextPhase(@NonNull Phase phase) {
        this.currentPhase = phase;
        enablePhase(phase);
    }

    private void nextPhase(Phase phase) {
        finishCurrentPhase();
        startNextPhase(phase);
    }

    private void runPhase(Phase phase) {
        nextPhase(phase);
        phase.setRunning(true);

        plugin.getLogger().info("Core | " + getName() + " | Phase " + phase.getName() + " has begun.");

        if (!phase.isTimed()) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                phase.setRunning(false);
                plugin.getLogger()
                        .info("Core | " + getName() + " | Phase " + phase.getName() + " has ended.");

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

        if (firstPhase != null) {
            runPhase(firstPhase);
        }
    }

    private void joinPhase(User user, Phase phase) {
        playerSet.add(user);

        phase.onJoin(user);

        for (var feature : phase.getFeatures()) {
            feature.onJoin(user);
        }
    }

    private void leavePhase(User user, Phase phase) {
        playerSet.remove(user);

        phase.onLeave(user);

        for (var feature : phase.getFeatures()) {
            feature.onLeave(user);
        }
    }

    public void joinGlobal(User user) {
        joinPhase(user, globalPhase);
    }

    public void leaveGlobal(User user) {
        leavePhase(user, globalPhase);
    }

    public void play(User user) {
        if (currentPhase == null) {
            return;
        }

        if (currentPhase.isAllowJoin()) {
            joinPhase(user, currentPhase);
        } else {
            plugin.getLogger()
                    .warning(getName() + " | It's not allowed to join in phase " + currentPhase.getName());
        }
    }

    public void leave(User user) {
        if (currentPhase == null) {
            return;
        }

        leavePhase(user, currentPhase);
    }
}
