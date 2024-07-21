package com.luisfuturist.core.features;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import com.luisfuturist.core.models.Feature;

public class NoBlockBreakFeature extends Feature {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (isPlaying(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
