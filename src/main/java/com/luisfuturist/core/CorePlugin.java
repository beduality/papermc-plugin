package com.luisfuturist.core;

import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;

import com.luisfuturist.core.managers.ItemManager;
import com.luisfuturist.core.managers.LocationManager;
import com.luisfuturist.core.managers.UserManager;
import com.luisfuturist.hub.Hub;
import com.luisfuturist.randomizer.Randomizer;

public class CorePlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        saveDefaultConfig();

        Bed.plugin = this;
        Bed.random = new Random();

        Bed.userManager = new UserManager();
        Bed.itemManager = new ItemManager();
        Bed.locationManager = new LocationManager();

        Bed.orchestrator = Bed.createOrchestrator(new MainOrchestrator());

        Hub.onLoad(); // TODO refactor into a standalone plugin
        Randomizer.onLoad(); // TODO refactor into a standalone plugin
    }
    
    @Override
    public void onEnable() {
        if(Bed.orchestrator != null) {
            Bed.orchestrator.onEnable();
        }
    }

    @Override
    public void onDisable() {
        if (Bed.orchestrator != null) {
            Bed.orchestrator.onDisable();
        }
    }
}
