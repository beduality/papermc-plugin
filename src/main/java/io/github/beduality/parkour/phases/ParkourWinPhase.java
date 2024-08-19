package io.github.beduality.parkour.phases;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;

import io.github.beduality.core.models.Phase;

public class ParkourWinPhase extends Phase {

    @Override
    public void onCreate() {
        setDuration(0);
    }

    @Override
    public void onStart() {
        getGame().getPlayers().forEach(user -> {
            spawnFirework(user.getPlayer().getLocation());
        });
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
}
