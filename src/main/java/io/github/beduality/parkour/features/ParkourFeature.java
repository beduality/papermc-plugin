package io.github.beduality.parkour.features;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashSet;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import io.github.beduality.core.models.Feature;
import io.github.beduality.core.models.User;
import io.github.beduality.core.utils.LocationUtils;
import io.github.beduality.parkour.games.ParkourGame;
import io.github.beduality.parkour.models.EulerAngles;
import io.github.beduality.parkour.utils.CheckpointUtils;
import lombok.Getter;
import lombok.Setter;

public class ParkourFeature extends Feature {
    private Set<String> checkpoints = new LinkedHashSet<>();
    private Map<String, EulerAngles> directions = new HashMap<>();

    @Getter
    @Setter
    private Block plate;

    private Location getFirstCheckpoint(Player player) {
        var checkpointString = checkpoints.toArray(new String[0])[0];
        return CheckpointUtils.getOrientedLocation(checkpointString, directions);
    }

    private Location getLastCheckpoint(Player player) {
        var length = checkpoints.size();
        var checkpointString = checkpoints.toArray(new String[0])[length - 1];
        return CheckpointUtils.getOrientedLocation(checkpointString, directions);
    }

    private void saveCheckpoint(User user, Block plate) {
        var player = user.getPlayer();
        var playerLocation = LocationUtils.getCenterLocation(plate, player);
        var checkpointString = CheckpointUtils.stringify(playerLocation);

        checkpoints.add(checkpointString);
        directions.put(checkpointString, new EulerAngles(playerLocation.getPitch(), playerLocation.getYaw()));
    }

    public void useCheckpoint(User user) {
        var player = user.getPlayer();
        var playerLocation = getLastCheckpoint(player);

        if(playerLocation == null) {
            return;
        }

        player.teleport(playerLocation);
    }

    public void giveUp(User user) {
        var player = user.getPlayer();
        player.teleport(getFirstCheckpoint(player));

        var parkourGame = (ParkourGame) getPhase().getGame();
        parkourGame.giveUp(user);
    }

    @Override
    public void onJoin(User user) {
        saveCheckpoint(user, plate);
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

        if(!plate.getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)) {
            return;
        }

        var isLastPlate = plate.getRelative(BlockFace.DOWN).getType().equals(Material.DIAMOND_BLOCK);
        var isCheckpointPlate = !isLastPlate;

        if (!isPlaying(player)) {
            return;
        }

        if (isCheckpointPlate) {
            saveCheckpoint(user, plate);
        }

        if (isLastPlate) {
            getPhase().getGame().win();
        }
    }
}
