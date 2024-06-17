package com.luisfuturist.hub.features;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.luisfuturist.core.models.Feature;
import com.luisfuturist.core.models.User;
import com.luisfuturist.randomizer.features.UhcWorldFeature;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

@AllArgsConstructor
public class PlayItemFeature extends Feature {

    private final Component ITEM_NAME = Component.text("Play").color(NamedTextColor.YELLOW);

    private UhcWorldFeature uhcWorldFeature;

    @Override
    public void onEnable() {
        super.onEnable();

        getPhase().getGame().getPlayers().forEach(user -> {
            giveMenuItem(user.getPlayer());
        });
    }

    @Override
    public void onJoin(User user) {
        super.onJoin(user);
        
        giveMenuItem(user.getPlayer());
    }
    
    private void giveMenuItem(Player player) {
        var menuIs = new ItemStack(Material.GOLDEN_APPLE, 1);
        var meta = menuIs.getItemMeta();

        meta.displayName(ITEM_NAME);

        menuIs.setItemMeta(meta);

        player.getInventory().addItem(menuIs);
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        var player = event.getPlayer();

        if (!isPlaying(player)) {
            return;
        }

        ItemStack item = event.getItem();

        if (item == null || !item.hasItemMeta()) {
            return;
        }
        
        if(item.getItemMeta().displayName().equals(ITEM_NAME)) {
            event.setCancelled(true);
            player.sendMessage("Clicked");
            var world = uhcWorldFeature.getWorld();

            if(world == null) {
                player.sendMessage("World is not available yet.");
                return;
            }

            teleport(player, world);
        }
    }

    private void teleport(Player player, World world) {
        var highestY = world.getHighestBlockYAt(0, 0);
        player.teleport(new Location(world, 0, highestY, 0));
    }
}
