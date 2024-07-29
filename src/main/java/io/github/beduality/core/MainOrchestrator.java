package io.github.beduality.core;

import io.github.beduality.core.games.GlobalGame;
import io.github.beduality.core.models.Orchestrator;

public class MainOrchestrator extends Orchestrator {
    
    @Override
    public void onCreate() {
        var globalGame = createGame(new GlobalGame());
        setGlobal(globalGame);
    }
}
