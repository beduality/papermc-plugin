package com.luisfuturist.core.models;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import lombok.Getter;

public class Orchestrator implements Handler {

    @Getter
    private Game global;
    @Getter
    private Game hub;

    private HashMap<String, Game> games = new HashMap<>();

    public void setGlobal(Game game) {
        game.setOrchestrator(this);
        global = game;
    }

    public void setHub(Game game) {
        game.setOrchestrator(this);
        hub = game;
    }

    public void onCreate() {
        if(global != null) {
            global.onCreate();
        }

        if(hub != null) {
            hub.onCreate();
        }

        for (var game : games.values())
            game.onCreate();
    }

    @Override
    public void onEnable() {
        for (var game : games.values())
            game.onEnable();

        var toEnable = new Game[] { hub, global };

        for (var o : toEnable) {
            if (o != null)
                o.onEnable();
        }

        var toStart = new Game[] { global, hub };

        for (var o : toStart) {
            if (o != null)
                o.start(o.getFirstPhase());
        }

        for (var game : games.values())
            game.start(game.getFirstPhase());
    }

    @Override
    public void onDisable() {
        for (var game : games.values())
            game.finish();

        for (var game : games.values())
            game.onDisable();

        games.clear();

        var toFinish = new Game[] { hub, global };

        for (var o : toFinish) {
            if (o != null)
                o.finish();
        }

        var toDisable = new Game[] { hub, global };

        for (var o : toDisable) {
            if (o != null)
                o.onDisable();
        }
    }

    public Game addGame(Game game) {
        game.setOrchestrator(this);
        return games.put(game.getName(), game);
    }

    public Game removeGame(Game game) {
        game.setOrchestrator(null);
        return games.remove(game.getName());
    }

    public boolean containsGame(Game game) {
        return games.containsKey(game.getName());
    }

    public Map<String, Game> getGames() {
        return new HashMap<>(games);
    }

    public boolean isPlaying(User user, String gameName) {
        var game = games.get(gameName);

        if (game == null) {
            throw new NullPointerException("Game not found: " + gameName);
        }

        return game.isPlaying(user);
    }

    public void join(User user, String gameName) {
        var game = games.get(gameName);

        if (game == null) {
            throw new NullPointerException("Game not found: " + gameName);
        }

        game.play(user);
    }

    public void leave(User user, String gameName) {
        var game = games.get(gameName);

        if (game == null) {
            throw new NullPointerException("Game not found: " + gameName);
        }

        game.leave(user);
    }
}
