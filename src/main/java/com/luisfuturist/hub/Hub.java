package com.luisfuturist.hub;

import com.luisfuturist.core.Bed;
import com.luisfuturist.hub.games.HubGame;

public class Hub {

    public static void onLoad() {
        Bed.getOrchestrator().setHub(new HubGame());
    }
}
