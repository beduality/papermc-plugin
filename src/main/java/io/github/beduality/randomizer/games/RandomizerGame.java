package io.github.beduality.randomizer.games;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.beduality.core.models.Game;
import io.github.beduality.core.models.Phase;
import io.github.beduality.core.phases.GracePhase;
import io.github.beduality.randomizer.phases.RandomizerLobbyPhase;
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

        var globalPhase = createPhase(new Phase() {

        });
        globalPhase.setName("RandomizerGlobal");
        //globalPhase.createAndAddFeature(new UhcWorldFeature());
        setGlobalPhase(globalPhase);

        var gracePhase = createPhase(new GracePhase());
        gracePhase.setDuration(20 * 5);

        var lobbyPhase = createPhase(new RandomizerLobbyPhase());
        lobbyPhase.setNextPhase(gracePhase);

        setFirstPhase(lobbyPhase);
    }
}
