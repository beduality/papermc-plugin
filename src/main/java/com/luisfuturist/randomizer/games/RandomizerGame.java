package com.luisfuturist.randomizer.games;

import org.bukkit.plugin.java.JavaPlugin;

import com.luisfuturist.core.managers.LocationManager;
import com.luisfuturist.core.models.Game;
import com.luisfuturist.core.models.Phase;
import com.luisfuturist.core.phases.GracePhase;
import com.luisfuturist.hub.games.HubGame;
import com.luisfuturist.randomizer.features.UhcWorldFeature;
import com.luisfuturist.randomizer.phases.RandomizerLobbyPhase;

import lombok.Getter;

@Getter
public class RandomizerGame extends Game {

    private HubGame hub;

    public RandomizerGame(
            JavaPlugin plugin,
            LocationManager locationManager,
            HubGame hub,
            UhcWorldFeature uhcWorldFeature) {
        super("Randomizer", plugin);
        this.hub = hub;

        var globalPhase = new Phase("RandomizerGlobal") {
        };
        globalPhase.addFeature(uhcWorldFeature);
        setGlobalPhase(createPhase(globalPhase));

        var gracePhase = createPhase(new GracePhase());
        gracePhase.setDuration(20 * 5);

        var lobbyPhase = createPhase(new RandomizerLobbyPhase(plugin, locationManager));
        lobbyPhase.setNextPhase(gracePhase);

        setFirstPhase(lobbyPhase);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
