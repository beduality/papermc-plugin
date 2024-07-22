package com.luisfuturist.core;

import com.luisfuturist.core.games.GlobalGame;
import com.luisfuturist.core.models.Orchestrator;

public class MainOrchestrator extends Orchestrator {
    
    @Override
    public void onCreate() {
        var globalGame = createGame(new GlobalGame());
        setGlobal(globalGame);
    }
}
