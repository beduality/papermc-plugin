package com.luisfuturist.randomizer.features;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import com.luisfuturist.core.CorePlugin;
import com.luisfuturist.core.models.Feature;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class UhcWorldFeature extends Feature {

    @Getter
    @Setter
    @NonNull
    private World world;

    private String WORLD_NAME = "uhc";

    @Override
    public void onEnable() {
        super.onEnable();
        loadWorld(WORLD_NAME);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        unloadWorld(WORLD_NAME);
    }

    public boolean unloadWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            CorePlugin.plugin.getLogger().warning("UhcWorldFeature | World " + worldName + " does not exist.");
            return false;
        }

        // TODO Ensure no players are in the world
        for (Player player : world.getPlayers()) {
            player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
        }

        boolean success = Bukkit.unloadWorld(world, true);

        if (success) {
            CorePlugin.plugin.getLogger().info("UhcWorldFeature | Successfully unloaded world: " + worldName);
        } else {
            CorePlugin.plugin.getLogger().severe("UhcWorldFeature | Failed to unload world: " + worldName);
        }

        return success;
    }

    private void loadWorld(String worldName) {
        world = Bukkit.getWorld(worldName);

        if (world != null) {
            CorePlugin.plugin.getLogger().info("UhcWorldFeature | World " + worldName + " already exists.");
            return;
        }

        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.type(WorldType.NORMAL);

        CorePlugin.plugin.getLogger().info("UhcWorldFeature | Creating overworld: " + worldName);

        world = worldCreator.createWorld();

        if (world != null) {
            CorePlugin.plugin.getLogger().info("UhcWorldFeature | Successfully created overworld: " + worldName);
        } else {
            CorePlugin.plugin.getLogger().severe("UhcWorldFeature | Failed to create overworld: " + worldName);
        }
    }
}
