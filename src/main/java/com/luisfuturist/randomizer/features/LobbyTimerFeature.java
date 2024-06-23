package com.luisfuturist.randomizer.features;

import org.bukkit.scheduler.BukkitRunnable;

import com.luisfuturist.core.Constants;
import com.luisfuturist.core.CorePlugin;
import com.luisfuturist.core.models.Feature;
import com.luisfuturist.core.models.Timed;
import com.luisfuturist.core.models.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

@NoArgsConstructor
public class LobbyTimerFeature extends Feature implements Timed {

    private BossBar bar;

    @Getter
    @Setter
    private int duration = 2 * 60 * Constants.TPS;
    @Getter
    @Setter
    private int remainingTime = duration;

    public LobbyTimerFeature(int duration) {
        this.duration = duration;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        final Component name = Component.text("Game is about to start");
        bar = BossBar.bossBar(name, 1.0f, BossBar.Color.YELLOW, BossBar.Overlay.PROGRESS);
    }

    public void showBossBar(User user) {
        user.getPlayer().showBossBar(bar);
    }

    public void hideBossBar(User user) {
        user.getPlayer().hideBossBar(bar);
    }

    @Override
    public void onLeave(User user) {
        super.onLeave(user);
        hideBossBar(user);
    }

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (remainingTime <= 0) {
                    this.cancel();
                    onFinish();
                    return;
                }

                remainingTime--;

                bar.progress((float) remainingTime / duration);

                bar.name(Component.text("Game starts in " + (remainingTime / Constants.TPS) + " seconds"));
            }
        }.runTaskTimer(CorePlugin.plugin, 0L, 1L);
        onStart();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onFinish() {
    }
}
