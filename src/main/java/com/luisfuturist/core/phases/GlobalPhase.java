package com.luisfuturist.core.phases;

import com.luisfuturist.core.features.LoginMessagesFeature;
import com.luisfuturist.core.features.PlayerChatMessagesFeature;
import com.luisfuturist.core.features.DeathMessagesFeature;
import com.luisfuturist.core.models.Phase;

public class GlobalPhase extends Phase {

    @Override
    public void onCreate() {
        setName("Global");
        setTimed(false);
        setAllowJoin(true);
        setAllowSpectate(false);

        addFeatures(
            new LoginMessagesFeature(),
            new PlayerChatMessagesFeature(),
            new DeathMessagesFeature()
        );

        super.onCreate();
    }
}
