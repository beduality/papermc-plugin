package com.luisfuturist.core.features;

import org.bukkit.GameRule;
import org.bukkit.World;

import com.luisfuturist.core.models.Feature;

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
