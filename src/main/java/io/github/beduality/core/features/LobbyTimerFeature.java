package io.github.beduality.core.features;

import org.bukkit.scheduler.BukkitRunnable;

import io.github.beduality.core.Bed;
import io.github.beduality.core.Constants;
import io.github.beduality.core.models.Feature;
import io.github.beduality.core.models.Timed;
import io.github.beduality.core.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.scheduler.BukkitTask;

@NoArgsConstructor
public class LobbyTimerFeature extends Feature implements Timed {

    private BossBar bar;

    @Getter
    @Setter
    private int duration = 2 * 60 * Constants.TPS;
    @Getter
    @Setter
    private int remainingTime = duration;
    @Getter
    @Setter
    private Component beginningName = Component.text("Game is about to start");
    private BukkitTask task;

    public LobbyTimerFeature(int duration) {
        this.duration = duration;
    }

    @Override
    public void onEnable() {
        bar = BossBar.bossBar(beginningName, 1.0f, BossBar.Color.YELLOW, BossBar.Overlay.PROGRESS);
    }

    public void showBossBar(User user) {
        user.getPlayer().showBossBar(bar);
    }

    public void hideBossBar(User user) {
        user.getPlayer().hideBossBar(bar);
    }

    @Override
    public void onLeave(User user) {
        hideBossBar(user);
    }

    public void start() {
        task = new BukkitRunnable() {
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
        }.runTaskTimer(Bed.plugin, 0L, 1L);
        onStart();
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
            onFinish();
        }
    }

    public void reset() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        setRemainingTime(getDuration());
        bar.name(beginningName);
        bar.progress(1);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onFinish() {
    }
}
