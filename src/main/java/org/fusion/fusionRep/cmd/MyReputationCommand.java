package org.fusion.fusionRep.cmd;

import net.kyori.adventure.audience.Audience;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fusion.fusionRep.FusionRep;
import org.jetbrains.annotations.NotNull;

public class MyReputationCommand implements CommandExecutor {
    private final FusionRep plugin;

    private String playerReputationMessage;

    public MyReputationCommand(FusionRep plugin) {
        this.plugin = plugin;
        updateVariables();
    }

    public void updateVariables() {
        this.playerReputationMessage = plugin.getCfg().getString("localization.my_reputation_command.player_reputation", "Your reputation: %reputation%");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return true;
        }
        if (plugin.getDatabaseController().getReputation(player) == 0) {
            plugin.getDatabaseController().setReputation(player, 0);
        }

        Audience sender = (Audience) commandSender;

        int reputation = plugin.getDatabaseController().getReputation(player);
        String playerReputation = plugin.getFormatMessage().formatReputation(Integer.parseInt(Integer.toString(reputation)));
        sender.sendMessage(plugin.getFormatMessage().parse(player, playerReputationMessage.replace("%reputation%", playerReputation)));

        return true;
    }
}
