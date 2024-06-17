package com.luisfuturist.core;

import java.util.Random;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.luisfuturist.core.games.GlobalGame;
import com.luisfuturist.core.managers.ItemManager;
import com.luisfuturist.core.managers.LocationManager;
import com.luisfuturist.core.managers.UserManager;
import com.luisfuturist.hub.games.HubGame;
import com.luisfuturist.randomizer.features.UhcWorldFeature;
import com.luisfuturist.randomizer.games.RandomizerGame;

public class CorePlugin extends JavaPlugin implements Listener {

    public static JavaPlugin plugin;
    public static Random random;

    public static UserManager userManager;
    public static ItemManager itemManager;
    public static LocationManager locationManager;

    public static GlobalGame global;
    public static HubGame hub;
    public static RandomizerGame game;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        plugin = this;
        random = new Random();

        userManager = new UserManager();
        itemManager = new ItemManager();
        locationManager = new LocationManager();

        loadGames();
    }

    private void loadGames() {
        var uhcWorldFeature = new UhcWorldFeature();

        hub = new HubGame(this, locationManager, uhcWorldFeature);
        game = new RandomizerGame(this, locationManager, hub, uhcWorldFeature);
        global = new GlobalGame(this, hub);

        game.onEnable();
        hub.onEnable();
        global.onEnable();

        //global.start(global.getFirstPhase()); // No first phase needed
        hub.start(hub.getFirstPhase());
        game.start(game.getFirstPhase());
    }

    @Override
    public void onDisable() {
        if (global != null) {
            global.onDisable();
        }
        if (game != null) {
            game.onDisable();
        }
        if (hub != null) {
            hub.onDisable();
        }
    }
}