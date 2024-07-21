package com.luisfuturist.core.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.luisfuturist.core.Bed;

public class ItemStackUtils {

    public static ItemStack fromString(String itemString) {
        var parts = itemString.split(":");
        var amount = 1;

        if (parts.length == 2) {
            try {
                amount = Integer.parseInt(parts[1]);
            } catch (IllegalArgumentException e) {
                Bed.plugin.getLogger().warning("Invalid item amount format in config: " + itemString);
            }
        }

        try {
            var material = Material.valueOf(parts[0].toUpperCase());
            var itemStack = new ItemStack(material, amount);

            return itemStack;
        } catch (IllegalArgumentException e) {
            Bed.plugin.getLogger().warning("Invalid material data format in config: " + itemString);
            return null;
        }
    }
}
