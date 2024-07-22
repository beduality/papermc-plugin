package com.luisfuturist.hub.games;

import org.bukkit.Bukkit;

import com.luisfuturist.core.features.PlayerListFeature;
import com.luisfuturist.core.models.Game;
import com.luisfuturist.core.phases.LobbyPhase;
import com.luisfuturist.hub.features.HubBoardFeature;
import com.luisfuturist.hub.features.GameMenuFeature;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class HubGame extends Game {

    @Override
    public void onCreate() {
        setName("Hub");
        setMinPlayers(0);
        setMaxPlayers(Bukkit.getMaxPlayers());

        var lobbyPhase = createPhase(new LobbyPhase());
        lobbyPhase.setTimed(false);
        lobbyPhase.createAndAddFeature(new GameMenuFeature());
        lobbyPhase.createAndAddFeature(new HubBoardFeature());

        var header = Component.text("Block-Entity Duality\n").color(NamedTextColor.AQUA);
        var footer = Component.text("\nProbabilistically fun.").color(NamedTextColor.WHITE);

        lobbyPhase.createAndAddFeature(new PlayerListFeature(header, footer));

        setFirstPhase(lobbyPhase);
    }
}
