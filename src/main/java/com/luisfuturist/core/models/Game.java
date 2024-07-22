package com.luisfuturist.core.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.luisfuturist.core.Bed;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Game implements Listener, Handler {

    private @Getter @Setter(value = AccessLevel.PROTECTED) Orchestrator orchestrator;

    private final Set<User> playerSet = new HashSet<>();

    @Getter
    private Phase currentPhase;
    @Getter
    @Setter
    private Phase globalPhase;
    @Getter
    @Setter
    private Phase firstPhase;

    @Getter
    private boolean isRunning;
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int minPlayers = 1, maxPlayers = 8;

    @Setter
    private ItemStack icon;

    public ItemStack getIcon() {
        if (icon == null) {
            icon = new ItemStack(Material.PAPER);

            var meta = icon.getItemMeta();
            meta.displayName(Component.text(getName()).color(NamedTextColor.YELLOW));

            icon.setItemMeta(meta);
        }

        return icon;
    }

    public void onCreate() {

    }

    public Set<User> getPlayers() {
        return new HashSet<>(playerSet); // Return a copy to prevent modification outside
    }

    private void enableFeatures(List<Feature> features) {
        for (var feature : features) {
            Bukkit.getPluginManager().registerEvents(feature, Bed.plugin);
            feature.onEnable();
        }
    }

    private void disableFeatures(List<Feature> features) {
        for (var feature : features) {
            feature.onDisable();
            HandlerList.unregisterAll(feature);
        }
    }

    @Override
    public void onEnable() {
        currentPhase = firstPhase;

        Bukkit.getPluginManager().registerEvents(this, Bed.plugin);

        if (globalPhase != null) {
            enablePhase(globalPhase);
        }
    }

    @Override
    public void onDisable() {
        currentPhase = null;

        if (globalPhase != null) {
            disablePhase(globalPhase);
        }

        HandlerList.unregisterAll(this);
    }

    public void onStart() {
        isRunning = true;

        Bed.plugin.getLogger().info("Core | " + getName() + " | Game started.");
    }

    public void onFinish() {
        isRunning = false;

        disableCurrentPhase();

        Bed.plugin.getLogger().info("Core | " + getName() + " | Game has ended.");
    }

    private void enablePhase(Phase phase) {
        Bukkit.getPluginManager().registerEvents(phase, Bed.plugin);
        enableFeatures(phase.getFeatures());
        phase.onStart();
    }

    private void disablePhase(Phase phase) {
        phase.onFinish();
        disableFeatures(phase.getFeatures());
        HandlerList.unregisterAll(phase);
    }

    private void disableCurrentPhase() {
        if (currentPhase != null) {
            disablePhase(currentPhase);
        }
    }

    private void enableNextPhase(@NonNull Phase phase) {
        this.currentPhase = phase;
        enablePhase(phase);
    }

    private void nextPhase(Phase phase) {
        disableCurrentPhase();
        enableNextPhase(phase);
    }

    public void finishCurrentPhase() {
        finishPhase(currentPhase);
    }

    private void finishPhase(Phase phase) {
        phase.setRunning(false);
        Bed.plugin.getLogger()
                .info("Core | " + getName() + " | Phase " + phase.getName() + " has ended.");

        if (phase.getNextPhase() != null) {
            runPhase(phase.getNextPhase());
        } else {
            onFinish();
        }
    }

    private void runPhase(Phase phase) {
        nextPhase(phase);
        phase.setRunning(true);

        Bed.plugin.getLogger().info("Core | " + getName() + " | Phase " + phase.getName() + " has begun.");

        if (!phase.isTimed()) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                finishPhase(phase);
            }
        }.runTaskLater(Bed.plugin, phase.getDuration());
    }

    public void start(Phase firstPhase) {
        onStart();

        if (firstPhase != null) {
            runPhase(firstPhase);
        }
    }

    public void finish() {
        onFinish();
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
            Bed.plugin.getLogger()
                    .warning(getName() + " | It's not allowed to join in phase " + currentPhase.getName());
        }
    }

    public void leave(User user) {
        if (currentPhase == null) {
            return;
        }

        leavePhase(user, currentPhase);
    }

    public boolean isPlaying(User user) {
        return playerSet.contains(user);
    }

    public <T extends Phase> T createPhase(T phase) {
        phase.setGame(this);
        phase.onCreate();
        return phase;
    }
}
