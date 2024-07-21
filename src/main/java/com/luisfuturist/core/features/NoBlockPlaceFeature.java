package com.luisfuturist.core.features;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import com.luisfuturist.core.models.Feature;

public class NoBlockPlaceFeature extends Feature {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (isPlaying(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
