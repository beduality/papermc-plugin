package io.github.beduality.core.features;

import org.bukkit.Location;

import io.github.beduality.core.models.Feature;
import io.github.beduality.core.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter @NonNull @NoArgsConstructor
public class SpawnFeature extends Feature {

    private Location location;

    @Override
    public void onEnable() {
        getPhase().getGame().getPlayers().forEach(this::onJoin);
    }

    @Override
    public void onJoin(User user) {
        user.getPlayer().teleport(location);
    }
}
