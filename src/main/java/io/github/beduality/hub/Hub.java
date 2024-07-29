package io.github.beduality.hub;

import io.github.beduality.core.Bed;
import io.github.beduality.hub.games.HubGame;

public class Hub {

    public static void onLoad() {
        var orchestrator = Bed.getOrchestrator();
        
        var hubGame = orchestrator.createGame(new HubGame());
        orchestrator.setHub(hubGame);
    }
}
