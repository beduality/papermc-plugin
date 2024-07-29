package io.github.beduality.core.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

import io.github.beduality.core.Bed;
import io.github.beduality.core.utils.LocationUtils;

public class LocationManager {

    private Map<String, Location> locationMap = new HashMap<>();

    public LocationManager() {
        reloadLocationMap();
    }

    private void reloadLocationMap() {
        locationMap.clear();

        var config = Bed.plugin.getConfig();

        if (!config.contains("locations", true)) {
            Bed.plugin.getLogger().warning("No 'locations' section found in the config.");
            return;
        }

        for (String key : config.getConfigurationSection("locations").getKeys(false)) {
            String locationLine = config.getString("locations." + key);

            Location location;

            try {
                location = LocationUtils.fromString(locationLine);
                locationMap.put(key, location);
            } catch (Exception e) {
                Bed.plugin.getLogger()
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
