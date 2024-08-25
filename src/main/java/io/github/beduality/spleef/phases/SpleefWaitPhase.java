package io.github.beduality.spleef.phases;

import io.github.beduality.core.Constants;
import io.github.beduality.core.features.LobbyTimerFeature;
import io.github.beduality.core.models.User;
import io.github.beduality.core.phases.LobbyPhase;
import io.github.beduality.spleef.games.SpleefGame;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;

public class SpleefWaitPhase extends LobbyPhase {

    private LobbyTimerFeature countdownFeature = new LobbyTimerFeature() {

        @Override
        public void onFinish() {
            super.onFinish();
            getGame().finishCurrentPhase();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        setName("SpleefWait");
        countdownFeature.setDuration(120);

        countdownFeature.setBeginningName(Component.text("Waiting for players").color(NamedTextColor.YELLOW));
        createAndAddFeature(countdownFeature);
    }

    @Override
    public void onStart() {
        super.onStart();
        getGame().getPlayers().forEach(user -> {
            countdownFeature.showBossBar(user);
        });

        var game = (SpleefGame) getGame();
        game.getArena().onEnable();
    }

    @Override
    public void onJoin(User user) {
        super.onJoin(user);

        var playersNum = getGame().getPlayers().size();
        var maxPlayers = getGame().getMaxPlayers();
        var duration = countdownFeature.getDuration();

        if (playersNum == getGame().getMinPlayers()) {
            countdownFeature.start();
        }

        var offset = 10 * Constants.TPS;

        var remainingTime = (int) (duration - duration / maxPlayers * playersNum) + offset;

        if (countdownFeature.getRemainingTime() > remainingTime) {

            countdownFeature.setRemainingTime(remainingTime);
        }

        countdownFeature.showBossBar(user);
    }

    @Override
    public void onFinish() {
        super.onFinish();
        getGame().getPlayers().forEach(user -> {
            countdownFeature.hideBossBar(user);
        });
    }

    @Override
    public void onLeave(User user) {
        super.onLeave(user);

        if (getGame().getPlayers().size() < getGame().getMinPlayers()) {
            countdownFeature.reset();
        }

        countdownFeature.hideBossBar(user);
    }
}
