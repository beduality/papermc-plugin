package io.github.beduality.parkour.utils;

import java.util.Map;

import org.bukkit.Location;

import io.github.beduality.core.utils.LocationUtils;
import io.github.beduality.parkour.models.EulerAngles;

public class CheckpointUtils {
    public static Location getOrientedLocation(
            String checkpointString,
            Map<String, EulerAngles> directions) {
        var checkpoint = LocationUtils.fromString(checkpointString);
        var direction = directions.get(checkpointString);
        checkpoint.setPitch(direction.pitch());
        checkpoint.setYaw(direction.yaw());
        return checkpoint;
    }

    public static String stringifyCheckpoint(Location location) {
        return location.getWorld().getName() + " " + location.getX() + " " + (int) location.getY() + " " + location.getZ()
                + " ";
    }
}
