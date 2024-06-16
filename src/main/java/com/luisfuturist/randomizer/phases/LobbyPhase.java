package com.luisfuturist.randomizer.phases;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import com.luisfuturist.core.features.ClearInventoryFeature;
import com.luisfuturist.core.features.HealthFeature;
import com.luisfuturist.core.features.NoDamageFeature;
import com.luisfuturist.core.features.NoGriefingFeature;
import com.luisfuturist.core.features.NoHungerLossFeature;
import com.luisfuturist.core.models.Phase;
import com.luisfuturist.randomizer.RandomizerPlugin;

public class LobbyPhase extends Phase {

    public LobbyPhase() {
        super("Lobby");
        addFeatures(
                new NoDamageFeature(),
                new NoGriefingFeature(),
                new HealthFeature(),
                new NoHungerLossFeature(),
                new ClearInventoryFeature());
        setAllowJoin(true);
    }

    public void resetPlayer(Player player) {
        var locationManager = RandomizerPlugin.locationManager;
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(locationManager.getLocation("lobby"));
    }

    @Override
    public void onStart() {
        super.onStart();

        for (var player : Bukkit.getOnlinePlayers()) {
            resetPlayer(player);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        resetPlayer(event.getPlayer());
    }
}
