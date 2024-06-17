package com.luisfuturist.core;

import org.bukkit.plugin.java.JavaPlugin;

import com.luisfuturist.core.games.GlobalGame;
import com.luisfuturist.core.managers.LocationManager;
import com.luisfuturist.core.models.Orchestrator;
import com.luisfuturist.hub.games.HubGame;
import com.luisfuturist.randomizer.features.UhcWorldFeature;
import com.luisfuturist.randomizer.games.RandomizerGame;

public class MainOrchestrator extends Orchestrator {
    
    public MainOrchestrator(JavaPlugin plugin, LocationManager locationManager) {
        var uhcWorldFeature = new UhcWorldFeature();

        var hubGame = new HubGame(plugin, locationManager);

        var hub = createGame(hubGame);
        var uhc = new RandomizerGame(plugin, locationManager, hubGame, uhcWorldFeature);
        var global = createGame(new GlobalGame(plugin, hub));

        setGlobal(global);
        setHub(hub);

        addGame(uhc);
    }
}
