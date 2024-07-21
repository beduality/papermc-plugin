package com.luisfuturist.core.features;

import org.bukkit.GameMode;

import com.luisfuturist.core.models.Feature;
import com.luisfuturist.core.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class GameModeFeature extends Feature {

    private GameMode gameMode = GameMode.SURVIVAL;

    @Override
    public void onEnable() {
        getPhase().getGame().getPlayers().forEach(user -> {
            user.getPlayer().setGameMode(gameMode);
        });
    }

    @Override
    public void onJoin(User user) {
        user.getPlayer().setGameMode(gameMode);
    }
}
