package com.luisfuturist.randomizer.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

import com.luisfuturist.core.utils.LocationUtils;
import com.luisfuturist.randomizer.RandomizerPlugin;

public class LocationManager {

    private Map<String, Location> locationMap = new HashMap<>();

    public LocationManager() {
        reloadLocationMap();
    }

    private void reloadLocationMap() {
        locationMap.clear();

        var config = RandomizerPlugin.plugin.getConfig();

        if (!config.contains("locations", true)) {
            RandomizerPlugin.plugin.getLogger().warning("No 'locations' section found in the config.");
            return;
        }

        for (String key : config.getConfigurationSection("locations").getKeys(false)) {
            String locationLine = config.getString("locations." + key);

            Location location;

            try {
                location = LocationUtils.fromString(locationLine);
                locationMap.put(key, location);
            } catch (Exception e) {
                RandomizerPlugin.plugin.getLogger()
                        .warning(e.getMessage());
            }
        }
    }

    public Map<String, Location> getLocationMap() {
        return new HashMap<>(locationMap); // Return a copy to prevent modification outside of this class
    }

    public Location getLocation(String id) {
        return locationMap.get(id);
    }
}