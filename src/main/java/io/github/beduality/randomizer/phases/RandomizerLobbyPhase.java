package io.github.beduality.randomizer.phases;

import io.github.beduality.core.Bed;
import io.github.beduality.core.features.BoardFeature;
import io.github.beduality.core.features.LobbyTimerFeature;
import io.github.beduality.core.features.SpawnFeature;
import io.github.beduality.core.models.User;
import io.github.beduality.core.phases.LobbyPhase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class RandomizerLobbyPhase extends LobbyPhase {

    private LobbyTimerFeature countdownFeature = new LobbyTimerFeature() {
        @Override
        public void onFinish() {
            super.onFinish();

            getGame().finishCurrentPhase();
        }
    };
    private BoardFeature boardFeature = new BoardFeature();

    private void setupBoard() {
        var displayName = Component.text().color(NamedTextColor.BLUE)
                .append(Component.text("Randomizer")).asComponent();

        boardFeature.getObjective().displayName(displayName);
    }

    private void updateBoard() {
        var playersNum = getGame().getPlayers().size();
        var maxPlayers = getGame().getMaxPlayers();

        boardFeature.setLines(new String[] {
                "Waiting for players",
                playersNum + "/" + maxPlayers
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        
        setName("RandomizerLobby");
        setTimed(false);

        createAndAddFeatures(boardFeature, countdownFeature);

        var lobbyLocation = Bed.locationManager.getLocation("lobby");

        if (lobbyLocation != null) {
            var spawnFeature = new SpawnFeature();
            spawnFeature.setLocation(lobbyLocation);
            createAndAddFeature(spawnFeature);
        } else {
            Bed.plugin.getLogger().warning("Randomizer | Spawn feature in " + getName()
                    + " phase is not available due to the lack of location config.");
        }
        
        setupBoard();
    }

    @Override
    public void onStart() {
        super.onStart();

        countdownFeature.start();

        getGame().getPlayers().forEach(user -> {
            countdownFeature.showBossBar(user);
        });
    }

    @Override
    public void onFinish() {
        super.onFinish();

        getGame().getPlayers().forEach(user -> {
            countdownFeature.hideBossBar(user);
        });
    }

    @Override
    public void onJoin(User user) {
        super.onJoin(user);
        countdownFeature.showBossBar(user);
        updateBoard();
    }

    @Override
    public void onLeave(User user) {
        super.onLeave(user);
        countdownFeature.hideBossBar(user);
        updateBoard();
    }
}
