package io.github.beduality.parkour.orchestrators;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import io.github.beduality.core.Bed;
import io.github.beduality.core.models.Orchestrator;
import io.github.beduality.core.models.User;
import io.github.beduality.parkour.features.ParkourFeature;
import io.github.beduality.parkour.games.ParkourGame;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class ParkourOrchestrator extends Orchestrator {

    private Set<UUID> recentlyLeft = new HashSet<>();
    private BukkitTask leaveTask;

    private void play(User user, Block plate) {
        var parkourFeature = new ParkourFeature();
        parkourFeature.setPlate(plate);

        var game = createGame(new ParkourGame(parkourFeature, user) {

            @Override
            public void onCreate() {
                super.onCreate();
                setName(getName() + "|" + user.getPlayer().getUniqueId());
            }

            @Override
            public void onGiveUp(User user) {
                var player = user.getPlayer();
                recentlyLeft.add(player.getUniqueId());

                leaveTask = new BukkitRunnable() {
                    @Override
                    public void run() {
                        recentlyLeft.remove(player.getUniqueId());
                    }
                }.runTaskLater(Bed.plugin, 20L * 3);
            }

            @Override
            public void onDisable() {
                super.onDisable();

                if (leaveTask != null) {
                    leaveTask.cancel();
                }
            }
        });

        addGame(game);
        game.onEnable();
        game.start(game.getFirstPhase());

        game.play(user);
    }

    @EventHandler
    public void onPlayerPressPlate(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.PHYSICAL)) {
            return;
        }

        var player = event.getPlayer();
        var user = getUser(player);
        var plate = event.getClickedBlock();

        var isFirstPlate = plate.getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)
                && plate.getRelative(BlockFace.DOWN).getType().equals(Material.GOLD_BLOCK);

        if (isFirstPlate && !isPlaying(user)) {
            if (recentlyLeft.contains(player.getUniqueId())) {
                return;
            }

            player.sendMessage(Component.text("You have joined the parkour.").color(NamedTextColor.YELLOW));

            play(user, plate);
        }
    }

    public User getUser(Player player) {
        return null;
    }
}