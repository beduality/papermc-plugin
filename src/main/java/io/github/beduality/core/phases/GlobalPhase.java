package io.github.beduality.core.phases;

import io.github.beduality.core.features.DeathMessagesFeature;
import io.github.beduality.core.features.LoginMessagesFeature;
import io.github.beduality.core.features.PlayerChatMessagesFeature;
import io.github.beduality.core.models.Phase;

public class GlobalPhase extends Phase {

    @Override
    public void onCreate() {
        setName("Global");
        setTimed(false);
        setAllowJoin(true);
        setAllowSpectate(false);

        createAndAddFeatures(
            new LoginMessagesFeature(),
            new PlayerChatMessagesFeature(),
            new DeathMessagesFeature()
        );
    }
}
