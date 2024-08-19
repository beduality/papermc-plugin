package io.github.beduality.parkour.phases;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.beduality.core.features.InventoryItemFeature;
import io.github.beduality.core.models.Phase;
import io.github.beduality.core.utils.GuiUtils;
import io.github.beduality.parkour.features.ParkourFeature;
import lombok.Getter;

public class ParkourPhase extends Phase {
    
    @Getter
    private final ParkourFeature parkourFeature = new ParkourFeature();
    private final InventoryItemFeature backFeature = new InventoryItemFeature();
    private final InventoryItemFeature leaveFeature = new InventoryItemFeature();
   
    @Override
    public void onCreate() {
        backFeature.setItemStack(GuiUtils.generateHotItem(Material.END_PORTAL_FRAME, "Return to Checkpoint"));
        backFeature.setSlotIndex(4);

        leaveFeature.setItemStack(GuiUtils.generateHotItem(Material.BARRIER, "Exit Parkour"));
        leaveFeature.setSlotIndex(5);
       
        setTimed(false);
        setAllowJoin(true);
        createAndAddFeatures(parkourFeature, backFeature, leaveFeature);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        var player = event.getPlayer();
        var user = getUser(player);

        if (leaveFeature.hasRightClicked(event)) {
            parkourFeature.giveUp(user);
        }

        if (backFeature.hasRightClicked(event)) {
            parkourFeature.teleportToLastCheckpoint(user);
        }
    }
}
