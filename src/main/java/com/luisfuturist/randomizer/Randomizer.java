package com.luisfuturist.randomizer;

import com.luisfuturist.core.Bed;
import com.luisfuturist.randomizer.games.RandomizerGame;

public class Randomizer {
    
    public static void onLoad() {
        var orchestrator = Bed.getOrchestrator();
        
        var randomizerGame = orchestrator.createGame(new RandomizerGame());
        orchestrator.addGame(randomizerGame);
    }
}
