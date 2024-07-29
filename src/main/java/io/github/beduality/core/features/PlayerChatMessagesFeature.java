package io.github.beduality.core.features;

import org.bukkit.event.EventHandler;

import io.github.beduality.core.models.Feature;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class PlayerChatMessagesFeature extends Feature {

    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {
        if (!isPlaying(event.getPlayer())) {
            return;
        }

        event.renderer((source, sourceDisplayName, message, viewer) -> {
            return Component.text()
                    .append(sourceDisplayName.color(NamedTextColor.WHITE))
                    .append(Component.text(": ").color(NamedTextColor.GRAY))
                    .append(message.color(NamedTextColor.GRAY))
                    .build();
        });
    }
}
