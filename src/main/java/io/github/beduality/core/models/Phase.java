package io.github.beduality.core.models;

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

    public void onCreate() {
        
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

    public final List<Feature> getFeatures() {
        return new ArrayList<>(features); // Return a copy to prevent modification outside of this class
    }

    public <T extends Feature> T createFeature(T feature) {
        feature.setPhase(this);
        feature.onCreate();
        return feature;
    }

    public final void createAndAddFeature(Feature feature) {
        features.add(createFeature(feature));
    }

    public final void createAndAddFeatures(Feature... features) {
        for (var feature : features) {
            createAndAddFeature(feature);
        }
    }

    public final void addFeature(Feature feature) {
        feature.setPhase(this);
        features.add(feature);
    }

    public final void addFeatures(Feature... features) {
        for (var feature : features) {
            addFeature(feature);
        }
    }
}
