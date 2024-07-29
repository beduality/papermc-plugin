package com.luisfuturist.hub.games;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.luisfuturist.core.features.InventoryItemFeature;
import com.luisfuturist.core.features.PlayerListFeature;
import com.luisfuturist.core.models.Game;
import com.luisfuturist.core.phases.LobbyPhase;
import com.luisfuturist.hub.features.GameMenuFeature;
import com.luisfuturist.hub.features.HubBoardFeature;
import com.luisfuturist.rps.features.RpsFeature;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class HubGame extends Game {

    private ItemStack generateRpsItem() {
        var item = new ItemStack(Material.STONE, 1);

        var meta = item.getItemMeta();
        var displayName = Component.text("[I] Rock-paper-scissors").color(NamedTextColor.YELLOW);
        meta.displayName(displayName);
        var description = Component.text("Invite somebody to play RPS").color(NamedTextColor.WHITE);
        meta.lore(List.of(description));

        item.setItemMeta(meta);

        return item;
    }

    @Override
    public void onCreate() {
        setName("Hub");
        setMinPlayers(0);
        setMaxPlayers(Bukkit.getMaxPlayers());

        var rpsItem = generateRpsItem();

        var lobbyPhase = createPhase(new LobbyPhase());
        lobbyPhase.setTimed(false);
        lobbyPhase.createAndAddFeature(new GameMenuFeature());
        lobbyPhase.createAndAddFeature(new HubBoardFeature());
        lobbyPhase.createAndAddFeature(new InventoryItemFeature(rpsItem, 8));
        lobbyPhase.createAndAddFeature(new RpsFeature(rpsItem));

        var header = Component.text("Block-Entity Duality\n").color(NamedTextColor.AQUA);
        var footer = Component.text("\nProbabilistically fun.").color(NamedTextColor.WHITE);

        lobbyPhase.createAndAddFeature(new PlayerListFeature(header, footer));

        setFirstPhase(lobbyPhase);
    }
}
