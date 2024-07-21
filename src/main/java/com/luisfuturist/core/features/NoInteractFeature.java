package com.luisfuturist.core.features;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import com.luisfuturist.core.models.Feature;

public class NoInteractFeature extends Feature {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (isPlaying(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
