package com.luisfuturist.hub.features;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.luisfuturist.core.MainOrchestrator;
import com.luisfuturist.core.models.Feature;
import com.luisfuturist.core.models.User;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

@AllArgsConstructor
public class PlayItemFeature extends Feature {

    private final Component ITEM_NAME = Component.text("Play").color(NamedTextColor.YELLOW);

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

        if (item == null || !item.hasItemMeta() || !event.getAction().isRightClick()) {
            return;
        }

        if (item.getItemMeta().displayName().equals(ITEM_NAME)) {
            event.setCancelled(true);

            var user = getUser(player);

            var orchestrator = getPhase().getGame().getOrchestrator();

            if (orchestrator.isPlaying(user, MainOrchestrator.RANDOMIZER)) {

                var joinMessage = Component.text("You have already joined the UHC minigame.")
                        .color(NamedTextColor.YELLOW);

                player.sendMessage(joinMessage);
                return;
            }

            orchestrator.join(user, MainOrchestrator.RANDOMIZER);

            var joinMessage = Component.text("You have joined the UHC minigame.")
                    .color(NamedTextColor.YELLOW);

            player.sendMessage(joinMessage);
        }
    }
}
