package com.luisfuturist.randomizer;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.luisfuturist.randomizer.features.RandomizerFeature;
import com.luisfuturist.randomizer.managers.ItemManager;

public class RandomizerPlugin extends JavaPlugin implements Listener {

    public static JavaPlugin plugin;
    public static Random random;

    public static ItemManager itemManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        plugin = this;
        random = new Random();
        
        itemManager = new ItemManager();

        Bukkit.getPluginManager().registerEvents(new RandomizerFeature(), this);
    }
}