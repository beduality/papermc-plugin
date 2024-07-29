package io.github.beduality.core.phases;

import org.bukkit.GameMode;

import io.github.beduality.core.Constants;
import io.github.beduality.core.features.ClearInventoryFeature;
import io.github.beduality.core.features.GameModeFeature;
import io.github.beduality.core.features.HealthFeature;
import io.github.beduality.core.features.NoPveFeature;
import io.github.beduality.core.features.RemovePotionEffectsFeature;
import io.github.beduality.core.models.Phase;
import io.github.beduality.randomizer.features.RandomDropFeature;

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
