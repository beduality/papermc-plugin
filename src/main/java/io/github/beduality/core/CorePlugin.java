package io.github.beduality.core;

import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;

import co.aikar.commands.PaperCommandManager;
import io.github.beduality.core.commands.HubCommand;

import io.github.beduality.core.managers.ItemManager;
import io.github.beduality.core.managers.LocationManager;
import io.github.beduality.core.managers.UserManager;
import io.github.beduality.hub.Hub;
import io.github.beduality.randomizer.Randomizer;

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
    
    private void loadCommands() {
        var manager = new PaperCommandManager(this);
        manager.enableUnstableAPI("help");
        
        manager.registerCommand(new HubCommand());
    }
    
    @Override
    public void onEnable() {
        //loadCommands();

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
