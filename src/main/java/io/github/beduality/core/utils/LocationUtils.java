package io.github.beduality.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {

    public static Location fromString(String line) {
        String[] argArray = line.split(" ");

        if (argArray.length < 1) {
            throw new IllegalArgumentException("The world name is required.");
        }

        String worldName = argArray[0];
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            throw new IllegalArgumentException("World '" + worldName + "' not found.");
        }

        double x = 0.0, y = 0.0, z = 0.0;
        float yaw = 0.0f, pitch = 0.0f;

        try {
            if (argArray.length > 1)
                x = Double.parseDouble(argArray[1]);
            if (argArray.length > 2)
                y = Double.parseDouble(argArray[2]);
            if (argArray.length > 3)
                z = Double.parseDouble(argArray[3]);
            if (argArray.length > 4)
                yaw = Float.parseFloat(argArray[4]);
            if (argArray.length > 5)
                pitch = Float.parseFloat(argArray[5]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in location string: " + line, e);
        }

        return new Location(world, x, y, z, yaw, pitch);
    }
}
