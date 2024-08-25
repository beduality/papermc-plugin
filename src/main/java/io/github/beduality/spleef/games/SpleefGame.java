package io.github.beduality.spleef.games;

import io.github.beduality.core.models.Game;
import io.github.beduality.core.models.Phase;
import io.github.beduality.core.models.User;
import io.github.beduality.core.phases.GracePhase;
import io.github.beduality.core.phases.LobbyPhase;
import io.github.beduality.randomizer.phases.RandomizerLobbyPhase;
import io.github.beduality.spleef.models.SpleefArena;
import io.github.beduality.spleef.phases.SpleefGracePhase;
import io.github.beduality.spleef.phases.SpleefWaitPhase;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
public class SpleefGame extends Game {

    private SpleefArena arena;

    private ItemStack generateIcon() {
        var icon = new ItemStack(Material.DIAMOND_SHOVEL);
        var displayName = Component.text(getName()).color(NamedTextColor.BLUE);
        var meta = icon.getItemMeta();
        meta.displayName(displayName);
        icon.setItemMeta(meta);

        return icon;
    }

    @Override
    public void onCreate() {
        setName("Spleef");
        setIcon(generateIcon());
        setMaxPlayers(12);
        setMinPlayers(2);

        var gameAmount = getOrchestrator().getGames().values().stream()
                .filter(game -> game.getName().equals(getName())).toArray().length;
        arena = new SpleefArena(gameAmount);

        var waitPhase = createPhase(new SpleefWaitPhase());
        var gracePhase = createPhase(new SpleefGracePhase());

        waitPhase.setNextPhase(gracePhase);

        setFirstPhase(waitPhase);
    }
}
