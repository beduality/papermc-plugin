package com.luisfuturist.randomizer.games;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.luisfuturist.core.models.Game;
import com.luisfuturist.core.models.Phase;
import com.luisfuturist.core.phases.GracePhase;
import com.luisfuturist.randomizer.features.UhcWorldFeature;
import com.luisfuturist.randomizer.phases.RandomizerLobbyPhase;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

@Getter
public class RandomizerGame extends Game {

    private ItemStack generateIcon() {
        var icon = new ItemStack(Material.GOLDEN_APPLE);
        var displayName = Component.text(getName()).color(NamedTextColor.GOLD);
        var meta = icon.getItemMeta();
        meta.displayName(displayName);
        icon.setItemMeta(meta);
        
        return icon;
    }

    @Override
    public void onCreate() {
        setName("Randomizer");
        setIcon(generateIcon());

        var globalPhase = new Phase() {

        };
        globalPhase.setName("RandomizerGlobal");
        globalPhase.addFeature(new UhcWorldFeature());
        setGlobalPhase(globalPhase);

        var gracePhase = new GracePhase();
        gracePhase.setDuration(20 * 5);

        var lobbyPhase = new RandomizerLobbyPhase();
        lobbyPhase.setNextPhase(gracePhase);

        setFirstPhase(lobbyPhase);

        super.onCreate();
    }
}
