package io.github.beduality.core.features;

import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;

import io.github.beduality.core.models.Feature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NoWeatherChangeFeature extends Feature {

    private World world;
    private WeatherType weather = WeatherType.CLEAR;

    @Override
    public void onEnable() {
        switch (weather) {
            case CLEAR:
                world.setStorm(false);
                world.setThundering(false);
                break;
            case DOWNFALL:
                world.setStorm(true);
                world.setThundering(true);
                break;
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        if (e.getWorld().getName().equals(world.getName())) {
            e.setCancelled(true);
        }
    }
}
