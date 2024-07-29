package io.github.beduality.core.features;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import io.github.beduality.core.models.Feature;

public class NoDamageFeature extends Feature {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (!isPlaying(player))
                return;

            event.setCancelled(true);
            player.setFireTicks(0);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            if (!isPlaying(player))
                return;

            event.setCancelled(true);
        }

        if (event.getEntity() instanceof Player player) {
            if (!isPlaying(player))
                return;

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (!isPlaying(player))
                return;

            event.setCancelled(true);
        }
    }
}
