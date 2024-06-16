package com.luisfuturist.core.features;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import com.luisfuturist.core.models.Feature;
import com.luisfuturist.core.models.Phase;

public class NoHungerLossFeature extends Feature {
    
    public NoHungerLossFeature(Phase phase) {
        super(phase);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if(!hasPlayer((Player) event.getEntity())) return;

        event.setCancelled(true);
    }
}
