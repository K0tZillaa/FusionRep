package org.fusion.fusionRep.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.fusion.fusionRep.FusionRep;


public class ReputationMenuOnClick implements Listener {
    private final FusionRep plugin;

    public ReputationMenuOnClick(FusionRep plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked() instanceof Player target) {
            if (player.isSneaking()) {
                plugin.getReputationMenu().openMenu(player, target);
            }
        }
    }
}
