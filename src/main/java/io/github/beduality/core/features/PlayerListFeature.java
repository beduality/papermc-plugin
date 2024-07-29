package io.github.beduality.core.features;

import org.bukkit.entity.Player;

import io.github.beduality.core.models.Feature;
import io.github.beduality.core.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.Component;

@NoArgsConstructor @AllArgsConstructor
public class PlayerListFeature extends Feature {

    @Getter @Setter
    private Component header, footer;

    private void updatePlayerList(Player player) {
        player.sendPlayerListHeaderAndFooter(header, footer);
    }

    @Override
    public void onEnable() {
        getPhase().getGame().getPlayers().forEach(user -> {
            updatePlayerList(user.getPlayer());
        });
    }

    @Override
    public void onJoin(User user) {
        updatePlayerList(user.getPlayer());
    }
}
