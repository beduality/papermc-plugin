package com.luisfuturist.core.features;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.luisfuturist.core.models.Feature;

public class NoPveFeature extends Feature {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (!isPlaying(player))
                return;

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player && isDamageSourcePlayerOrMob(event.getCause())) {
            if (!isPlaying(player))
                return;

            event.setCancelled(true);
        }
    }

    private boolean isDamageSourcePlayerOrMob(EntityDamageEvent.DamageCause cause) {
        switch (cause) {
            case ENTITY_ATTACK:
            case PROJECTILE:
                return true;
            default:
                return false;
        }
    }
}