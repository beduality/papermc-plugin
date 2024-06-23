package com.luisfuturist.core.features;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.luisfuturist.core.models.Feature;
import com.luisfuturist.core.models.Timed;
import com.luisfuturist.core.models.User;

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
            var gameStateScore = objective.getScore(line);
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
    public void onEnable() {
        super.onEnable();

        initScoreboard();

        getPhase().getGame().getPlayers().forEach(user -> {
            showBoard(user);
        });
    }
    
    @Override
    public void onDisable() {
        super.onDisable();

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
