package io.github.beduality.core.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import io.github.beduality.core.models.Feature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;

@AllArgsConstructor
@NoArgsConstructor
public class InvitableFeature extends Feature {

    @Getter
    private final Map<String, List<String>> invites = new HashMap<>();
    @Getter @Setter
    private ItemStack itemStack;
    @Getter @Setter
    private String inviteName;

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        var player = event.getPlayer();
        var ent = event.getRightClicked();

        if (!isPlaying(player) || !player.getInventory().getItemInMainHand().isSimilar(itemStack)) {
            return;
        }

        if (!(ent instanceof Player)) {
            return;
        }

        event.setCancelled(true);

        var clickedPlayer = (Player) ent;

        var clickedPlayerInvites = invites.get(clickedPlayer.getName());
        var isAccepting = clickedPlayerInvites != null && clickedPlayerInvites.contains(player.getName());

        if (isAccepting) {
            var inviter = clickedPlayer;
            var invitedPlayer = player;
            onAccepted(inviter, invitedPlayer);
            return;
        }

        var inviter = player;
        var invitedOnes = invites.get(inviter.getName());

        var hasAlreadyInvited = invitedOnes != null && invitedOnes.contains(clickedPlayer.getName());
        
        if (hasAlreadyInvited) {
            var invitedPlayer = clickedPlayer;
            onAlreadyInvite(inviter, invitedPlayer);
            return;
        }

        invite(invitedOnes, player, clickedPlayer);
    }

    private void invite(List<String> invitedOnes, Player inviter, Player invitedPlayer) {
        if (invitedOnes == null) { // hasNeverInvited
            invitedOnes = new ArrayList<String>();
            invites.put(inviter.getName(), invitedOnes);
        }

        invitedOnes.add(invitedPlayer.getName());

        onInvite(inviter, invitedPlayer);
    }

    public void onAlreadyInvite(Player inviter, Player invitedPlayer) {
        inviter.sendMessage("You've already invited them.");
    }
    
    public void onInvite(Player inviter, Player invitedPlayer) {
        var message = Component.text(inviter.getName()).color(NamedTextColor.GOLD)
                .append(Component.text(" has invited you to play ")
                        .color(NamedTextColor.YELLOW))
                .append(Component.text(getInviteName())
                        .color(NamedTextColor.GRAY))
                .append(Component.text(".")
                        .color(NamedTextColor.YELLOW))
                .append(Component.text(" Click here to accept.")
                        .color(NamedTextColor.BLUE)
                        .clickEvent(ClickEvent.callback((e) -> {
                            onAccepted(inviter, invitedPlayer);
                        })));

        invitedPlayer.sendMessage(message);
    }
    
    public void onAccepted(Player inviter, Player invitedPlayer) {
        var message = Component.text(invitedPlayer.getName()).color(NamedTextColor.GOLD)
            .append(Component.text(" has accepted your invite.").color(NamedTextColor.YELLOW));
        
        inviter.sendMessage(message);
    }
}
