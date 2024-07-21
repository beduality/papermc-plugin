package com.luisfuturist.core.features;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.luisfuturist.core.models.Feature;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class DeathMessagesFeature extends Feature {
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        var player = (Player) event.getEntity();

        if (!isPlaying(player)) {
            return;
        }

        var playerName = player.getName();

        var deathMessage = Component.text(playerName + " died.")
                .color(NamedTextColor.RED);

        switch (player.getLastDamageCause().getCause()) {
            case FALL:
                deathMessage = Component.text(playerName + " fell from a high place.")
                        .color(NamedTextColor.RED);
                break;
            case DROWNING:
                deathMessage = Component.text(playerName + " drowned.")
                        .color(NamedTextColor.RED);
                break;
            case FIRE:
            case FIRE_TICK:
                deathMessage = Component.text(playerName + " burned to death.")
                        .color(NamedTextColor.RED);
                break;
            case LAVA:
                deathMessage = Component.text(playerName + " tried to swim in lava.")
                        .color(NamedTextColor.RED);
                break;
            case VOID:
                deathMessage = Component.text(playerName + " fell into the void.")
                        .color(NamedTextColor.RED);
                break;
            case CONTACT:
                deathMessage = Component.text(playerName + " was pricked to death.")
                        .color(NamedTextColor.RED);
                break;
            case MAGIC:
                deathMessage = Component.text(playerName + " was killed by magic.")
                        .color(NamedTextColor.RED);
                break;
            default:
                break;
        }

        if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            var nEvent = (EntityDamageByEntityEvent) event
                    .getEntity().getLastDamageCause();

            String killerName = nEvent.getDamager().getName();

            if ((nEvent.getDamager() instanceof Projectile)) {
                var projectile = (Projectile) nEvent.getDamager();
                var shooter = (Entity) projectile.getShooter();
                killerName = shooter.getName();
            }

            deathMessage = Component.text(playerName + " was killed by " + killerName + ".")
                    .color(NamedTextColor.RED);
        }

        event.deathMessage(deathMessage);
    }
}
