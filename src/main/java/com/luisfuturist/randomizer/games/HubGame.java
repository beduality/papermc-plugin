package com.luisfuturist.randomizer.games;

import org.bukkit.plugin.java.JavaPlugin;

import com.luisfuturist.core.features.SpawnFeature;
import com.luisfuturist.core.models.Game;
import com.luisfuturist.randomizer.features.PlayItemFeature;
import com.luisfuturist.randomizer.features.UhcWorldFeature;
import com.luisfuturist.randomizer.managers.LocationManager;
import com.luisfuturist.randomizer.phases.LobbyPhase;

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
