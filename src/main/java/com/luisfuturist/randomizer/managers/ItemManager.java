package com.luisfuturist.randomizer.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import com.luisfuturist.core.utils.ItemStackUtils;
import com.luisfuturist.randomizer.RandomizerPlugin;

public class ItemManager {

    private List<ItemStack> itemList = new ArrayList<>();

    public ItemManager() {
        reloadItemList();
    }

    private void reloadItemList() {
        itemList.clear();

        FileConfiguration config = RandomizerPlugin.plugin.getConfig();
        List<String> itemNames = config.getStringList("items");

        for (String itemName : itemNames) {
            itemList.add(ItemStackUtils.fromString(itemName));
        }
    }

    public List<ItemStack> getItemList() {
        return new ArrayList<>(itemList); // Return a copy to prevent modification outside of this class
    }
}
