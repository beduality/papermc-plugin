package com.luisfuturist.core.phases;

import org.bukkit.Location;
import org.bukkit.WeatherType;

import com.luisfuturist.core.Bed;
import com.luisfuturist.core.features.ClearInventoryFeature;
import com.luisfuturist.core.features.HealthFeature;
import com.luisfuturist.core.features.NoBlockBreakFeature;
import com.luisfuturist.core.features.NoBlockPlaceFeature;
import com.luisfuturist.core.features.NoDamageFeature;
import com.luisfuturist.core.features.NoHungerLossFeature;
import com.luisfuturist.core.features.NoInteractFeature;
import com.luisfuturist.core.features.NoItemDropFeature;
import com.luisfuturist.core.features.NoTimeChangeFeature;
import com.luisfuturist.core.features.NoWeatherChangeFeature;
import com.luisfuturist.core.features.SpawnFeature;
import com.luisfuturist.core.features.VoidSpawnTeleportFeature;
import com.luisfuturist.core.models.Phase;
import com.luisfuturist.core.models.User;

public class LobbyPhase extends Phase {

    private Location location;

    private void resetPlayer(User user) {
        var player = user.getPlayer();

        var locationManager = Bed.locationManager;
        player.teleport(locationManager.getLocation("lobby"));
    }

    @Override
    public void onCreate() {
        setName("Lobby");

        var locationManager = Bed.locationManager;
        location = locationManager.getLocation("lobby");
        var world = location.getWorld();

        createAndAddFeatures(
                new NoDamageFeature(),
                new NoBlockBreakFeature(),
                new NoBlockPlaceFeature(),
                new NoInteractFeature(),
                new NoItemDropFeature(),
                new HealthFeature(),
                new NoHungerLossFeature(),
                new NoTimeChangeFeature(world, 6000L),
                new NoWeatherChangeFeature(world, WeatherType.CLEAR),
                new ClearInventoryFeature());
        setAllowJoin(true);
        setAllowSpectate(false);

        var lobbyLocation = locationManager.getLocation("lobby");

        if (lobbyLocation != null) {
            var spawnFeature = new SpawnFeature();
            spawnFeature.setLocation(lobbyLocation);
            createAndAddFeature(spawnFeature);

            createAndAddFeature(new VoidSpawnTeleportFeature(spawnFeature));
        } else {
            Bed.plugin.getLogger().warning("Hub | Spawn feature in " + getName()
                    + " phase is not available due to the lack of location config.");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        getGame().getPlayers().forEach(user -> {
            resetPlayer(user);
        });
    }

    @Override
    public void onJoin(User user) {
        resetPlayer(user);
    }
}
