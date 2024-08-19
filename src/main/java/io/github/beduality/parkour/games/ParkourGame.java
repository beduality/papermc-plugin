package io.github.beduality.parkour.games;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import io.github.beduality.core.features.InventoryItemFeature;
import io.github.beduality.core.models.Game;
import io.github.beduality.core.models.User;
import io.github.beduality.core.utils.GuiUtils;
import io.github.beduality.core.models.Phase;
import io.github.beduality.parkour.features.ParkourFeature;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ParkourGame extends Game {

    private ParkourFeature parkourFeature;
    private User user;
    private final InventoryItemFeature backFeature = new InventoryItemFeature();
    private final InventoryItemFeature leaveFeature = new InventoryItemFeature();

    @Override
    public void onCreate() {
        setName("Parkour");
        setMinPlayers(1);
        setMaxPlayers(1);

        backFeature.setItemStack(GuiUtils.generateHotItem(Material.END_PORTAL_FRAME, "Back to last checkpoint"));
        backFeature.setSlotIndex(4);

        leaveFeature.setItemStack(GuiUtils.generateHotItem(Material.BARRIER, "Leave parkour"));
        leaveFeature.setSlotIndex(5);

        var firstPhase = createPhase(new Phase() {
            
        });
        firstPhase.setTimed(false);
        firstPhase.setName("ParkourPhase");
        firstPhase.setAllowJoin(true);
        firstPhase.createAndAddFeatures(parkourFeature, backFeature, leaveFeature);

        setFirstPhase(firstPhase);
    }

    public void onGiveUp(User user) {

    }

    public void giveUp(User user) {
        onGiveUp(user);
        leave(user);
        finish();
    }

    private void spawnFirework(Location location) {
        var firework = location.getWorld().spawn(location, Firework.class);
        var meta = firework.getFireworkMeta();

        FireworkEffect effect = FireworkEffect.builder()
            .withColor(Color.PURPLE, Color.WHITE)
            .withFade(Color.BLACK)
            .with(FireworkEffect.Type.BALL_LARGE)
            .withFlicker()
            .withTrail()
            .build();

        meta.addEffect(effect);
        meta.setPower(1);
        firework.setFireworkMeta(meta);
    }

    @Override
    public void onWin() {
        var player = user.getPlayer();
        spawnFirework(player.getLocation());

        leave(user);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        var player = event.getPlayer();
        var user = getUser(player);

        if (leaveFeature.hasRightClicked(event)) {
            parkourFeature.giveUp(user);
        }

        if (backFeature.hasRightClicked(event)) {
            parkourFeature.useCheckpoint(user);
        }
    }
}
