package io.github.beduality.core.games;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.beduality.core.Bed;
import io.github.beduality.core.models.Game;
import io.github.beduality.core.models.User;
import io.github.beduality.core.phases.GlobalPhase;

public class GlobalGame extends Game {

    @Override
    public void onCreate() {
        setName("Global");
        setGlobalPhase(createPhase(new GlobalPhase()));
    }

    private void disableAdvancements() {
        if (Bed.plugin == null)
            return;

        Bed.plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "gamerule announceAdvancements false");
    }

    @Override
    public void onEnable() {
        super.onEnable();

        disableAdvancements();

        Bukkit.getOnlinePlayers().forEach(player -> {
            var user = Bed.userManager.login(player);
            play(user);
        });
    }

    @Override
    public void onDisable() {
        super.onDisable();

        Bukkit.getOnlinePlayers().forEach(player -> {
            var user = Bed.userManager.login(player);
            leave(user);
        });
    }

    @Override
    public void play(User user) {
        joinGlobal(user);
        super.play(user);

        var hub = getOrchestrator().getHub();

        if (hub != null) {
            hub.play(user);
        }
    }

    @Override
    public void leave(User user) {
        leaveGlobal(user);
        super.leave(user);

        var hub = getOrchestrator().getHub();

        if (hub != null) {
            hub.play(user);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();

        var user = Bed.userManager.getOrLogin(player);
        play(user);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent event) {
        var user = Bed.userManager.getUser(event.getPlayer());
        
        leave(user);
        Bed.userManager.logout(user);
    }
}
