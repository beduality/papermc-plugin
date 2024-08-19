package io.github.beduality.parkour.features;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.beduality.core.models.Feature;
import io.github.beduality.core.models.User;
import io.github.beduality.core.utils.LocationUtils;
import io.github.beduality.parkour.games.ParkourGame;
import io.github.beduality.parkour.models.EulerAngles;
import io.github.beduality.parkour.utils.CheckpointUtils;

public class ParkourFeature extends Feature {
    private Set<String> checkpoints = new LinkedHashSet<>();
    private Map<String, EulerAngles> directions = new HashMap<>();

    @Override
    public void onJoin(User user) {
        var firstPlate = ((ParkourGame) getPhase().getGame()).getFirstPlate();
        saveCheckpoint(user, firstPlate);
    }

    @Override
    public void onLeave(User user) {
        checkpoints.clear();
        directions.clear();
    }

    @EventHandler
    public void onPlayerPressPlate(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.PHYSICAL)) {
            return;
        }

        var player = event.getPlayer();
        var user = getUser(player);
        var plate = event.getClickedBlock();

        if (!plate.getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)) {
            return;
        }

        var isLastPlate = plate.getRelative(BlockFace.DOWN).getType().equals(Material.DIAMOND_BLOCK);

        if (!isPlaying(player)) {
            return;
        }
        
        if (isLastPlate) {
            getPhase().getGame().win();
        } else {
            saveCheckpoint(user, plate);
        }
    }

    private void saveCheckpoint(User user, Block plate) {
        var player = user.getPlayer();
        var playerLocation = LocationUtils.getCenterLocation(plate, player);
        var checkpointString = CheckpointUtils.stringifyCheckpoint(playerLocation);

        checkpoints.add(checkpointString);
        directions.put(checkpointString, new EulerAngles(playerLocation.getPitch(), playerLocation.getYaw()));
    }

    public void teleportToLastCheckpoint(User user) {
        var lastCheckpointString = checkpoints.toArray(new String[0])[checkpoints.size() - 1];
        var playerLocation = CheckpointUtils.getOrientedLocation(lastCheckpointString, directions);

        if (playerLocation != null) {
            user.getPlayer().teleport(playerLocation);
        }
    }

    public void giveUp(User user) {
        var firstCheckpointString = checkpoints.stream().findFirst().orElse(null);
        var firstLocation = CheckpointUtils.getOrientedLocation(firstCheckpointString, directions);
        user.getPlayer().teleport(firstLocation);

        ((ParkourGame) getPhase().getGame()).giveUp(user);
    }
}
