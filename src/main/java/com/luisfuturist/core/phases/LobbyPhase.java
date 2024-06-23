package com.luisfuturist.core.phases;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import com.luisfuturist.core.CorePlugin;
import com.luisfuturist.core.features.ClearInventoryFeature;
import com.luisfuturist.core.features.HealthFeature;
import com.luisfuturist.core.features.NoDamageFeature;
import com.luisfuturist.core.features.NoGriefingFeature;
import com.luisfuturist.core.features.NoHungerLossFeature;
import com.luisfuturist.core.features.NoTimeChangeFeature;
import com.luisfuturist.core.features.NoWeatherChangeFeature;
import com.luisfuturist.core.models.Phase;

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
    }

    public void resetPlayer(Player player) {
        var locationManager = CorePlugin.locationManager;
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(locationManager.getLocation("lobby"));
    }

    @Override
    public void onStart() {
        super.onStart();

        getGame().getPlayers().forEach(user -> {
            resetPlayer(user.getPlayer());
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        resetPlayer(event.getPlayer());
    }
}
