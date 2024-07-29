package io.github.beduality.core.phases;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.WeatherType;

import io.github.beduality.core.Bed;
import io.github.beduality.core.features.ClearInventoryFeature;
import io.github.beduality.core.features.GameModeFeature;
import io.github.beduality.core.features.HealthFeature;
import io.github.beduality.core.features.NoBlockBreakFeature;
import io.github.beduality.core.features.NoBlockPlaceFeature;
import io.github.beduality.core.features.NoDamageFeature;
import io.github.beduality.core.features.NoHungerLossFeature;
import io.github.beduality.core.features.NoInteractFeature;
import io.github.beduality.core.features.NoItemDropFeature;
import io.github.beduality.core.features.NoTimeChangeFeature;
import io.github.beduality.core.features.NoWeatherChangeFeature;
import io.github.beduality.core.features.SpawnFeature;
import io.github.beduality.core.features.VoidSpawnTeleportFeature;
import io.github.beduality.core.models.Phase;
import io.github.beduality.core.models.User;

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
                new GameModeFeature(GameMode.ADVENTURE),
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
