package io.github.beduality.spleef;

import io.github.beduality.core.Bed;
import io.github.beduality.spleef.games.SpleefGame;

public class Spleef {

    public static void onLoad() {
        var orchestrator = Bed.getOrchestrator();

        var spleefGame = orchestrator.createGame(new SpleefGame());
        orchestrator.addGame(spleefGame);
    }
}
