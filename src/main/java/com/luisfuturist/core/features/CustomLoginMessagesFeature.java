package com.luisfuturist.core.features;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.luisfuturist.core.models.Feature;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class CustomLoginMessagesFeature extends Feature {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();
        
        if(!isPlaying(player)) {
            return;
        }

        Component joinMessage = Component.text("Hooray! ")
                .color(NamedTextColor.GOLD)
                .append(Component.text(player.getName())
                        .color(NamedTextColor.GREEN)
                        .decorate(TextDecoration.BOLD))
                .append(Component.text(" has joined us!")
                        .color(NamedTextColor.GOLD));

        event.joinMessage(joinMessage);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        var player = event.getPlayer();

        if(!isPlaying(player)) {
            return;
        }

        Component quitMessage = Component.text(player.getName())
                .color(NamedTextColor.RED)
                .decorate(TextDecoration.BOLD)
                .append(Component.text(" has logged off. We hope to see you soon!")
                        .color(NamedTextColor.YELLOW));

        event.quitMessage(quitMessage);
    }
}
