package com.luisfuturist.core.features;

import org.bukkit.Location;

import com.luisfuturist.core.models.Feature;
import com.luisfuturist.core.models.User;

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
    public void onJoin(User user) {
        user.getPlayer().teleport(location);
    }
}
