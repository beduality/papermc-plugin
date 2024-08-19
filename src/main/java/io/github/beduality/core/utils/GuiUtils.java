package io.github.beduality.core.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class GuiUtils {

    public static ItemStack generateHotItem(Material material, String name) {
        var item = new ItemStack(material, 1);

        var meta = item.getItemMeta();
        var displayName = Component.text("[A] " + name).color(NamedTextColor.YELLOW);
        meta.displayName(displayName);

        item.setItemMeta(meta);

        return item;
    }
}
