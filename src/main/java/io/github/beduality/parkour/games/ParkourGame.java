package io.github.beduality.parkour.games;

import org.bukkit.block.Block;

import io.github.beduality.core.models.Game;
import io.github.beduality.core.models.User;
import io.github.beduality.parkour.phases.ParkourPhase;
import io.github.beduality.parkour.phases.ParkourWinPhase;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ParkourGame extends Game {

    @Getter
    private final Block firstPlate;

    @Override
    public void onCreate() {
        setMinPlayers(1);
        setMaxPlayers(1);

        var winPhase = createPhase(new ParkourWinPhase());
        
        var parkourPhase = createPhase(new ParkourPhase());
        parkourPhase.setNextPhase(winPhase);

        setFirstPhase(parkourPhase);
        setWinPhase(winPhase);
    }

    public void onGiveUp(User user) {

    }

    public void giveUp(User user) {
        onGiveUp(user);
        leave(user);
        finish();
    }
}
