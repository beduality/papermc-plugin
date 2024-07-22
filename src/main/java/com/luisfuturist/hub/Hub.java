package com.luisfuturist.hub;

import com.luisfuturist.core.Bed;
import com.luisfuturist.hub.games.HubGame;

public class Hub {

    public static void onLoad() {
        var orchestrator = Bed.getOrchestrator();
        
        var hubGame = orchestrator.createGame(new HubGame());
        orchestrator.setHub(hubGame);
    }
}
