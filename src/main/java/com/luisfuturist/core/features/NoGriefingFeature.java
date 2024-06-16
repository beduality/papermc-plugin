package com.luisfuturist.core.features;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.luisfuturist.core.models.Feature;
import com.luisfuturist.core.models.Phase;

public class NoGriefingFeature extends Feature {
    
    public NoGriefingFeature(Phase phase) {
        super(phase);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(hasPlayer(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(hasPlayer(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
