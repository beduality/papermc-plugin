package com.luisfuturist.core.features;

import org.bukkit.entity.Player;

import com.luisfuturist.core.models.Feature;

public class RemovePotionEffectsFeature extends Feature {

    private void removePotionEffects(Player player) {
        player.getActivePotionEffects()
                .forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
    }

    @Override
    public void onEnable() {
        super.onEnable();

        getPhase().getGame().getPlayers().forEach(this::removePotionEffects);
    }

    @Override
    public void onJoin(Player event) {
        removePotionEffects(event.getPlayer());
    }
}
