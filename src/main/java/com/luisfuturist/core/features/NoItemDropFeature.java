package com.luisfuturist.core.features;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.luisfuturist.core.models.Feature;

import lombok.Getter;
import lombok.Setter;

public class NoItemDropFeature extends Feature {

    @Getter
    @Setter
    private Set<Material> whitelist = new HashSet<Material>();

    public void whitelistMaterial(@Nonnull Material material) {
        whitelist.add(material);
    }

    public void whitelistMaterials(@Nonnull Material... materials) {
        for (var material : materials) {
            whitelist.add(material);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (!isPlaying(event.getPlayer())) {
            return;
        }

        var itemStack = event.getItemDrop().getItemStack();

        if(!whitelist.contains(itemStack.getType())) {
            event.setCancelled(true);
        }
    }
}
