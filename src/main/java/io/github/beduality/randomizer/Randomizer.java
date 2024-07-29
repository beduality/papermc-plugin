package io.github.beduality.randomizer;

import io.github.beduality.core.Bed;
import io.github.beduality.randomizer.games.RandomizerGame;

public class Randomizer {
    
    public static void onLoad() {
        var orchestrator = Bed.getOrchestrator();
        
        var randomizerGame = orchestrator.createGame(new RandomizerGame());
        orchestrator.addGame(randomizerGame);
    }
}
