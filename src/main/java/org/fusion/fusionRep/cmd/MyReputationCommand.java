package org.fusion.fusionRep.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fusion.fusionRep.FusionRep;
import org.jetbrains.annotations.NotNull;

public class MyReputationCommand implements CommandExecutor {
    private final FusionRep plugin;

    public MyReputationCommand(FusionRep plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return true;
        }
        int reputation = plugin.getDatabaseController().getReputation(player);
        String reputationFormatted;
        if (reputation < 0) {
            reputationFormatted = "§c" + reputation;
        } else if (reputation == 0) {
            reputationFormatted = "§e" + reputation;
        } else {
            reputationFormatted = "§a" + reputation;
        }
        player.sendMessage("Ваша репутация: " + reputationFormatted);
        return true;
    }
}
