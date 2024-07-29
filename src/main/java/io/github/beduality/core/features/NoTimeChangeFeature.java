package io.github.beduality.core.features;

import org.bukkit.GameRule;
import org.bukkit.World;

import io.github.beduality.core.models.Feature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NoTimeChangeFeature extends Feature {

    private World world;
    private long time = 6000;

    @Override
    public void onEnable() {
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setTime(time);
    }
}
