package com.luisfuturist.randomizer.features;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.luisfuturist.core.models.Feature;
import com.luisfuturist.core.models.Phase;
import com.luisfuturist.randomizer.RandomizerPlugin;

public class RandomizerFeature extends Feature {

    private Map<String, ItemStack> drops = new HashMap<>();
    private int itemsAddedAmount = 0;

    public RandomizerFeature(Phase phase) {
        super(phase);
    }

    private ItemStack getRandomItemStack() {
        var itemList = RandomizerPlugin.itemManager.getItemList();

        if (itemList.isEmpty()) {
            return null;
        }

        var max = itemList.size();
        var hasUniqueItemsToAdd = itemsAddedAmount < max;

        if (!hasUniqueItemsToAdd) {
            return null;
        }

        int randomInt = RandomizerPlugin.random.nextInt(max);

        var is = itemList.get(randomInt);

        if (drops.containsValue(is)) {
            return getRandomItemStack();
        }

        itemsAddedAmount++;

        return is;
    }

    private void breakBlock(Block block, String material) {
        if (material.equalsIgnoreCase("AIR:0")) {
            return;
        }

        material = filterMaterials(material);

        ItemStack is = null;

        if (!drops.containsKey(material)) {
            is = getRandomItemStack();
            drops.put(material, is);
        } else {
            is = drops.get(material);
        }

        if (is == null) {
            return;
        }

        var location = block.getLocation();
        location.getWorld().dropItemNaturally(location, is);
    }

    private void breakBlock(Block block) {
        var blockUnder = block.getRelative(BlockFace.DOWN);

        if (block.getType() == Material.LEGACY_DOUBLE_PLANT && blockUnder.getType() == Material.LEGACY_DOUBLE_PLANT) {
            breakBlock(blockUnder, blockUnder.getType() + ":" + blockUnder.getData());

            blockUnder.setType(Material.AIR);
            blockUnder.getDrops().clear();
        } else {
            breakBlock(block, block.getType() + ":" + block.getData());
        }

        block.getDrops().clear();
        block.setType(Material.AIR);
    }

    private String filterMaterials(String material) {
        String id = material.split(":")[0];
        String subid = material.split(":")[1];

        String comparator = "REDSTONE_COMPARATOR_OFF";
        String leave = "LEAVES";
        String wood_step = "WOOD_STEP";
        String step = "STEP";
        String quartz = "QUARTZ_BLOCK";
        String anvil = "ANVIL";
        String cocoa = "COCOA";

        for (int i = 0; i <= 7; i++) {
            if (id.equalsIgnoreCase(quartz) && i <= 4) {
                if (i >= 2 && subid.equalsIgnoreCase(i + ""))
                    return quartz + ":2";
                else if (subid.equalsIgnoreCase(i + ""))
                    return quartz + ":" + i;
            }

            if (id.equalsIgnoreCase(step) && i <= 7) {
                if (subid.equalsIgnoreCase(i + ""))
                    return step + i;
                if (subid.equalsIgnoreCase((i + 8) + ""))
                    return step + i;
            }

            if (id.equalsIgnoreCase(wood_step) && i <= 5) {
                if (subid.equalsIgnoreCase(i + ""))
                    return wood_step + i;
                if (subid.equalsIgnoreCase((i + 8) + ""))
                    return wood_step + i;
            }

            if (i <= 3)
                for (int j = 0; j <= 3; j++) {
                    if (j <= 2)
                        for (String log : new String[] { "LOG", "LOG_2" })
                            if (id.equalsIgnoreCase(log))
                                if (subid.equalsIgnoreCase((i + (j * 4)) + ""))
                                    return log + ":" + i;

                    if (id.equalsIgnoreCase(leave) && subid.equalsIgnoreCase((i + (j * 4)) + ""))
                        return leave + ":" + i;
                    if (id.equalsIgnoreCase(comparator) && subid.equalsIgnoreCase((i * 4 + j) + ""))
                        return comparator + ":" + i;
                    if (id.equalsIgnoreCase(cocoa) && subid.equalsIgnoreCase(i * 4 + j + ""))
                        return cocoa + ":" + i;
                    if (id.equalsIgnoreCase(anvil) && subid.equalsIgnoreCase(i * 4 + j + ""))
                        return anvil + ":" + i;
                }
        }

        if (id.contains("REDSTONE_ORE"))
            return "REDSTONE_ORE";
        if (id.contains("DAYLIGHT_DETECTOR"))
            return "DAYLIGHT_DETECTOR";
        if (id.contains("SIGN"))
            return "SIGN";
        if (id.contains("BANNER"))
            return "BANNER";

        for (String importantSubid : new String[] { "STONE", "DIRT", "WOOD", "SAND", "SPONGE", "SANDSTONE", "WOOL",
                "STAINED_GLASS", "SMOOTH_BRICK", "COBBLE_WALL", "STAINED_CLAY", "PRISMARINE", "RED_SANDSTONE",
                "CAKE_BLOCK", "PUMPKIN_STEM", "MELON_STEM", "WOOD_DOUBLE_STEP", "DOUBLE_STEP", "DOUBLE_STONE_SLAB2",
                "REDSTONE_WIRE", "CAULDRON", "BREWING_STAND", "CARPET", "LONG_GRASS", "RED_ROSE", "MONSTER_EGGS",
                "SNOW", "STAINED_GLASS_PANE", "DOUBLE_PLANT", "SAPLING", "CROPS", "CARROT", "POTATO", "NETHER_WARTS",
                "ENDER_PORTAL_FRAME", "JUKEBOX" })
            if (id.equalsIgnoreCase(importantSubid))
                return id + ":" + subid;

        return id;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled() || !hasPlayer(event.getPlayer())) {
            return;
        }

        event.setCancelled(true);

        breakBlock(event.getBlock());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER) {
            return;
        }

        event.getDrops().clear();

        var entName = event.getEntity().getName();
        ItemStack is = null;

        if (!drops.containsKey(entName)) {
            is = getRandomItemStack();
            drops.put(entName, is);
        } else {
            is = drops.get(entName);
        }

        if (is == null) {
            return;
        }

        event.getDrops().add(is);
    }
}
