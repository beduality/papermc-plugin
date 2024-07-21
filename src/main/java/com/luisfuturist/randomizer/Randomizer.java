package com.luisfuturist.randomizer;

import com.luisfuturist.core.Bed;
import com.luisfuturist.randomizer.games.RandomizerGame;

public class Randomizer {

    public static String getName() {
        return "Randomizer";
    }
    
    public static void onLoad() {
        var randomizerGame = new RandomizerGame();

        Bed.getOrchestrator().addGame(randomizerGame);
    }
}
