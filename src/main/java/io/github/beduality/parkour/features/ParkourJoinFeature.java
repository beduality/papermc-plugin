package io.github.beduality.parkour.features;

import org.bukkit.entity.Player;

import io.github.beduality.core.Bed;
import io.github.beduality.core.models.Feature;
import io.github.beduality.core.models.User;
import io.github.beduality.parkour.orchestrators.ParkourOrchestrator;

public class ParkourJoinFeature extends Feature {

    private ParkourOrchestrator parkourOrchestrator;

    @Override
    public void onCreate() {
        parkourOrchestrator = Bed.createOrchestrator(new ParkourOrchestrator() {
            @Override
            public User getUser(Player player) {
                return ParkourJoinFeature.this.getUser(player);
            }
        });
    }

    @Override
    public void onEnable() {
        parkourOrchestrator.onEnable();
    }

    @Override
    public void onDisable() {
        parkourOrchestrator.onDisable();
    }
}
