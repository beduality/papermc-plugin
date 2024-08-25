package io.github.beduality.spleef.phases;

import io.github.beduality.core.models.User;
import io.github.beduality.core.phases.GracePhase;
import io.github.beduality.spleef.games.SpleefGame;
import org.bukkit.entity.Player;

public class SpleefGracePhase extends GracePhase {

    @Override
    public void onStart(){
        getGame().getPlayers().forEach(user -> {
            teleport(user.getPlayer());
        } );
    }

    @Override
    public void onJoin(User user) {
        super.onJoin(user);
        teleport(user.getPlayer());
    }

    public void teleport(Player player){
        var game = (SpleefGame) getGame();
        player.teleport(game.getArena().getPodLocation());
    }
}
