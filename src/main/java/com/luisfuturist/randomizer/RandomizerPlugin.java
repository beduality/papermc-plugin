package com.luisfuturist.randomizer;

import java.util.Random;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.luisfuturist.randomizer.games.GlobalGame;
import com.luisfuturist.randomizer.games.HubGame;
import com.luisfuturist.randomizer.games.RandomizerGame;
import com.luisfuturist.randomizer.managers.ItemManager;
import com.luisfuturist.randomizer.managers.LocationManager;

public class RandomizerPlugin extends JavaPlugin implements Listener {

    public static JavaPlugin plugin;
    public static Random random;

    public static ItemManager itemManager;
    public static LocationManager locationManager;

    public static GlobalGame global;
    public static RandomizerGame game;
    public static HubGame hub;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        plugin = this;
        random = new Random();

        itemManager = new ItemManager();
        locationManager = new LocationManager();

        hub = new HubGame(this, locationManager);
        global = new GlobalGame(this, hub);
        game = new RandomizerGame(this, locationManager, hub);

        global.onEnable();
        hub.onEnable();
        game.onEnable();

        global.start(global.getFirstPhase());
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