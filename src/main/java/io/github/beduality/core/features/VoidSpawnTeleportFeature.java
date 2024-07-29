package io.github.beduality.core.features;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.beduality.core.models.Feature;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VoidSpawnTeleportFeature extends Feature {

    private SpawnFeature spawnFeature;

    private void teleport(Player player) {
        var loc = spawnFeature.getLocation();
        player.teleportAsync(loc);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if(e.getEntityType() != EntityType.PLAYER) {
            return;
        }

        var player = (Player) e.getEntity();

        if(!isPlaying(player)) {
            return;
        }
        
        if(e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            teleport(player);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        var player = e.getPlayer();

        if(!isPlaying(player)) {
            return;
        }
        
        if(e.getTo().getY() < 0) {
            teleport(player);
        }
    }
}
