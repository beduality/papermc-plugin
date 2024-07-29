package com.luisfuturist.hub.features;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.luisfuturist.core.features.InventoryItemFeature;
import com.luisfuturist.core.models.Game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class GameMenuFeature extends InventoryItemFeature {

    private ItemStack generateMenuItem() {
        var item = new ItemStack(Material.CHEST, 1);

        var meta = item.getItemMeta();
        var displayName = Component.text("[A] Game Menu").color(NamedTextColor.YELLOW);
        meta.displayName(displayName);

        item.setItemMeta(meta);

        return item;
    }

    private void openGameMenu(Player player) {
        var orchestrator = getPhase().getGame().getOrchestrator();
        var games = orchestrator.getGames();

        var title = Component.text("Select a Minigame");
        var size = 9 * ((games.size() / 9) + 1);
        var gameMenu = Bukkit.createInventory(null, size, title);

        games.forEach((name, game) -> {
            var icon = game.getIcon();

            if(icon == null) {
                icon = new ItemStack(Material.PAPER);
                ItemMeta meta = icon.getItemMeta();
                meta.displayName(Component.text(name).color(NamedTextColor.YELLOW));
                icon.setItemMeta(meta);
            }

            gameMenu.addItem(icon);
        });

        player.openInventory(gameMenu);
    }

    private Game getGameByIcon(ItemStack icon) {
        var orchestrator = getPhase().getGame().getOrchestrator();
        var games = orchestrator.getGames();

        for (var game : games.values()) {
            if (icon.isSimilar(game.getIcon())) {
                return game;
            }
        }

        return null;
    }

    @Override
    public void onCreate() {
        setItemStack(generateMenuItem());
        setSlotIndex(0);
        super.onCreate();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(!event.getAction().isRightClick() || !hasClicked(event)) {
            return;
        }

        event.setCancelled(true);
        openGameMenu(event.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        var player = (Player) event.getWhoClicked();

        if(!isPlaying(player)) {
            return;
        }

        var clickedItem = event.getCurrentItem();

        if (clickedItem == null || !clickedItem.hasItemMeta()) {
            return;
        }

        var user = getUser(player);
        var game = getGameByIcon(clickedItem);

        if(game == null) {
            var errorMessage = Component.text("Game not found for the icon: " + clickedItem.displayName())
            .color(NamedTextColor.RED);
            player.sendMessage(errorMessage);
            return;
        }
        
        var orchestrator = getPhase().getGame().getOrchestrator();
        var gameName = game.getName();

        if (orchestrator.isPlaying(user, gameName)) {
            var joinMessage = Component.text("You have already joined the " + gameName + " minigame.")
                    .color(NamedTextColor.YELLOW);
            player.sendMessage(joinMessage);
        } else {
            orchestrator.join(user, gameName);

            var joinMessage = Component.text("You have joined the " + gameName + " minigame.")
                    .color(NamedTextColor.YELLOW);
            player.sendMessage(joinMessage);
        }

        event.setCancelled(true);
        player.closeInventory();
    }
}
