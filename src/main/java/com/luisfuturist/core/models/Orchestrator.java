package com.luisfuturist.core.models;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class Orchestrator implements Handler {

    @Getter @Setter
    private Game global;
    @Getter @Setter
    private Game hub;

    private HashMap<String, Game> games = new HashMap<>();

    @Override
    public void onEnable() {
        for(var game : games.values()) {
            game.onEnable();
        }

        hub.onEnable();
        global.onEnable();

        global.start(global.getFirstPhase());
        hub.start(hub.getFirstPhase());

        for(var game : games.values()) {
            game.start(game.getFirstPhase());
        }
    }

    @Override
    public void onDisable() {
        if (global != null) {
            global.onDisable();
        }

        for(var game : games.values()) {
            game.start(game.getFirstPhase());
        }

        if (hub != null) {
            hub.onDisable();
        }

        games.clear();
    }

    public Game createGame(Game game) {
        game.setOrchestrator(this);
        return game;
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

    public void join(User user, String gameName) {
        var game = games.get(gameName);
        game.play(user);
    }

    public void leave(User user, String gameName) {
        var game = games.get(gameName);
        game.leave(user);
    }
}
