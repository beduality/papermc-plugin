package com.luisfuturist.core.models;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Listener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public abstract class Phase implements Listener, Joinable, Timed {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter(value = AccessLevel.PROTECTED)
    private List<Feature> features = new ArrayList<>();
    @Getter
    @Setter
    private long duration;
    @Getter
    @Setter
    private boolean isTimed = true;
    @Getter
    @Setter(value = AccessLevel.PROTECTED)
    private boolean isRunning;
    @Getter
    @Setter
    private Phase nextPhase;
    @Getter
    @Setter(value = AccessLevel.PROTECTED)
    private Game game;

    @Getter
    @Setter
    private boolean allowJoin = false;
    @Getter
    @Setter
    private boolean allowSpectate = true;

    public Phase(String label) {
        this.name = label;
    }

    public void onStart() {
    }

    public void onFinish() {
    }

    @Override
    public void onJoin(User user) {
    }

    @Override
    public void onLeave(User user) {
    }

    public void addFeature(Feature feature) {
        feature.setPhase(this);
        this.features.add(feature);
    }

    public void addFeatures(Feature... features) {
        for (Feature feature : features) {
            addFeature(feature);
        }
    }
}
