package com.luisfuturist.core.features;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.luisfuturist.core.models.Feature;

public class NoGriefingFeature extends Feature {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (isPlaying(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (isPlaying(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        event.getPlayer().sendMessage(getPhase().getGame().getName() + " | players size: " + getPhase().getGame().getPlayers().size());
        
        if (isPlaying(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
