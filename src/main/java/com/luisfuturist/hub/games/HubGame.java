package com.luisfuturist.hub.games;

import org.bukkit.plugin.java.JavaPlugin;

import com.luisfuturist.core.features.SpawnFeature;
import com.luisfuturist.core.managers.LocationManager;
import com.luisfuturist.core.models.Game;
import com.luisfuturist.core.phases.LobbyPhase;
import com.luisfuturist.hub.features.PlayItemFeature;
import com.luisfuturist.randomizer.features.UhcWorldFeature;

public class HubGame extends Game {

    public HubGame(JavaPlugin plugin, LocationManager locationManager, UhcWorldFeature uhcWorldFeature) {
        super("Hub", plugin);

        var lobbyPhase = createPhase(new LobbyPhase());
        lobbyPhase.setTimed(false);
        lobbyPhase.addFeature(new PlayItemFeature(uhcWorldFeature));

        var lobbyLocation = locationManager.getLocation("lobby");
        
        if(lobbyLocation != null) {
            var spawnFeature = new SpawnFeature();
            spawnFeature.setLocation(lobbyLocation);
            lobbyPhase.addFeature(spawnFeature);
        } else {
            plugin.getLogger().warning("Hub | Spawn feature in " + lobbyPhase.getName() + " phase is not available due to the lack of location config.");
        }

        setFirstPhase(lobbyPhase);
    }
}
