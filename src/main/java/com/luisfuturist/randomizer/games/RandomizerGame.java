package com.luisfuturist.randomizer.games;

import org.bukkit.plugin.java.JavaPlugin;

import com.luisfuturist.core.features.SpawnFeature;
import com.luisfuturist.core.models.Game;
import com.luisfuturist.randomizer.managers.LocationManager;
import com.luisfuturist.randomizer.phases.GracePhase;
import com.luisfuturist.randomizer.phases.LobbyPhase;

import lombok.Getter;

@Getter
public class RandomizerGame extends Game {

    private HubGame hub;

    public RandomizerGame(JavaPlugin plugin, LocationManager locationManager, HubGame hub) {
        super("Randomizer", plugin);
        this.hub = hub;

        var gracePhase = createPhase(new GracePhase());
        gracePhase.setDuration(20 * 5);

        var lobbyPhase = createPhase(new LobbyPhase());
        lobbyPhase.setNextPhase(gracePhase);
        lobbyPhase.setDuration(20 * 10);

        var lobbyLocation = locationManager.getLocation("lobby");

        if (lobbyLocation != null) {
            var spawnFeature = new SpawnFeature();
            spawnFeature.setLocation(lobbyLocation);
            lobbyPhase.addFeature(spawnFeature);
        } else {
            plugin.getLogger().warning("Randomizer | Spawn feature in " + lobbyPhase.getName()
                    + " phase is not available due to the lack of location config.");
        }

        setFirstPhase(lobbyPhase);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        getPlayers().forEach(player -> {

        });
    }
}
