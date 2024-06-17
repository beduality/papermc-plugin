package com.luisfuturist.core;

import java.util.Random;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.luisfuturist.core.managers.ItemManager;
import com.luisfuturist.core.managers.LocationManager;
import com.luisfuturist.core.managers.UserManager;
import com.luisfuturist.core.models.Orchestrator;

public class CorePlugin extends JavaPlugin implements Listener {

    public static JavaPlugin plugin;
    public static Random random;

    public static UserManager userManager;
    public static ItemManager itemManager;
    public static LocationManager locationManager;

    public static Orchestrator orchestrator;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        plugin = this;
        random = new Random();

        userManager = new UserManager();
        itemManager = new ItemManager();
        locationManager = new LocationManager();

        orchestrator = new MainOrchestrator(plugin, locationManager);
        orchestrator.onEnable();
    }

    @Override
    public void onDisable() {
        orchestrator.onDisable();
    }
}