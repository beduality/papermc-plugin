package com.luisfuturist.core.phases;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.WeatherType;

import com.luisfuturist.core.CorePlugin;
import com.luisfuturist.core.features.ClearInventoryFeature;
import com.luisfuturist.core.features.HealthFeature;
import com.luisfuturist.core.features.NoDamageFeature;
import com.luisfuturist.core.features.NoGriefingFeature;
import com.luisfuturist.core.features.NoHungerLossFeature;
import com.luisfuturist.core.features.NoTimeChangeFeature;
import com.luisfuturist.core.features.NoWeatherChangeFeature;
import com.luisfuturist.core.models.Phase;
import com.luisfuturist.core.models.User;

public class LobbyPhase extends Phase {

    private Location location;

    public LobbyPhase() {
        super("Lobby");

        var locationManager = CorePlugin.locationManager;
        location = locationManager.getLocation("lobby");
        var world = location.getWorld();

        addFeatures(
                new NoDamageFeature(),
                new NoGriefingFeature(),
                new HealthFeature(),
                new NoHungerLossFeature(),
                new NoTimeChangeFeature(world, 6000L),
                new NoWeatherChangeFeature(world, WeatherType.CLEAR),
                new ClearInventoryFeature());
        setAllowJoin(true);
        setAllowSpectate(false);
    }

    public void resetPlayer(User user) {
        var player = user.getPlayer();

        var locationManager = CorePlugin.locationManager;
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(locationManager.getLocation("lobby"));
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
