package com.luisfuturist.core.phases;

import org.bukkit.GameMode;

import com.luisfuturist.core.Constants;
import com.luisfuturist.core.features.ClearInventoryFeature;
import com.luisfuturist.core.features.GameModeFeature;
import com.luisfuturist.core.features.HealthFeature;
import com.luisfuturist.core.features.NoPveFeature;
import com.luisfuturist.core.features.RemovePotionEffectsFeature;
import com.luisfuturist.core.models.Phase;
import com.luisfuturist.randomizer.features.RandomDropFeature;

public class GracePhase extends Phase {
    
    @Override
    public void onCreate() {
        setName("Grace");
        createAndAddFeatures(
            new NoPveFeature(),
            new HealthFeature(),
            new RemovePotionEffectsFeature(),
            new ClearInventoryFeature(),
            new GameModeFeature(GameMode.SURVIVAL),
            new RandomDropFeature());
        setDuration(Constants.TPS * 60 * 5);
        setAllowJoin(true);
    }
}
