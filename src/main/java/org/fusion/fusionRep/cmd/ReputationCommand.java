package org.fusion.fusionRep.cmd;

import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fusion.fusionRep.FusionRep;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReputationCommand implements CommandExecutor {
    private final FusionRep plugin;

    private String invalidSyntaxMessage;
    private String playerReputationMessage;
    private String topTitleMessage;
    private String topPlayerMessage;
    private String topSenderPlace;
    private String playerNotFoundMessage;

    public ReputationCommand(FusionRep plugin) {
        this.plugin = plugin;
        updateVariables();
    }

    public void updateVariables() {
        this.invalidSyntaxMessage = plugin.getCfg().getString("localization.reputation_command.usage", "Usage: /reputation <player / top>");
        this.playerReputationMessage = plugin.getCfg().getString("localization.reputation_command.player_reputation", "%player%'s reputation: %reputation%");
        this.topTitleMessage = plugin.getCfg().getString("localization.reputation_command.top.title", "=== Top 10 players by reputation ===");
        this.topPlayerMessage = plugin.getCfg().getString("localization.reputation_command.top.player_in_top", "%rank%. %player% %reputation");
        this.topSenderPlace = plugin.getCfg().getString("localization.reputation_command.top.sender_place", "Your place in the rating: %rank%");
        this.playerNotFoundMessage = plugin.getCfg().getString("localization.reputation_command.player_not_found_message", "Player not found.");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Audience sender = (Audience) commandSender;

        if (strings.length != 1) {
            sender.sendMessage(plugin.getFormatMessage().parse(invalidSyntaxMessage));
            return true;
        }

        if (strings[0].equalsIgnoreCase("top")) {
            if (commandSender instanceof Player) {
                if (plugin.getDatabaseController().getReputation((Player) commandSender) == 0) {
                    plugin.getDatabaseController().setReputation((Player) commandSender, 0);
                }
            }

            List<String> topPlayers = plugin.getDatabaseController().getTopReputationPlayers();
            sender.sendMessage(plugin.getFormatMessage().parse(topTitleMessage));
            int rank = 1;
            for (String playerInfo : topPlayers) {
                String[] parts = playerInfo.split(":", 2);
                String playerRank = String.valueOf(rank++);
                String playerName = parts[0];
                String playerReputation = plugin.getFormatMessage().formatReputation(Integer.parseInt(parts[1]));
                sender.sendMessage(plugin.getFormatMessage().parse(topPlayerMessage.replace("%rank%", playerRank)
                        .replace("%player%", playerName)
                        .replace("%reputation%", playerReputation)));
            }

            if (commandSender instanceof Player player) {
                int playerRank = plugin.getDatabaseController().getPlayerRank(player);
                sender.sendMessage(plugin.getFormatMessage().parse(topSenderPlace.replace("%rank%", String.valueOf(playerRank))));
            }

            return true;
        }

        Player target = Bukkit.getPlayer(strings[0]);

        if (!Bukkit.getOnlinePlayers().contains(target) || target == null) {
            sender.sendMessage(plugin.getFormatMessage().parse(playerNotFoundMessage.replace("%player%", strings[0])));
            return true;
        }

        int reputation = plugin.getDatabaseController().getReputation(target);
        String formattedReputation = plugin.getFormatMessage().formatReputation(reputation);
        sender.sendMessage(plugin.getFormatMessage().parse(playerReputationMessage
                .replace("%reputation%", formattedReputation)
                .replace("%player%", target.getName())));

        return true;
    }
}
