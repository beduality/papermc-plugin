package io.github.beduality.core;

import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.beduality.core.managers.ItemManager;
import io.github.beduality.core.managers.LocationManager;
import io.github.beduality.core.managers.UserManager;
import io.github.beduality.core.models.Feature;
import io.github.beduality.core.models.Orchestrator;
import lombok.Getter;

public class Bed {
   
    public static JavaPlugin plugin;
    public static Random random;

    public static UserManager userManager;
    public static ItemManager itemManager;
    public static LocationManager locationManager;

    @Getter
    protected static Orchestrator orchestrator;

    public static <T extends Orchestrator> T createOrchestrator(T orchestrator) {
        orchestrator.onCreate();
        return orchestrator;
    }

    public static Feature createOrchestratorAsFeature(Orchestrator orchestrator) {
        var createdOrchestrator = createOrchestrator(orchestrator);
        return new Feature() {
            @Override
            public void onEnable() {
                createdOrchestrator.onEnable();
            }

            @Override
            public void onDisable() {
                createdOrchestrator.onDisable();
            }
        };
    }
}