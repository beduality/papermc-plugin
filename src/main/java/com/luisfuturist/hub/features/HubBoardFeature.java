package com.luisfuturist.hub.features;

import com.luisfuturist.core.features.BoardFeature;
import com.luisfuturist.core.models.User;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;

public class HubBoardFeature extends BoardFeature {

    private void setupBoard() { 
        var displayName = Component.text().color(NamedTextColor.BLUE)
                .append(Component.text("BED Hub")).asComponent();

        getObjective().displayName(displayName);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupBoard();
    }

    @Override
    public void onJoin(User user) {
        super.onJoin(user);
        updateBoard();
    }

    public void updateBoard() {
        var orchestrator = getPhase().getGame().getOrchestrator();
        
        var playersNum = orchestrator.getGlobal().getPlayers().size();
        var maxPlayers = orchestrator.getGlobal().getMaxPlayers();

        setLines(new String[] {
                ChatColor.GRAY + "Online",
                playersNum + "/" + maxPlayers
        });
    }
}
