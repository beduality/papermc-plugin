package com.luisfuturist.core.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class Phase implements Listener {

    @Getter private String name;
    @Getter @Setter(value = AccessLevel.PROTECTED) private List<Feature> features = new ArrayList<>();
    @Getter @Setter private long duration;
    @Getter @Setter(value = AccessLevel.PROTECTED) private boolean isRunning;
    @Getter @Setter private Phase nextPhase;
    @Getter @Setter(value = AccessLevel.PROTECTED) private Game game;
    
    @Getter @Setter(value = AccessLevel.PROTECTED) private List<Player> players = new ArrayList<>();
    @Getter @Setter private boolean allowJoin = false;
    @Getter @Setter private boolean allowSpectate = true;
    
    public Phase(String label) {
        this.name = label;
    }

    public void onStart() {}
    public void onFinish() {}
    
    public void onJoin(Player player) {}
    public void onLeave(Player player) {}

    public void addFeatures(Feature... features) {
        this.features.addAll(Arrays.asList(features));
    }
}
