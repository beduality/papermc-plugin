package com.luisfuturist.core.features;

import org.bukkit.entity.Player;

import com.luisfuturist.core.models.Feature;
import com.luisfuturist.core.models.User;

public class RemovePotionEffectsFeature extends Feature {

    private void removePotionEffects(Player player) {
        player.getActivePotionEffects()
                .forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
    }

    @Override
    public void onEnable() {
        super.onEnable();

        getPhase().getGame().getPlayers().forEach(user -> {
            removePotionEffects(user.getPlayer());
        });
    }

    @Override
    public void onJoin(User user) {
        removePotionEffects(user.getPlayer());
    }
}
