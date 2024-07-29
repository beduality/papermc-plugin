package io.github.beduality.core.features;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.beduality.core.models.Feature;

public class NoInteractFeature extends Feature {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        
        if (isPlaying(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
