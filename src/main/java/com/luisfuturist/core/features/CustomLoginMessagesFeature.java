package com.luisfuturist.core.features;

import org.bukkit.Bukkit;

import com.luisfuturist.core.models.Feature;
import com.luisfuturist.core.models.User;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class CustomLoginMessagesFeature extends Feature {

    @Override
    public void onJoin(User user) {
        var player = user.getPlayer();

        Component joinMessage = Component.text("Hooray! ")
                .color(NamedTextColor.GOLD)
                .append(Component.text(player.getName())
                        .color(NamedTextColor.GREEN)
                        .decorate(TextDecoration.BOLD))
                .append(Component.text(" has joined us!")
                        .color(NamedTextColor.GOLD));

        Bukkit.broadcast(joinMessage);
    }

    @Override
    public void onLeave(User user) {
        var player = user.getPlayer();

        Component quitMessage = Component.text(player.getName())
                .color(NamedTextColor.RED)
                .decorate(TextDecoration.BOLD)
                .append(Component.text(" has logged off. We hope to see you soon!")
                        .color(NamedTextColor.YELLOW));

        Bukkit.broadcast(quitMessage);
    }
}
