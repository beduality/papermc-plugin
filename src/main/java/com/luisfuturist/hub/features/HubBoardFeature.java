package com.luisfuturist.hub.features;

import org.bukkit.Bukkit;

import com.luisfuturist.core.features.BoardFeature;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;

public class HubBoardFeature extends BoardFeature {
    
    private void setupBoard() {
        var orchestrator = getPhase().getGame().getOrchestrator();
        
        var displayName = Component.text().color(NamedTextColor.BLUE)
                .append(Component.text("BED Hub")).asComponent();

        getObjective().displayName(displayName);

        var playersNum = orchestrator.getGlobal().getPlayers().size();
        var maxPlayers = orchestrator.getGlobal().getMaxPlayers();

        setLines(new String[] {
                ChatColor.GRAY + "Online",
                playersNum + "/" + maxPlayers
        });
    }

    @Override
    public void onEnable() {
        super.onEnable();
        setupBoard();
    }
}
