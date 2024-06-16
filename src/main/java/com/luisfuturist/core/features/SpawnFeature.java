package com.luisfuturist.core.features;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.luisfuturist.core.models.Feature;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class SpawnFeature extends Feature {

    @Getter
    @Setter
    @NonNull
    private Location location;

    @Override
    public void onEnable() {
        getPhase().getGame().getPlayers().forEach(this::onJoin);
    }

    @Override
    public void onJoin(Player player) {
        player.teleport(location);
    }
}
