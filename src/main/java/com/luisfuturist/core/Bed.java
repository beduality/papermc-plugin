package com.luisfuturist.core;

import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;

import com.luisfuturist.core.managers.ItemManager;
import com.luisfuturist.core.managers.LocationManager;
import com.luisfuturist.core.managers.UserManager;
import com.luisfuturist.core.models.Orchestrator;

import lombok.Getter;

public class Bed {
   
    public static JavaPlugin plugin; // TODO change to protected
    public static Random random; // TODO change to protected

    public static UserManager userManager;
    public static ItemManager itemManager;
    public static LocationManager locationManager;

    @Getter
    protected static Orchestrator orchestrator;

}