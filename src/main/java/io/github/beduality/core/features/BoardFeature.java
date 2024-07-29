package io.github.beduality.core.features;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import io.github.beduality.core.models.Feature;
import io.github.beduality.core.models.User;
import io.github.beduality.core.utils.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.Component;

@NoArgsConstructor
@Getter
@Setter
public class BoardFeature extends Feature {

    private Scoreboard scoreboard;
    private Objective objective;

    private static final int MAX_LENGTH = 16;
    @Getter
    private int length = 16;

    public void setLength(int length) {
        if(length > MAX_LENGTH) {
            throw new IllegalArgumentException("Board length can't be greater than " + MAX_LENGTH);
        }

        this.length = length;
    }

    private void initScoreboard() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        objective = scoreboard.registerNewObjective(getPhase().getName(), Criteria.DUMMY, Component.text(""));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void setLines(String[] lines) {
        for (var entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }

        var i = lines.length;

        for (var line : lines) {
            var gameStateScore = objective.getScore(StringUtils.padEnd(line, length));
            gameStateScore.setScore(i);
            i--;
        }

        updateScoreboard();
    }

    private void updateScoreboard() {
        getPhase().getGame().getPlayers().forEach(user -> {
            showBoard(user);
        });
    }

    public void showBoard(User user) {
        user.getPlayer().setScoreboard(scoreboard);
    }

    public void hideBoard(User user) {
        user.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    @Override
    public void onCreate() {
        initScoreboard();
    }

    @Override
    public void onEnable() {
        getPhase().getGame().getPlayers().forEach(user -> {
            showBoard(user);
        });
    }

    @Override
    public void onDisable() {
        getPhase().getGame().getPlayers().forEach(user -> {
            hideBoard(user);
        });
    }

    @Override
    public void onJoin(User user) {
        showBoard(user);
    }

    @Override
    public void onLeave(User user) {
        hideBoard(user);
    }
}
